package yijiang.jboot.Tasks;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import yijiang.jboot.comm.Constant;
import yijiang.jboot.utils.Md5.DesUtil;
import yijiang.jboot.utils.TmFunctions;
import yijiang.jboot.utils.transaction.TransactionUtils;
import yijiang.jboot.utils.transactionManage.transactionManage;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

//区块链分账并生成bet待释放的任务
public class CreateBETTasks implements Runnable{
    public static final int blxs=2; //保留小数

    Logger logger=Logger.getLogger("CreateBETTasks");
    //写标志位要线程安全
    private synchronized void setRunstadu(boolean flg){
        Constant.iCreateBETTaskRun=flg;
    }

//    public static int count=0;

    @Override
    public void run(){
        //已经在运行的，线程不再运行
        if(Constant.iCreateBETTaskRun){
            return;
        }
        else{
            setRunstadu(true);
        }

        showmsg("开始本次处理..");
        Record return_bet=Db.findFirst("select * from sys_para where id="+Constant.return_rate_bet);//投注返还率
        Record return_rate_kp=Db.findFirst("select * from sys_para where id="+Constant.return_rate_kp);//投注返还率
        // TODO 后续动态 获取爬取的 BET 对USDT 价格
        Record hl=Db.findFirst("select * from sys_para where id="+Constant.rate);//汇率BET对 USDT(如果 1USDT =500BET 则为 500)
        Record fee_bet=Db.findFirst("select * from sys_para where id="+Constant.fee_bet);//盈利手续费--投注
        Record fee_kp=Db.findFirst("select * from sys_para where id="+Constant.fee_kp);//盈利手续费--开盘
        String tronUrlVal=Db.findFirst("select * from sys_para where id="+Constant.tronUrlVal).getStr("para_value");//钱包服务地址
        payBet(return_bet,hl,fee_bet,tronUrlVal);//支付投注
        payQuotation(return_rate_kp,hl,fee_kp,tronUrlVal);//支付盘口
        showmsg("本次处理完成..");
        setRunstadu(false);
    }

    /**
     *
     * @param sys 投注返还率
     * @param sys1 汇率
     * @param sys2 扣除手续费
     * @param tronUrlVal 钱包地址
     */
    private void payBet(Record sys, Record sys1, Record sys2, String tronUrlVal){
        String sql="select * from tbl_bet_record where status="+Constant.WAITPAY;
        List<Record> list= Db.find(sql);
        for(Record rd:list){
            //TODO   调用 链上接口 支付 并 把 结果 回填到 原表里
            try{
                // 开始事务
                transactionManage.beginTran();
                Record user=Db.findFirst("select * from user where user_id=" +rd.getStr("user_id"));
//                Record wallet=Db.findFirst("select * from tbl_league_match where league_match_id=" +rd.getStr("league_match"));
                Record wallet=Db.findFirst("select * from tbl_match_address where match_id=" +rd.getStr("match_id"));
                //根据比赛结果获取收益值
                BigDecimal sy=checkOutPayBet(rd);//结算投注
                if(sy.compareTo(new BigDecimal(0))==1){//盈利
                    BigDecimal fee=sy.multiply(sys2.getBigDecimal("para_value")).multiply(new BigDecimal("0.01")).stripTrailingZeros();
                    BigDecimal profitAmount=sy.subtract(fee);//实际盈利
                    BigDecimal  fhje=profitAmount.add(rd.getBigDecimal("bet_amount"));

                    //平台到投注者
                    JSONObject js= TransactionUtils.outToAddressUsdt(DesUtil.decrypt(wallet.getStr("walkey")),user.getStr("wallet_address"), String.valueOf(fhje),tronUrlVal);
                    if("SUCCESS".equals(js.get("code"))){
                        //保存操作记录
                        Record transactionsRecord= new Record();
                        transactionsRecord.set("id",TmFunctions.getKeyStr("R")).set("cou_num",0).set("league_match_id",rd.getStr("league_match")).set("match_id",rd.getStr("match_id"))
                                .set("major_key",rd.getStr("id")).set("remark","结算投注人收益+本金").set("status",Constant.NO).set("table_name","tbl_bet_record")
                                .set("to_address",user.getStr("wallet_address")).set("transaction_hash",js.get("transactionHash"))
                                .set("walkey",wallet.getStr("walkey")).set("amount",fhje).set("create_time",System.currentTimeMillis());
                        Db.save("tbl_platform_transactions_record",transactionsRecord);

                        rd.set("profit_amount",profitAmount).set("fee",fee).set("status",Constant.FINISHED).set("update_time",System.currentTimeMillis());
                        Db.update("tbl_bet_record",rd);
                        writeBetRecord(sy,sys,sys1,rd.getStr("id"),rd.getStr("user_id"),rd.getBigDecimal("bet_amount"),Constant.BET_PROFIT);

                        // 事务提交
//                        transactionManage.commit();
                    }else{
                        rd.set("profit_amount",profitAmount).set("fee",fee).set("status",Constant.PAYFILE).set("update_time",System.currentTimeMillis());
                        Db.update("tbl_bet_record",rd);
//                        transactionManage.rollback();
                    }

                }else if(sy.compareTo(new BigDecimal(0))==0){//流局
                    JSONObject js= TransactionUtils.outToAddressUsdt(DesUtil.decrypt(wallet.getStr("walkey")),user.getStr("wallet_address"), String.valueOf(rd.getBigDecimal("bet_amount")),tronUrlVal);
                    if("SUCCESS".equals(js.get("code"))){

                        //保存操作记录
                        Record transactionsRecord= new Record();
                        transactionsRecord.set("id",TmFunctions.getKeyStr("R")).set("cou_num",0).set("league_match_id",rd.getStr("league_match")).set("match_id",rd.getStr("match_id"))
                                .set("major_key",rd.getStr("id")).set("remark","流局退还投注者金额").set("status",Constant.NO).set("table_name","tbl_bet_record")
                                .set("to_address",user.getStr("wallet_address")).set("transaction_hash",js.get("transactionHash"))
                                .set("walkey",wallet.getStr("walkey")).set("amount",rd.getBigDecimal("bet_amount")).set("create_time",System.currentTimeMillis());
                        Db.save("tbl_platform_transactions_record",transactionsRecord);

                        rd.set("profit_amount",0).set("fee",0).set("status",Constant.FINISHED).set("update_time",System.currentTimeMillis());
                        Db.update("tbl_bet_record",rd);

                        // 事务提交
//                        transactionManage.commit();
                    }else{
                        rd.set("profit_amount",0).set("fee",0).set("status",Constant.PAYFILE).set("update_time",System.currentTimeMillis());
                        Db.update("tbl_bet_record",rd);
//                        transactionManage.rollback();
                    }
                }else{//亏损
                    Record team=Db.findFirst("select * from team_member where user_id='"+rd.getStr("user_id")+"'");
                    if(team!=null){

                        //保存自己的团队信息
                        team.set("pay_count",team.getInt("pay_count")+1)
                                .set("pay_sumusdt",team.getBigDecimal("pay_sumusdt").add(rd.getBigDecimal("bet_amount")))
                                .set("profitusdt",sy);// 去除手续费的实际金额
//                        .set("bet_number",bet);//包含
                        Db.update("team_member",team);

                        // 写入收益记录里
                        Record teamProfitR=new Record();
                        teamProfitR.set("id", TmFunctions.getKeyStr("SY"))
                                .set("order_id",rd.getStr("id"))
                                .set("user_id",team.getStr("user_id"))
                                .set("revenue_type",Constant.BET_PROFIT)
                                .set("pay_type",Constant.PAY_USDT)
                                .set("team_id",team.get("team_id"))
                                .set("amount",sy)
                                .set("create_time", System.currentTimeMillis());
                        Db.save("revenue_record",teamProfitR);
                    }

                    rd.set("profit_amount",rd.getBigDecimal("bet_amount").multiply(new BigDecimal(-1))).set("fee",0).set("status",Constant.FINISHED).set("update_time",System.currentTimeMillis());
                    Db.update("tbl_bet_record",rd);
                    // 事务提交
//                    transactionManage.commit();
                }
                // 事务提交
                transactionManage.commit();
            }catch (Exception e){
                try {
                    transactionManage.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                e.printStackTrace();
            }finally {
                DbKit.getConfig().removeThreadLocalConnection();
            }
        }
    }

    //结算投注收益
    private BigDecimal checkOutPayBet(Record bet) throws Exception {
        Record match=Db.findFirst("select * from tbl_match where id='"+bet.getStr("match_id")+"'");//比赛
        Record quotation=Db.findFirst("select * from tbl_quotation where id='"+bet.getStr("quotation_id")+"'");//盘口
//        Float homeScore=match.getFloat("home_score");
//        Float guestScore=match.getFloat("guest_score");
//        Integer type=quotation.getInt("quotation_type");
//        boolean bn=false;// true 为 赢球
//        boolean ifp=false;// 是否为平局
//        //全场
//        if(type==Constant.QUOTATION_QC){
//            String winTeam=null;
//            if(homeScore>guestScore){
//                winTeam=match.getStr("home_team_id");
//            }else if(homeScore<guestScore){
//                winTeam=match.getStr("guest_team_id");
//            }
//            if(StringUtils.isNotBlank(quotation.getStr("win_team_id"))){
//                if(quotation.getStr("win_team_id").equals(winTeam)){
//                    bn=true;
//                }
//            }else{
//                if(StringUtils.isBlank(winTeam)){
//                    bn=true;
//                }
//            }
//        }else if(type==Constant.QUOTATION_RF){//让分
//            Float givePoints=quotation.getFloat("give_points");
//            homeScore=homeScore+givePoints;
//            String winTeam=null;
//            if(homeScore>guestScore){
//                winTeam=match.getStr("home_team_id");
//            }else if(homeScore<guestScore){
//                winTeam=match.getStr("guest_team_id");
//            }else{
//                ifp=true;//平局
//            }
//            if(quotation.getStr("win_team_id").equals(winTeam)){
//                bn=true;
//            }
//
//        }else if(type==Constant.QUOTATION_QC){//比大小
//            Float total=homeScore+guestScore;
//            if((total>quotation.getFloat("specific_size")&& "1".equals(quotation.getStr("compare_flog")))||
//                    (total<quotation.getFloat("specific_size")&& "0".equals(quotation.getStr("compare_flog")))){
//                bn=true;
//            }else if(total==quotation.getFloat("specific_size")){
//                ifp=true;//平局
//            }
//
//
//        }else {
//            throw new Exception();
//        }
        int res=findQuotationResult(quotation,match);
        if(res==3){//流局
            return new BigDecimal(0);
        }
        if(res==1){//获胜
            return bet.getBigDecimal("bet_amount").multiply(quotation.getBigDecimal("odds"));
        }

        return bet.getBigDecimal("bet_amount").multiply(new BigDecimal(-1));//赔的金额
    }


    /**
     *
     * 根据盘口和比赛结果 查询该盘口 是否成功（以投注方的视角）  1 成功 2 失败 3 平局（流局）
     * @param quotation
     * @param match
     * @return
     */
    private int findQuotationResult(Record quotation,Record match) throws Exception {
        Integer type=quotation.getInt("quotation_type");
        Float homeScore=match.getFloat("home_score");
        Float guestScore=match.getFloat("guest_score");
        //全场
        if(type==Constant.QUOTATION_QC){
            String winTeam=null;//获胜队伍
            if(homeScore>guestScore){
                winTeam=match.getStr("home_team_id");
            }else if(homeScore<guestScore){
                winTeam=match.getStr("guest_team_id");
            }
            if(StringUtils.isNotBlank(quotation.getStr("win_team_id"))){
                if(quotation.getStr("win_team_id").equals(winTeam)){
                    return Constant.QUOTATION_RESULT_SUCCESS;
                }
            }else{//开盘类型为平局的时候
                if(StringUtils.isBlank(winTeam)){//结果 为平局时
                    return Constant.QUOTATION_RESULT_SUCCESS;
                }
            }
        }else if(type==Constant.QUOTATION_RF){//让分
            Float givePoints=quotation.getFloat("give_points");
            homeScore=homeScore+givePoints;
            String winTeam=null;
            if(homeScore>guestScore){
                winTeam=match.getStr("home_team_id");
            }else if(homeScore<guestScore){
                winTeam=match.getStr("guest_team_id");
            }else{
                return Constant.QUOTATION_RESULT_FLOW;//分数相等 为流局 --客户确认
            }
            if(quotation.getStr("win_team_id").equals(winTeam)){
                return Constant.QUOTATION_RESULT_SUCCESS;
            }

        }else if(type==Constant.QUOTATION_DX){//比大小
            Float total=homeScore+guestScore;
            if((total>quotation.getFloat("specific_size")&& "1".equals(quotation.getStr("compare_flog")))||
                    (total<quotation.getFloat("specific_size")&& "0".equals(quotation.getStr("compare_flog")))){
                return Constant.QUOTATION_RESULT_SUCCESS;
            }else if(total==quotation.getFloat("specific_size")){
                return Constant.QUOTATION_RESULT_FLOW;// 当分数等于 大于小于分数时  为和局 返回开盘投注金额--客户确认
            }


        }else {
            throw new Exception();
        }
        return Constant.QUOTATION_RESULT_FAIL;
    }


    /**
     *结算盘口
     * @param sys 投注返还率
     * @param sys1 汇率
     * @param sys2 扣除手续费
     * @param tronUrlVal 钱包地址
     */
    private void payQuotation(Record sys, Record sys1, Record sys2, String tronUrlVal){
        String sql="select * from tbl_quotation where status="+Constant.WAITPAY;
        List<Record> list= Db.find(sql);
        for(Record rd:list){
            Record user=Db.findFirst("select * from user where user_id=" +rd.getStr("user_id"));
//            Record wallet=Db.findFirst("select * from tbl_league_match where league_match_id=" +rd.getStr("league_match"));
            Record wallet=Db.findFirst("select * from tbl_match_address where match_id=" +rd.getStr("match_id"));
            try{
                // 开始事务
                transactionManage.beginTran();
                Record match=Db.findFirst("select * from tbl_match where id='"+rd.getStr("match_id")+"'");//比赛
                int res=findQuotationResult(rd,match);
                if(res==3){//流局
                    JSONObject js= TransactionUtils.outToAddressUsdt(DesUtil.decrypt(wallet.getStr("walkey")),user.getStr("wallet_address"), String.valueOf(rd.getBigDecimal("bond")),tronUrlVal);
                    if("SUCCESS".equals(js.get("code"))){

                        //保存操作记录
                        Record transactionsRecord= new Record();
                        transactionsRecord.set("id",TmFunctions.getKeyStr("R")).set("cou_num",0).set("league_match_id",rd.getStr("league_match")).set("match_id",rd.getStr("match_id"))
                                .set("major_key",rd.getStr("id")).set("remark","流局退还开盘人金额").set("status",Constant.NO).set("table_name","tbl_quotation")
                                .set("to_address",user.getStr("wallet_address")).set("transaction_hash",js.get("transactionHash"))
                                .set("walkey",wallet.getStr("walkey")).set("amount",rd.getBigDecimal("bond")).set("create_time",System.currentTimeMillis());
                        Db.save("tbl_platform_transactions_record",transactionsRecord);

                        rd.set("profit_amount",0).set("fee",0).set("status",Constant.FINISHED).set("update_time",System.currentTimeMillis())
                                .set("surplus_bond",0);
                        Db.update("tbl_quotation",rd);


//                        // 事务提交
//                        transactionManage.commit();
                    }else{
                        rd.set("profit_amount",0).set("fee",0).set("status",Constant.PAYFILE).set("update_time",System.currentTimeMillis());
                        Db.update("tbl_quotation",rd);
//                        transactionManage.rollback();
                    }
                }else  if(res==1){//获胜--对应 开盘 人员是失败
                    Record  betSumR =Db.findFirst("select IFNULL(sum(bet_amount),0) as sumBetAmount  from tbl_bet_record where quotation_id='"+rd.getStr("id")+"' and block_hash is not null");
                    BigDecimal amountpf=betSumR.getBigDecimal("sumBetAmount").multiply(rd.getBigDecimal("odds"));//赔付金额
//                    BigDecimal amount=rd.getBigDecimal("bond").subtract(amountpf);//返还金额
                    BigDecimal amount=rd.getBigDecimal("surplus_bond");//返还金额


                    //将收益写入到 团队和收益记录里
                    Record team=Db.findFirst("select * from team_member where user_id='"+rd.getStr("user_id")+"'");
                    if(team!=null){
                        //保存自己的团队信息
                        team.set("pay_count",team.getInt("pay_count")+1)
                                .set("pay_sumusdt",team.getBigDecimal("pay_sumusdt").add(amountpf))//实际赔付金额
                                .set("profitusdt",amountpf.multiply(new BigDecimal(-1)));// 去除手续费的实际金额
//                        .set("bet_number",bet);//包含
                        Db.update("team_member",team);

                        // 写入收益记录里
                        Record teamProfitR=new Record();
                        teamProfitR.set("id", TmFunctions.getKeyStr("SY"))
                                .set("order_id",rd.getStr("id"))
                                .set("user_id",team.getStr("user_id"))
                                .set("revenue_type",Constant.QUOTATION_PROFIT)
                                .set("pay_type",Constant.PAY_USDT)
                                .set("team_id",team.get("team_id"))
                                .set("amount",amountpf.multiply(new BigDecimal(-1)))
                                .set("create_time", System.currentTimeMillis());
                        Db.save("revenue_record",teamProfitR);
                    }



                    if(amount.compareTo(new BigDecimal(0))==1){//还有剩余 ，返还给开盘人
                        JSONObject js= TransactionUtils.outToAddressUsdt(DesUtil.decrypt(wallet.getStr("walkey")),user.getStr("wallet_address"), String.valueOf(amount),tronUrlVal);
                        if("SUCCESS".equals(js.get("code"))){

                            //保存操作记录
                            Record transactionsRecord= new Record();
                            transactionsRecord.set("id",TmFunctions.getKeyStr("R")).set("cou_num",0).set("league_match_id",rd.getStr("league_match")).set("match_id",rd.getStr("match_id"))
                                    .set("major_key",rd.getStr("id")).set("remark","比赛结束开盘人失败，退还开盘人剩余金额").set("status",Constant.NO).set("table_name","tbl_quotation")
                                    .set("to_address",user.getStr("wallet_address")).set("transaction_hash",js.get("transactionHash"))
                                    .set("walkey",wallet.getStr("walkey")).set("amount",amount).set("create_time",System.currentTimeMillis());
                            Db.save("tbl_platform_transactions_record",transactionsRecord);

                            rd.set("profit_amount",amountpf.multiply(new BigDecimal(-1))).set("fee",0).set("status",Constant.FINISHED).set("update_time",System.currentTimeMillis())
                                    .set("surplus_bond",0);
                            Db.update("tbl_quotation",rd);



                            // 事务提交
//                            transactionManage.commit();
                        }else{
                            rd.set("profit_amount",amountpf.multiply(new BigDecimal(-1))).set("fee",0).set("status",Constant.PAYFILE).set("update_time",System.currentTimeMillis());
                            Db.update("tbl_quotation",rd);
//                            transactionManage.rollback();
                        }
                    }else{
                        rd.set("profit_amount",amountpf.multiply(new BigDecimal(-1))).set("fee",0).set("status",Constant.FINISHED).set("update_time",System.currentTimeMillis());
                        Db.update("tbl_quotation",rd);
                        // 事务提交
//                        transactionManage.commit();
                    }

                }else{// 失败--对应 开盘 人员是成功 成功后返还开盘人员的 盈利加保证金
                    Record  betSumR =Db.findFirst("select IFNULL(sum(bet_amount),0) as sumBetAmount  from tbl_bet_record where quotation_id='"+rd.getStr("id")+"' and block_hash is not null");
                    BigDecimal sy=betSumR.getBigDecimal("sumBetAmount");//收益
                    if(sy.compareTo(new BigDecimal(0))==0){//如果无人投注
                        rd.set("profit_amount",0).set("status",Constant.FINISHED).set("fee",0).set("update_time",System.currentTimeMillis()).set("transfer_remark","无人投注，直接将状态设置为完成");
                        Db.update("tbl_quotation",rd);


                    }else{
//                        BigDecimal cyje=rd.getBigDecimal("bond").subtract(rd.getBigDecimal("surplus_bond"));//实际占用金额
                        BigDecimal cyje=sy.multiply(rd.getBigDecimal("odds"));//实际占用金额
                        BigDecimal fee=sy.multiply(sys2.getBigDecimal("para_value")).multiply(new BigDecimal("0.01")).stripTrailingZeros();
                        BigDecimal profitAmount=sy.subtract(fee);//实际盈利
                        BigDecimal  fhje=profitAmount.add(cyje);//需要实际返回给用户的金额
                        JSONObject js= TransactionUtils.outToAddressUsdt(DesUtil.decrypt(wallet.getStr("walkey")),user.getStr("wallet_address"), String.valueOf(fhje),tronUrlVal);
                        if("SUCCESS".equals(js.get("code"))){

                            //保存操作记录
                            Record transactionsRecord= new Record();
                            transactionsRecord.set("id",TmFunctions.getKeyStr("R")).set("cou_num",0).set("league_match_id",rd.getStr("league_match")).set("match_id",rd.getStr("match_id"))
                                    .set("major_key",rd.getStr("id")).set("remark","比赛结束开盘人盈利，退还开盘人盈利金额加剩余保证金").set("status",Constant.NO).set("table_name","tbl_quotation")
                                    .set("to_address",user.getStr("wallet_address")).set("transaction_hash",js.get("transactionHash"))
                                    .set("walkey",wallet.getStr("walkey")).set("amount",fhje).set("create_time",System.currentTimeMillis());
                            Db.save("tbl_platform_transactions_record",transactionsRecord);


                            rd.set("profit_amount",profitAmount).set("fee",fee).set("status",Constant.FINISHED).set("update_time",System.currentTimeMillis())
                                    .set("surplus_bond",0);
                            Db.update("tbl_quotation",rd);
                            writeBetRecord(sy,sys,sys1,rd.getStr("id"),rd.getStr("user_id"),cyje,Constant.QUOTATION_PROFIT);

                            // 事务提交
//                        transactionManage.commit();
                        }else{
                            rd.set("profit_amount",profitAmount).set("fee",fee).set("status",Constant.PAYFILE).set("update_time",System.currentTimeMillis());
                            Db.update("tbl_quotation",rd);
//                        transactionManage.rollback();
                        }
                    }
//
                }
                  // 事务提交
                  transactionManage.commit();
            }catch (Exception e){
                try {
                    transactionManage.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                e.printStackTrace();
            }finally {
                DbKit.getConfig().removeThreadLocalConnection();
            }
        }

    }

    //写入 bet 生成信息

    /**
     *
     *
     * @param sy  收益金额
     * @param sys 交易金额
     * @param sys1
     * @param userId 用户id
     * @param amount  交易金额
     * @param type   类型 开盘&投注
     * @throws Exception
     */
    private void writeBetRecord(BigDecimal sy, Record sys, Record sys1,String id, String userId,BigDecimal amount,String type) throws Exception {
        //待释放的bet--后续修改为 BET 生成 只根据盈利金额的比率 计算，也就是只有盈利后才会生成BET
        BigDecimal bet=sy.multiply(sys.getBigDecimal("para_value").multiply(new BigDecimal("0.01"))).multiply(sys1.getBigDecimal("para_value")).setScale(blxs,BigDecimal.ROUND_HALF_UP);

        String teamId=null;

        // 查询上级并 返回 下级返现 类型的 bet
        Record team=Db.findFirst("select * from team_member where user_id='"+userId+"'");
        if(team!=null){
            teamId=team.getStr("team_id");
            //保存自己的团队信息
            team.set("pay_count",team.getInt("pay_count")+1)
                    .set("pay_sumusdt",team.getBigDecimal("pay_sumusdt").add(amount))//花费的金额
                    .set("profitusdt",sy);// 去除手续费的实际金额
//                        .set("bet_number",bet);//包含
            Db.update("team_member",team);

            // 写入收益记录里
            Record teamProfitR=new Record();
            teamProfitR.set("id", TmFunctions.getKeyStr("SY"))
                    .set("order_id",id)
                    .set("user_id",team.getStr("user_id"))
                    .set("revenue_type",type)
                    .set("pay_type",Constant.PAY_USDT)
                    .set("team_id",teamId)
                    .set("amount",sy)
                    .set("create_time", System.currentTimeMillis());
            Db.save("revenue_record",teamProfitR);

            String parentId=team.getStr("parent_id");
            if(StringUtils.isNotBlank(parentId)){
                // 当 释放时 写入到团队记录里
                Record userR=Db.findFirst("select * from user where user_id='"+team.getStr("parent_id")+"'");
                Record roleB=Db.findFirst("select * from sys_role_bet where agent_rank='"+userR.getInt("rank")+"'");
                int rewardsRatio =roleB.getInt("rewards_ratio");//奖励比率
                BigDecimal parentBet=bet.multiply(new BigDecimal(rewardsRatio/100)).setScale(blxs,BigDecimal.ROUND_HALF_UP);//上级获取待释放的bet

                // 代理收益 释放写入
                Record dlR=new Record();
                dlR.set("id", TmFunctions.getKeyStr("SF"))
                        .set("order_id",id)
                        .set("user_id",team.getStr("parent_id"))
                        .set("revenue_type", Constant.AGENT_PROFIT)
                        .set("team_id",teamId)
                        .set("begin_days",roleB.getInt("begin_days"))
                        .set("total_number",parentBet).set("surplus_number",parentBet)
                        .set("daily_number",bet.multiply(new BigDecimal(roleB.getInt("daily_release_ratio")/100)))//日释放比率
                        .set("create_time", System.currentTimeMillis());
                Db.save("to_released_bet_record",dlR);
            }
        }

        //将Bet 写入 待释放记录里
        Record record =new Record();
        record.set("id", TmFunctions.getKeyStr("SF"))
                .set("order_id",id)
                .set("user_id",userId)
                .set("revenue_type",type)//收益类型
                .set("team_id",teamId)
                .set("total_number",bet)
                .set("surplus_number",bet)
                .set("daily_number",bet.multiply(new BigDecimal(0.2)))//5天释放完成
                .set("create_time", System.currentTimeMillis());
        Db.save("to_released_bet_record",record);


    }



    private void showmsg(String msg){
        System.out.println(msg);
        logger.debug(msg);
    }

//    /**
//     * 和当前时间不在同一天返回 true
//     * @param time
//     * @return
//     */
//    private boolean chenkSameDay(Timestamp time) {
//        if(time==null){
//            return  true;
//        }
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
//        if(sdf.format(new Date()).equals(sdf.format(time))){
//            return false;
//        }
//        return  true;
//    }
}
