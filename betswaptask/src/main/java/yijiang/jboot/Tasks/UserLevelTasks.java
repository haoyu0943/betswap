package yijiang.jboot.Tasks;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import yijiang.jboot.comm.Constant;
import yijiang.jboot.utils.TmDateUtil;
import yijiang.jboot.utils.TmFunctions;
import yijiang.jboot.utils.transactionManage.transactionManage;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//用户等级回填定时
public class UserLevelTasks implements Runnable{
    Logger logger=Logger.getLogger("CreateBETTasks");
    //写标志位要线程安全
    private synchronized void setRunstadu(boolean flg){
        Constant.ifUserLevelTaskRun=flg;
    }

//    public static int count=0;

    @Override
    public void run(){
        //已经在运行的，线程不再运行
        if(Constant.ifUserLevelTaskRun){
            return;
        }
        else{
            setRunstadu(true);
        }

        showmsg("开始本次处理..");
        payBet();//支付投注
        payQuotation();//支付盘口
        showmsg("本次处理完成..");
        setRunstadu(false);
    }

    private void payBet(){
        Integer  maxValue=0;//最大值
        String sql="select * from sys_role_bet order by agent_rank desc";
        List<Record> list= Db.find(sql);
        int i=0;
        for(Record rd:list){
            try{
                // 开始事务
                transactionManage.beginTran();
                List<Record> lst=new ArrayList<>();
                if(i==0){
                     lst= Db.find("select * from team_summary where lower_count>="+rd.getInt("subordinate_count")+"  and  rank<>'"+rd.getStr("agent_rank")+"' and totalUsdt>="+rd.getInt("threshold_income"));
                }else{
                    lst= Db.find("select * from team_summary where lower_count>="+rd.getInt("subordinate_count")+" and   rank<>'"+rd.getStr("agent_rank")+"' and totalUsdt>="+rd.getInt("threshold_income")
                            +" and totalUsdt <"+maxValue);
                }
                for(Record team:lst){
                    Record user=Db.findFirst("select * from user where user_id= '"+team.getStr("user_id")+"'");
                    user.set("rank",rd.getStr("agent_rank")).set("update_time", System.currentTimeMillis());
                    Db.update("user",user);

                    //添加 用户等级变更记录
                    Record  rankR=new Record();
                    rankR.set("user_id",team.getStr("user_id"))
                            .set("rank",rd.getStr("agent_rank"))
                            .set("rank_type",Constant.RANK_DEFAULT)
                            .set("remark","定时任务金额达到要求回填")
                            .set("create_time", System.currentTimeMillis());
                    Db.save("user_rank_record",rankR);
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
            maxValue=rd.getInt("threshold_income");
            i++;
        }
    }

    //方法回填
    private void backfillMethod(Record rd) throws Exception {

    }


    private void payQuotation(){
        String sql="select * from tbl_quotation where status="+Constant.WAITPAY;
        List<Record> list= Db.find(sql);
        Record sys=Db.findFirst("select * from sys_para where id=3");//投注返还率
        Record sys1=Db.findFirst("select * from sys_para where id=2");//汇率BET对 USDT
        for(Record rd:list){
            //TODO   调用 链上接口 支付 并 把 结果 回填到 原表里
            //获取收益金额
            BigDecimal sy=new BigDecimal(0);

            try{
                // 开始事务
                transactionManage.beginTran();
                writeBetRecord(sy,sys,sys1,rd,Constant.QUOTATION_PROFIT);
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
    private void writeBetRecord(BigDecimal sy, Record sys, Record sys1, Record rd,String type) throws Exception {
        //待释放的bet
        BigDecimal bet=rd.getBigDecimal("bet_amount").multiply(sys.getBigDecimal("para_value")).multiply(sys1.getBigDecimal("para_value"));

        String teamId=null;

        // 查询上级并 返回 下级返现 类型的 bet
        Record team=Db.findFirst("select * from team_member where user_id='"+rd.getStr("user_id")+"'");
        if(team!=null){
            teamId=team.getStr("id");
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
                    .set("user_id",team.getStr("parent_id"))
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
                BigDecimal parentBet=bet.multiply(new BigDecimal(rewardsRatio/100));//上级获取待释放的bet

                // 代理收益 释放写入
                Record dlR=new Record();
                dlR.set("id", TmFunctions.getKeyStr("SF"))
                        .set("order_id",rd.getStr("id"))
                        .set("user_id",team.getStr("parent_id"))
                        .set("revenue_type", Constant.AGENT_PROFIT)
                        .set("team_id",teamId)
                        .set("total_number",parentBet).set("surplus_number",parentBet)
                        .set("daily_number",bet.multiply(new BigDecimal(roleB.getInt("daily_release_ratio")/100)))//日释放比率
                        .set("create_time", System.currentTimeMillis());
                Db.save("to_released_bet_record",dlR);
            }
        }

        //将Bet 写入 待释放记录里
        Record record =new Record();
        record.set("id", TmFunctions.getKeyStr("SF"))
                .set("order_id",rd.getStr("id"))
                .set("user_id",rd.getStr("user_id"))
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
