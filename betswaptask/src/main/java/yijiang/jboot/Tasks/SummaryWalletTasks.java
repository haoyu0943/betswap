package yijiang.jboot.Tasks;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;
import org.apache.log4j.Logger;
import yijiang.jboot.comm.Constant;
import yijiang.jboot.utils.Md5.DesUtil;
import yijiang.jboot.utils.TmFunctions;
import yijiang.jboot.utils.transaction.TransactionUtils;
import yijiang.jboot.utils.transactionManage.transactionManage;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

//归集钱包定时
public class SummaryWalletTasks implements Runnable{
    Logger logger= Logger.getLogger("SummaryWalletTasks");
    //写标志位要线程安全
    private synchronized void setRunstadu(boolean flg){
        Constant.ifSummaryWalletTaskRun=flg;
    }

    @Override
    public void run(){
        //已经在运行的，线程不再运行
        if(Constant.ifSummaryWalletTaskRun){
            return;
        }
        else{
            setRunstadu(true);
        }

        showmsg("开始本次处理SummaryWalletTasks..");
        Record uWallet = Db.findFirst("select * from sys_address where type=? and ifdel=0",Constant.WALLET_TYPE_USDT);
        Record tWallet = Db.findFirst("select * from sys_address where type=? and ifdel=0",Constant.WALLET_TYPE_TRX);
        if(uWallet!=null&&tWallet!=null){
            summaryWallet(uWallet,tWallet);
        }
        showmsg("本次处理完成SummaryWalletTasks..");
        setRunstadu(false);
    }

    private void summaryWallet(Record uWallet, Record tWallet) {
        String tronUrlVal=Db.findFirst("select * from sys_para where id="+Constant.tronUrlVal).getStr("para_value");//钱包服务地址
        Record sys=Db.findFirst("select * from sys_para where id=?",Constant.summaryWalletTime);//比赛时间
        Long  time=System.currentTimeMillis()- Integer.valueOf(sys.getStr("para_value"))*1000;
        List<Record> list= Db.find("SELECT * FROM tbl_match_address where status=? and end_time<? ",Constant.NO,time);//查询未归集钱包信息
        Record gas=Db.findFirst("select * from sys_para where id=?",Constant.oneConsumeGAS);//一次转账消耗
        for(Record rd:list) {
            Record qus=Db.findFirst("select count(1) as con  from tbl_quotation where status=? and match_id=? ",Constant.WAITPAY,rd.getStr("match_id"));
            Record bet=Db.findFirst("select count(1) as con from tbl_bet_record where status=? and match_id=? ",Constant.WAITPAY,rd.getStr("match_id"));
            //当开盘和投注都结算完成后，开始归集
            if(qus.getInt("con")==0&&bet.getInt("con")==0){
                try{
                    // 开始事务
                    transactionManage.beginTran();

                    JSONObject jsonU = TransactionUtils.findUsdtBalance(rd.getStr("wallet_address"),tronUrlVal);
                    JSONObject jsonT = TransactionUtils.findTrxBalance(rd.getStr("wallet_address"),tronUrlVal);
                    if ("SUCCESS".equals(jsonU.get("code"))&&"SUCCESS".equals(jsonT.get("code"))) {
                        BigDecimal waitZU=jsonU.getBigDecimal("amount");//待归集USDT

                        //先归集USDT
                        if(waitZU.compareTo(new BigDecimal(0))==1){
                            if(jsonT.getBigDecimal("amount").compareTo(TmFunctions.changeBigDecimalT(gas.getStr("para_value")))==-1){//小于单次转账消耗GAS
                                JSONObject json =  TransactionUtils.outToAddressTRX(DesUtil.decrypt(tWallet.getStr("walkey")),rd.getStr("wallet_address"),gas.getStr("para_value"),tronUrlVal);
                                if ("SUCCESS".equals(json.get("code"))) {
                                    Record rdd=new Record();
                                    rdd.set("id",TmFunctions.getKeyStr("SR")).set("amount",gas.getStr("para_value")).set("in_or_out",2).set("type",2).set("sys_address_id",tWallet.getStr("id"))
                                            .set("from_address",tWallet.getStr("wallet_address")).set("to_address",rd.getStr("wallet_address")).set("create_time",System.currentTimeMillis())
                                            .set("remark","比赛钱包归集时GAS不足，补充GAS");
                                    Db.save("sys_address_transfer_record",rdd);//该定时设置5分钟启动一次，正常转账会完成，不需要在定时去查是否转账成功
                                }
                            }else{//归集USDT
                                JSONObject json =  TransactionUtils.outToAddressUsdt(DesUtil.decrypt(rd.getStr("walkey")),uWallet.getStr("wallet_address"),String.valueOf(waitZU),tronUrlVal);
                                if ("SUCCESS".equals(json.get("code"))) {
                                    Record rdd=new Record();
                                    rdd.set("id",TmFunctions.getKeyStr("SR")).set("amount",waitZU).set("in_or_out",1).set("type",1).set("sys_address_id",uWallet.getStr("id"))
                                            .set("from_address",rd.getStr("wallet_address")).set("to_address",uWallet.getStr("wallet_address")).set("create_time",System.currentTimeMillis())
                                            .set("remark","比赛钱包归集USDT");
                                    Db.save("sys_address_transfer_record",rdd);//该定时设置5分钟启动一次，正常转账会完成，不需要在定时去查是否转账成功
                                }
                            }
                        }else{//归集 TRX
                            if(jsonT.getBigDecimal("amount").compareTo(TmFunctions.changeBigDecimalT(gas.getStr("para_value")))==-1){//小于单次转账消耗GAS
                                Db.update("update tbl_match_address set finish_time=?,status=? where id=?",System.currentTimeMillis(),Constant.YES,rd.getStr("id"));
                            }else{
                                BigDecimal waitZT=jsonT.getBigDecimal("amount").subtract(TmFunctions.changeBigDecimal(gas.getStr("para_value")));//待归集TRX
                                JSONObject json =  TransactionUtils.outToAddressTRX(DesUtil.decrypt(rd.getStr("walkey")),tWallet.getStr("wallet_address"),String.valueOf(waitZT),tronUrlVal);
                                if ("SUCCESS".equals(json.get("code"))) {
                                    Record rdd=new Record();
                                    rdd.set("id",TmFunctions.getKeyStr("SR")).set("amount",waitZT).set("in_or_out",1).set("type",2).set("sys_address_id",tWallet.getStr("id"))
                                            .set("from_address",rd.getStr("wallet_address")).set("to_address",tWallet.getStr("wallet_address")).set("create_time",System.currentTimeMillis())
                                            .set("remark","比赛钱包归集TRX");
                                    Db.save("sys_address_transfer_record",rdd);//该定时设置5分钟启动一次，正常转账会完成，不需要在定时去查是否转账成功

                                    Db.update("update tbl_match_address set finish_time=?,status=? where id=?",System.currentTimeMillis(),Constant.YES,rd.getStr("id"));
                                }
                            }

                        }


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
    }

    private void showmsg(String msg){
        System.out.println(msg);
        logger.debug(msg);
    }

}
