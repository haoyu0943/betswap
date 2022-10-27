package yijiang.jboot.Tasks;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;
import org.apache.log4j.Logger;
import yijiang.jboot.comm.Constant;
import yijiang.jboot.utils.TmDateUtil;
import yijiang.jboot.utils.transactionManage.transactionManage;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

//订单超时支付定时
public class OrderTimeOutTasks implements Runnable{
    Logger logger= Logger.getLogger("OrderTimeOutTasks");
    //写标志位要线程安全
    private synchronized void setRunstadu(boolean flg){
        Constant.ifOrderTimeOut=flg;
    }

    @Override
    public void run(){
        //已经在运行的，线程不再运行
        if(Constant.ifOrderTimeOut){
            return;
        }
        else{
            setRunstadu(true);
        }

        showmsg("开始本次处理..");
        releasedTbt();
        showmsg("本次处理完成..");
        setRunstadu(false);
    }

    private void releasedTbt() {
       orderTimeOut();
    }
    private void orderTimeOut() {
        Long  time=System.currentTimeMillis()+ Constant.ORDER_TIME_OUT*1000;

        String sql="SELECT * FROM tbl_order where status="+ Constant.ORDER_WAIT_ORDER+" and create_time > "+ time;
        List<Record> list= Db.find(sql);
        for(Record rd:list){
            try{
                // 开始事务
                transactionManage.beginTran();

                rd.set("update_time", System.currentTimeMillis());
                rd.set("remark", Constant.ORDER_PAY_OUT);
                rd.set("status", Constant.ORDER_CLOSE);
                Db.update("tbl_order",rd);

                //修改盘口剩余金额
                Record quotation= Db.findFirst("select * from tbl_quotation where id='"+rd.getStr("quotation_id")+"'");
                quotation.set("surplus_bond", quotation.getBigDecimal("surplus_bond").add(rd.getBigDecimal("bet_amount")));
                Db.update("tbl_quotation",quotation);

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

    private void showmsg(String msg){
        System.out.println(msg);
        logger.debug(msg);
    }

}
