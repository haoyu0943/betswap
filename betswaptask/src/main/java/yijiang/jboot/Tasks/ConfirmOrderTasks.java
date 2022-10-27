package yijiang.jboot.Tasks;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Const;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;
import org.apache.log4j.Logger;
import yijiang.jboot.comm.Constant;
import yijiang.jboot.utils.TmFunctions;
import yijiang.jboot.utils.transaction.TransactionUtils;
import yijiang.jboot.utils.transactionManage.transactionManage;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//根据交易id 查询 用户投注和开盘是否转账成功--平台转账用户交易信息
public class ConfirmOrderTasks implements Runnable{
    Logger logger=Logger.getLogger("ConfirmOrderTasks");
    //写标志位要线程安全
    private synchronized void setRunstadu(boolean flg){
        Constant.ifConfirmOrderTaskRun=flg;
    }

//    public static int count=0;

    @Override
    public void run(){
        //已经在运行的，线程不再运行
        if(Constant.ifConfirmOrderTaskRun){
            return;
        }
        else{
            setRunstadu(true);
        }

        showmsg("根据交易id查询 开始本次处理..");
        String tronUrlVal=Db.findFirst("select * from sys_para where id="+Constant.tronUrlVal).getStr("para_value");//钱包服务地址
        int checkCount=Integer.valueOf(Db.findFirst("select * from sys_para where id="+Constant.checkCount).getStr("para_value"));//查询交易次数
        checkBet(tronUrlVal,checkCount);//支付投注
        checkQuotation(tronUrlVal,checkCount);//支付盘口
        checkTransactions(tronUrlVal,checkCount);//平台支付情况盘口
        showmsg("根据交易id 查询 本次处理完成..");
        setRunstadu(false);
    }

    private void checkTransactions(String tronUrl, int checkCount) {
        String sql="select *  from tbl_platform_transactions_record where status='"+Constant.NO+"' " +
                " and cou_num<=? ";
        List<Record> list= Db.find(sql,checkCount);
        for(Record rd:list){
            try{
                // 开始事务
                transactionManage.beginTran();

//                Record user=Db.findFirst("select * from user where user_id=?",rd.getStr("user_id"));
//                Record leagueMatch=Db.findFirst("select * from tbl_league_match  where league_match_id= ?",rd.getStr("league_match"));
//                if(user==null||leagueMatch==null){
//                    System.out.println("用户或者联赛不存在 sql="+sql+"==transaction_hash="+rd.getStr("transaction_hash"));
//                    continue;
//                }

                //根据交易id查询 交易状态
                JSONObject js=  TransactionUtils.getTransactionById(rd.getStr("transaction_hash"),tronUrl);

                if("SUCCESS".equals(js.get("code"))){
                    System.out.println(rd.getStr("transaction_hash")+"==="+js.getString("blockHash"));
                    Db.update("update tbl_platform_transactions_record set status=? ,block_hash=?,cou_num=? where id=? ",
                            Constant.YES,js.getString("blockHash"),rd.getInt("cou_num")+1,
                            rd.getStr("id"));
                }else{
                    if(rd.getInt("cou_num")>checkCount){
                        Db.update("update tbl_platform_transactions_record set status=? ,transfer_remark=?  where id=?",
                                Constant.NO,"超过最大查询次数",
                                rd.getStr("id"));
                    }else{
                        Db.update("update tbl_platform_transactions_record set  cou_num=? where id=?",
                                rd.getInt("cou_num")+1,rd.getStr("id"));
                    }
                }

                // 事务提交
                transactionManage.commit();
            }catch (Exception e){
                System.out.println("回写报错： sql="+sql+"==transaction_hash="+rd.getStr("transaction_hash"));
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

    private void checkQuotation(String tronUrl, int checkCount) {
        String sql="select sum(bond) as bond,transaction_hash,user_id,match_id,cou_num  from tbl_quotation where status='"+Constant.ORDER_INCHAIN+"' " +
                " and cou_num<=? GROUP BY   transaction_hash";
        List<Record> list= Db.find(sql,checkCount);
        for(Record rd:list){
            try{
                // 开始事务
                transactionManage.beginTran();

                Record user=Db.findFirst("select * from user where user_id=?",rd.getStr("user_id"));
//                Record leagueMatch=Db.findFirst("select * from tbl_league_match  where league_match_id= ?",rd.getStr("league_match"));
                Record wallet=Db.findFirst("select * from tbl_match_address where match_id=" +rd.getStr("match_id"));
                if(user==null||wallet==null){
                    System.out.println("用户或者比赛账户不存在 sql="+sql+"==transaction_hash="+rd.getStr("transaction_hash"));
                    continue;
                }

                //根据交易id查询 交易状态
                JSONObject js=  TransactionUtils.getTransactionById(rd.getStr("transaction_hash"),tronUrl);
                if("SUCCESS".equals(js.get("code"))){
                    if(rd.getBigDecimal("bond").compareTo(js.getBigDecimal("amount"))==0&&
                    js.getString("to_address").equals(wallet.getStr("wallet_address"))&&
                    js.getString("owner_address").equals(user.getStr("wallet_address"))){//当金额和 转出转入地址一致时
                        Db.update("update tbl_quotation set status=? ,block_hash=?,cou_num=? where transaction_hash=? and status=?",
                                Constant.ONGOING,js.getString("blockHash"),rd.getInt("cou_num")+1,
                                rd.getStr("transaction_hash"),Constant.ORDER_INCHAIN);
                    }else{
                        Db.update("update tbl_quotation set status=? ,transfer_remark=?,cou_num=? where transaction_hash=? and status=?", Constant.ORDER_INCHAIN_FAIL,
                                "交易信息不符合要求to_address="+js.getString("to_address")+"owner_address="+js.getString("owner_address")+"amount="+js.getBigDecimal("amount")
                                ,rd.getInt("cou_num")+1,rd.getStr("transaction_hash"),Constant.ORDER_INCHAIN);
                    }
                }else{
                    if(rd.getInt("cou_num")>checkCount){
                        Db.update("update tbl_quotation set status=? ,transfer_remark=?  where transaction_hash=? and status=?",
                                Constant.ORDER_INCHAIN_FAIL,"超过最大查询次数",
                                rd.getStr("transaction_hash"),Constant.ORDER_INCHAIN);
                    }else{
                        Db.update("update tbl_quotation set  cou_num=? where transaction_hash=? and status=?",
                                rd.getInt("cou_num")+1,
                                rd.getStr("transaction_hash"),Constant.ORDER_INCHAIN);
                    }
                }

                // 事务提交
                transactionManage.commit();
            }catch (Exception e){
                System.out.println("回写报错： sql="+sql+"==transaction_hash="+rd.getStr("transaction_hash"));
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

    private void checkBet(String tronUrl, int checkCount) {
        String sql="select sum(bet_amount) as bond,transaction_hash,user_id,match_id,cou_num  from tbl_bet_record where status='"+Constant.ORDER_INCHAIN+"' " +
                " and cou_num<=? GROUP BY   transaction_hash";
        List<Record> list= Db.find(sql,checkCount);
        for(Record rd:list){
            try{
                // 开始事务
                transactionManage.beginTran();

                Record user=Db.findFirst("select * from user where user_id=?",rd.getStr("user_id"));
//                Record leagueMatch=Db.findFirst("select * from tbl_league_match  where league_match_id= ?",rd.getStr("league_match"));
                Record wallet=Db.findFirst("select * from tbl_match_address where match_id=" +rd.getStr("match_id"));
                if(user==null||wallet==null){
                    System.out.println("用户或者比赛账户不存在 sql="+sql+"==transaction_hash="+rd.getStr("transaction_hash"));
                    continue;
                }

                //根据交易id查询 交易状态
                JSONObject js=  TransactionUtils.getTransactionById(rd.getStr("transaction_hash"),tronUrl);
                if("SUCCESS".equals(js.get("code"))){
                    if(rd.getBigDecimal("bond").compareTo(js.getBigDecimal("amount"))==0&&
                            js.getString("to_address").equals(wallet.getStr("wallet_address"))&&
                            js.getString("owner_address").equals(user.getStr("wallet_address"))){//当金额和 转出转入地址一致时
                        Db.update("update tbl_bet_record set status=? ,block_hash=?,cou_num=? where transaction_hash=? and status=?",
                                Constant.ONGOING,js.getString("blockHash"),rd.getInt("cou_num")+1,
                                rd.getStr("transaction_hash"),Constant.ORDER_INCHAIN);
                    }else{
                        Db.update("update tbl_bet_record set status=? ,transfer_remark=?,cou_num=? where transaction_hash=? and status=?", Constant.ORDER_INCHAIN_FAIL,
                                "交易信息不符合要求to_address="+js.getString("to_address")+"owner_address="+js.getString("owner_address")+"amount="+js.getBigDecimal("amount")
                                ,rd.getInt("cou_num")+1,rd.getStr("transaction_hash"),Constant.ORDER_INCHAIN);
                    }
                }else{
                    if(rd.getInt("cou_num")>checkCount){
                        Db.update("update tbl_bet_record set status=? ,transfer_remark=?  where transaction_hash=? and status=?",
                                Constant.ORDER_INCHAIN_FAIL,"超过最大查询次数",
                                rd.getStr("transaction_hash"),Constant.ORDER_INCHAIN);
                        //如果投注失效的话，将盘口的剩余金额回写
                        Record quotation=Db.findFirst("select * from tbl_quotation where id =?",rd.getStr("quotation_id"));
                        quotation.set("surplus_bond",quotation.getBigDecimal("surplus_bond").add(changeToSurplusBond(rd.getBigDecimal("bet_amount"),quotation.getBigDecimal("odds"))));
                        quotation.set("update_time",System.currentTimeMillis());
                        Db.update("tbl_quotation",quotation);

                    }else{
                        Db.update("update tbl_bet_record set cou_num=? where transaction_hash=? and status=?",
                                rd.getInt("cou_num")+1,
                                rd.getStr("transaction_hash"),Constant.ORDER_INCHAIN);
                    }
                }

                // 事务提交
                transactionManage.commit();
            }catch (Exception e){
                System.out.println("回写报错： sql="+sql+"==transaction_hash="+rd.getStr("transaction_hash"));
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

    /**
     * /将投注金额 转换成 赔付金额
     * @param betAmount 投注金额
     * @param odds  剩余保证金
     * @return
     */
    private BigDecimal changeToSurplusBond(BigDecimal betAmount, BigDecimal odds) {
        return betAmount.multiply(odds);
    }

    private void showmsg(String msg){
        System.out.println(msg);
        logger.debug(msg);
    }

    /**
     * 和当前时间不在同一天返回 true
     * @param time
     * @return
     */
    private boolean chenkSameDay(Long time) {
        if(time==null){
            return  true;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        if(sdf.format(new Date()).equals(sdf.format(new Date(time)))){
            return false;
        }
        return  true;
    }
}
