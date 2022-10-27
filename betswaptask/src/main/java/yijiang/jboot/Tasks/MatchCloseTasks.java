//package yijiang.jboot.Tasks;
//
//import com.alibaba.fastjson.JSONObject;
//import com.jfinal.plugin.activerecord.Db;
//import com.jfinal.plugin.activerecord.DbKit;
//import com.jfinal.plugin.activerecord.Record;
//import org.apache.log4j.Logger;
//import yijiang.jboot.comm.Constant;
//import yijiang.jboot.utils.Md5.DesUtil;
//import yijiang.jboot.utils.TmDateUtil;
//import yijiang.jboot.utils.TmFunctions;
//import yijiang.jboot.utils.transaction.TransactionUtils;
//import yijiang.jboot.utils.transactionManage.transactionManage;
//
//import java.sql.SQLException;
//import java.util.Date;
//import java.util.List;
//
////比赛封盘定时任务--弃用
//public class MatchCloseTasks implements Runnable{
//    Logger logger= Logger.getLogger("MatchCloseTasks");
//    //写标志位要线程安全
//    private synchronized void setRunstadu(boolean flg){
//        Constant.ifMatchClose=flg;
//    }
//
//    @Override
//    public void run(){
//        //已经在运行的，线程不再运行
//        if(Constant.ifMatchClose){
//            return;
//        }
//        else{
//            setRunstadu(true);
//        }
//
//        showmsg("开始本次处理..");
//        matchClose();
//        showmsg("本次处理完成..");
//        setRunstadu(false);
//    }
//
//    private void matchClose() {
//        String tronUrlVal=Db.findFirst("select * from sys_para where id="+Constant.tronUrlVal).getStr("para_value");//钱包服务地址
//        Record sys=Db.findFirst("select * from sys_para where id=20");//投注返还率
//        Long  time=System.currentTimeMillis()+ Integer.valueOf(sys.getStr("para_value"))*1000;
//        String sql="SELECT * FROM tbl_match where if_close="+ Constant.NO+" and match_time > "+ time +" limit 0,1000";
//        List<Record> list= Db.find(sql);
//        for(Record rd:list){
//            try{
//                // 开始事务
//                transactionManage.beginTran();
//
////                rd.set("update_time", System.currentTimeMillis());
//                rd.set("if_close", Constant.YES);
//                Db.update("tbl_match",rd);
//
//                Record wallet=Db.findFirst("select * from tbl_league_match where league_match_id=" +rd.getStr("league_match"));
//
//                //修改盘口剩余金额
//                List<Record> quotations= Db.find("select * from tbl_quotation where match_id='"+rd.getStr("id")+"'");
//                for(Record quotation:quotations){
//                    Record user=Db.findFirst("select * from user where user_id=" +quotation.getStr("user_id"));
//
//
//                    //将剩余保证金 返还给用户
//                    JSONObject json= TransactionUtils.outToAddressUsdt(DesUtil.decrypt(wallet.getStr("walkey")),user.getStr("wallet_address"), String.valueOf(rd.getBigDecimal("surplus_bond")),tronUrlVal);
//                    if("SUCCESS".equals(json.get("code"))){
//                        quotation.set("surplus_bond",0).set("back_bond",quotation.getBigDecimal("surplus_bond")).set("update_time",System.currentTimeMillis());
//                        Db.update("tbl_quotation",quotation);
//
//                        //创建 退回记录
//                        Record backRecord= new Record();
//                        backRecord.set("surplus_bond",quotation.getBigDecimal("surplus_bond")).set("back_bond",rd.getBigDecimal("surplus_bond")).set("create_time",System.currentTimeMillis())
//                        .set("id", TmFunctions.getKeyStr("R")).set("quotation_id",quotation.getStr("id")).set("status",Constant.YES)
//                                .set("transaction_hash",json.get("transactionHash")).set("block_hash",json.get("blockHash"));
//                        Db.save("tbl_quotation_back_bond_record",backRecord);
//
//                    }else{
//                        //创建 退回记录
//                        Record backRecord= new Record();
//                        backRecord.set("surplus_bond",quotation.getBigDecimal("surplus_bond")).set("back_bond",0).set("create_time",System.currentTimeMillis())
//                                .set("id", TmFunctions.getKeyStr("R")).set("quotation_id",quotation.getStr("id")).set("status",Constant.NO);
//                        Db.save("tbl_quotation_back_bond_record",backRecord);
//                    }
//                }
//
//
//                // 事务提交
//                transactionManage.commit();
//            }catch (Exception e){
//                try {
//                    transactionManage.rollback();
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//                e.printStackTrace();
//            }finally {
//                DbKit.getConfig().removeThreadLocalConnection();
//            }
//        }
//    }
//
//    private void showmsg(String msg){
//        System.out.println(msg);
//        logger.debug(msg);
//    }
//
//}
