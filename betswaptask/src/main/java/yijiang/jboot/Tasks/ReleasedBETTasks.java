package yijiang.jboot.Tasks;

import com.graphbuilder.math.func.EFunction;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.apache.log4j.Logger;
import yijiang.jboot.comm.Constant;
import yijiang.jboot.utils.Md5.DesUtil;
import yijiang.jboot.utils.TmDateUtil;
import yijiang.jboot.utils.TmFunctions;
import yijiang.jboot.utils.transactionManage.transactionManage;

import java.beans.Transient;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//BET 定时释放服务
public class ReleasedBETTasks implements Runnable{
    Logger logger=Logger.getLogger("ReleasedTBTTasks");
    //写标志位要线程安全
    private synchronized void setRunstadu(boolean flg){
        Constant.ifReleasedBETTaskRun=flg;
    }

//    public static int count=0;

    @Override
    public void run(){
        //已经在运行的，线程不再运行
        if(Constant.ifReleasedBETTaskRun){
            return;
        }
        else{
            setRunstadu(true);
        }

        showmsg("开始本次处理..");
        releasedBet();
        showmsg("本次处理完成..");
        setRunstadu(false);
    }

    //执行完成的数据会放入到备份表里
    private void releasedBet() {
        String sql="select * from to_released_bet_record";
        List<Record> list= Db.find(sql);
        for(Record rd:list){
            //一天执行一次 ，如果上次执行时间和现在不是同一天的话，可执行
            if(chenkSameDay(rd.getLong("update_time"))){
                try{
                    // 开始事务
                    transactionManage.beginTran();

                    Record user=Db.findFirst("select * from user where user_id=" +rd.getStr("user_id"));
                    if(rd.getStr("revenue_type").equals(Constant.AGENT_PROFIT)){//代理收益
                        //判断开始释放时间
                        if(rd.getInt("begin_days")<((new Date().getTime()-rd.getLong("create_time"))/(24*60*60*1000))){
                            break;
                        }
                    }
                    //开始释放
                    BigDecimal num=rd.getBigDecimal("surplus_number").subtract(rd.getBigDecimal("daily_number"));
                    BigDecimal amount;//交易金额
                    BigDecimal nowAmount;//交易后金额
                    if(num.compareTo(new BigDecimal(0))==1){
                        //剩余数量大于每日释放数量
                        rd.set("surplus_number",rd.getBigDecimal("surplus_number").subtract(rd.getBigDecimal("daily_number")));//剩余数量
                        rd.set("update_time",new Timestamp(System.currentTimeMillis()));
                        Db.update("to_released_bet_record",rd);
                        amount=rd.getBigDecimal("daily_number");
                    }else{
                        Db.save("to_released_bet_record_backups",rd);//备份删除的数据
                        Db.delete("to_released_bet_record",rd);
                        amount=rd.getBigDecimal("surplus_number");
                    }

                    // 将 BET 释放记录放入到收益里去
                    Record teamProfitR=new Record();
                    teamProfitR.set("id", TmFunctions.getKeyStr("SY"))
                            .set("order_id",rd.getStr("order_id"))
                            .set("user_id",rd.getStr("user_id"))
                            .set("revenue_type",rd.getStr("revenue_type"))
                            .set("pay_type",Constant.PAY_BET)
                            .set("team_id",rd.getStr("team_id"))
                            .set("amount",amount)
                            .set("create_time", System.currentTimeMillis());
                    Db.save("revenue_record",teamProfitR);

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
