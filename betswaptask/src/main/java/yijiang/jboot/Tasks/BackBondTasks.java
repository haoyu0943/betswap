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

//封盘退回保证金
public class BackBondTasks implements Runnable{
    Logger logger= Logger.getLogger("BackBondTasks");
    //写标志位要线程安全
    private synchronized void setRunstadu(boolean flg){
        Constant.ifBackBoondTaskRun=flg;
    }

    @Override
    public void run(){
        //已经在运行的，线程不再运行
        if(Constant.ifBackBoondTaskRun){
            return;
        }
        else{
            setRunstadu(true);
        }

        showmsg("开始本次处理..");
        backBond();
        showmsg("本次处理完成..");
        setRunstadu(false);
    }

    private void backBond() {
        String tronUrlVal=Db.findFirst("select * from sys_para where id="+Constant.tronUrlVal).getStr("para_value");//钱包服务地址
        String sql="SELECT * FROM tbl_quotation_back_bond_record where status="+ Constant.NO;
        List<Record> list= Db.find(sql);//退回记录
        for(Record rd:list) {
            try{
                // 开始事务
                transactionManage.beginTran();

                Record quotation = Db.findFirst("select * from tbl_quotation where id='" + rd.getStr("quotation_id") + "'");
//                Record wallet = Db.findFirst("select * from tbl_league_match where league_match_id=" + quotation.getStr("league_match"));
                Record wallet=Db.findFirst("select * from tbl_match_address where match_id=" +quotation.getStr("match_id"));
                Record user = Db.findFirst("select * from user where user_id=" + quotation.getStr("user_id"));

                if(quotation.getBigDecimal("surplus_bond").compareTo(new BigDecimal(0))==1){
                    //将剩余保证金 返还给用户
                    JSONObject json = TransactionUtils.outToAddressUsdt(DesUtil.decrypt(wallet.getStr("walkey")), user.getStr("wallet_address"), String.valueOf(quotation.getBigDecimal("surplus_bond")), tronUrlVal);
                    if ("SUCCESS".equals(json.get("code"))) {

                        //保存操作记录
                        Record transactionsRecord= new Record();
                        transactionsRecord.set("id",TmFunctions.getKeyStr("R")).set("cou_num",0).set("league_match_id",quotation.getStr("league_match")).set("match_id",quotation.getStr("match_id"))
                                .set("major_key",rd.getStr("id")).set("remark","封盘后退还开盘人剩余保证金").set("status",Constant.NO).set("table_name","tbl_quotation_back_bond_record")
                                .set("to_address",user.getStr("wallet_address")).set("transaction_hash",json.get("transactionHash"))
                                .set("walkey",wallet.getStr("walkey")).set("amount",quotation.getBigDecimal("surplus_bond")).set("create_time",System.currentTimeMillis());
                        Db.save("tbl_platform_transactions_record",transactionsRecord);

                        quotation.set("surplus_bond", 0).set("back_bond", quotation.getBigDecimal("surplus_bond")).set("update_time", System.currentTimeMillis());
                        Db.update("tbl_quotation", quotation);

                        //修改 退回记录
                        rd.set("back_bond", rd.getBigDecimal("surplus_bond")).set("status", Constant.YES)
                                .set("transaction_hash", json.get("transactionHash")).set("block_hash", json.get("blockHash"));
                        Db.update("tbl_quotation_back_bond_record", rd);


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

    private void showmsg(String msg){
        System.out.println(msg);
        logger.debug(msg);
    }

}
