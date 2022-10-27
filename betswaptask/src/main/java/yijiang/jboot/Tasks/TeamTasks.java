package yijiang.jboot.Tasks;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mysql.cj.xdevapi.JsonArray;
import okhttp3.FormBody;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import yijiang.jboot.comm.Constant;
import yijiang.jboot.utils.HttpHelpUtil;
import yijiang.jboot.utils.Md5.DesUtil;
import yijiang.jboot.utils.TmDateUtil;
import yijiang.jboot.utils.TmFunctions;
import yijiang.jboot.utils.transaction.TransactionUtils;

import java.math.BigDecimal;
import java.util.*;

public class TeamTasks implements Runnable{
    Logger logger=Logger.getLogger("TeamTasks");
    public static Integer start=1;
    public static Integer time=0;//更新时间-因为比赛不是全量更新，所以 比赛表里的更新时间，不是最新的更新时间
    //写标志位要线程安全
    private synchronized void setRunstadu(boolean flg){
        Constant.ifTeamTaskRun=flg;
    }

    @Override
    public void run(){
        //已经在运行的，线程不再运行
        if(Constant.ifTeamTaskRun){
            return;
        }
        else{
            setRunstadu(true);
        }

        showmsg("开始本次处理..");
//        start=1;
//        addLeagueMatchAll();
//        start=1;
//        addTeamAll();
//        start=1;
//        addMatchAll();
        addLeagueMatch();
        addTeam();
        addMatch();
        showmsg("本次处理完成..");
        setRunstadu(false);
    }

    private void addMatch() {
        try {
            JSONObject json = new JSONObject();
            Map<String, Object> params=new HashMap<>();
            params.put("user", Constant.user);
            params.put("secret",Constant.secret);

            if(time!=0){
                params.put("time",time);
            }else{
                Record maxTeam= Db.findFirst("SELECT max(update_time) as update_time FROM tbl_match");
                params.put("time",maxTeam.getLong("update_time")/1000);
            }
//            params.put("id",start);
//            params.put("limit","1");

            json= HttpHelpUtil.sendRequestGet2Status("https://open.sportnanoapi.com/api/v5/football/recent/match/list",params);
            if("0".equals(json.getString("code"))){
                JSONArray arr=json.getJSONArray("results");
                for(int i=0;i<arr.size();i++){
                    JSONObject obj=arr.getJSONObject(i);
                    if(obj.getInteger("id")==3666524){
                        System.out.println("1111");
                    }
                    if(checkLeague(obj.getInteger("competition_id"))){
                        Record ls= Db.findFirst("select * from tbl_match where  id='"+obj.getInteger("id")+"'");
                        Record homeTeam =Db.findFirst("select * from tbl_competition_team where  id='"+obj.getInteger("home_team_id")+"'");
                        Record awayTeam =Db.findFirst("select * from tbl_competition_team where  id='"+obj.getInteger("away_team_id")+"'");
                        if(ls!=null){
                            int status=ls.getInt("status");
                            if(ls.getLong("update_time")/1000<obj.getLong("updated_at")){
                                ls.set("league_match",obj.getInteger("competition_id")).set("home_team_id",obj.getString("home_team_id"))
                                        .set("guest_team_id",obj.getString("away_team_id")).set("status",obj.getInteger("status_id")).set("match_time",obj.getLong("match_time")*1000)
                                        .set("update_time",obj.getLong("updated_at")*1000);
                                if(homeTeam!=null){
                                    ls.set("home_team",homeTeam.getStr("name")).set("home_team_en",homeTeam.getStr("name_en"))
                                            .set("home_team_zht",homeTeam.getStr("name_zht")).set("home_team_cover",homeTeam.getStr("cover"));
                                }
                                if(awayTeam!=null){
                                    ls.set("guest_team",awayTeam.getStr("name")).set("guest_team_en",awayTeam.getStr("name_en"))
                                            .set("guest_team_zht",awayTeam.getStr("name_zht")).set("guest_team_cover",awayTeam.getStr("cover"));
                                }
                                JSONArray home_scores=obj.getJSONArray("home_scores");
                                JSONArray away_scores=obj.getJSONArray("away_scores");
                                //当加时比分为0时 比分为 常规比分+点球比分，不为0时 为  加时比分+点球比分
                                if(home_scores!=null&&home_scores.size()==7){
                                    if(home_scores.getInteger(5)==0){//加时比分
                                        ls.set("home_score",home_scores.getInteger(0)+home_scores.getInteger(6));
                                    }else{
                                        ls.set("home_score",home_scores.getInteger(5)+home_scores.getInteger(6));
                                    }
                                }
                                if(away_scores!=null&&away_scores.size()==7){
                                    if(home_scores.getInteger(5)==0){//加时比分
                                        ls.set("guest_score",away_scores.getInteger(0)+away_scores.getInteger(6));
                                    }else{
                                        ls.set("guest_score",away_scores.getInteger(5)+away_scores.getInteger(6));
                                    }
                                }
                                //比赛开始
                                if(checkMatchClose(obj.getInteger("status_id"))){
                                    if(ls.getInt("if_close")!=Constant.YES){
                                        ls.set("if_close",Constant.YES);
                                        List<Record> quotations= Db.find("select * from tbl_quotation where match_id=? and status=?",ls.getStr("id"),Constant.ONGOING);
                                        for(Record quotation:quotations){
                                            // 保证金大于0时 创建 退回记录
                                            if(quotation.getBigDecimal("surplus_bond").compareTo(new BigDecimal(0))==1){
                                                Record backRecord= new Record();
                                                backRecord.set("surplus_bond",quotation.getBigDecimal("surplus_bond")).set("back_bond",0).set("create_time",System.currentTimeMillis())
                                                        .set("id", TmFunctions.getKeyStr("R")).set("quotation_id",quotation.getStr("id")).set("status",Constant.NO);
                                                Db.save("tbl_quotation_back_bond_record",backRecord);
                                            }
                                        }
                                    }

                                    Record matchA=Db.findFirst("select * from tbl_match_address where match_id=?",ls.getStr("id"));
                                    if(matchA!=null){
                                        if(matchA.getInt("intogasstatus")==null||matchA.getInt("intogasstatus")==0){
                                            Record gas=Db.findFirst("select * from sys_para where id=?",Constant.oneConsumeGAS);//一次转账消耗
                                            Record qus=Db.findFirst("select count(1) as con  from tbl_quotation where status=? and match_id=? ",Constant.ONGOING,ls.getStr("id"));
                                            Record bet=Db.findFirst("select count(1) as con from tbl_bet_record where status=? and match_id=? ",Constant.ONGOING,ls.getStr("id"));
                                            Record tWallet = Db.findFirst("select * from sys_address where type=? and ifdel=0",Constant.WALLET_TYPE_TRX);
                                            String tronUrlVal=Db.findFirst("select * from sys_para where id="+Constant.tronUrlVal).getStr("para_value");//钱包服务地址
                                            if(qus.getInt("con")>0||bet.getInt("con")>0){
                                                //开盘交易两次 结算 和 退还保证金  投注一次
                                                BigDecimal gasB=TmFunctions.changeBigDecimalT(qus.getInt("con")*2).multiply(TmFunctions.changeBigDecimal(gas.getStr("para_value")))
                                                        .add(TmFunctions.changeBigDecimalT(bet.getInt("con")).multiply(TmFunctions.changeBigDecimal(gas.getStr("para_value"))));
                                                JSONObject jsonT =  TransactionUtils.outToAddressTRX(DesUtil.decrypt(tWallet.getStr("walkey")),matchA.getStr("wallet_address"),String.valueOf(gasB),tronUrlVal);
                                                if ("SUCCESS".equals(jsonT.get("code"))) {
                                                    Record rdd=new Record();
                                                    rdd.set("id",TmFunctions.getKeyStr("SR")).set("amount",gasB).set("in_or_out",2).set("type",2).set("sys_address_id",tWallet.getStr("id"))
                                                            .set("from_address",tWallet.getStr("wallet_address")).set("to_address",matchA.getStr("wallet_address")).set("create_time",System.currentTimeMillis())
                                                            .set("remark","比赛开始后，向比赛钱包转入结算预计消耗GAS");
                                                    Db.save("sys_address_transfer_record",rdd);

                                                    Db.update("update tbl_match_address set intogasstatus=?,into_amount=? where id=?",Constant.YES,gasB,matchA.getStr("id"));
                                                }
                                            }else{
                                                Db.update("update tbl_match_address set intogasstatus=?,into_amount=? where id=?",Constant.YES,0,matchA.getStr("id"));
                                            }


                                        }
                                    }

                                }

                                // 比赛已取消 需要退还 开盘和投注金额
                                if(Constant.MATCH_YQX==obj.getInteger("status_id")){
                                    // 查询所有的盘口
                                    List<Record> quA= Db.find("select * from tbl_quotation where match_id= '"+ls.getStr("id")+"' and status='"+Constant.ONGOING+"'");
                                    BigDecimal total=new BigDecimal(0);
                                    for(Record qu:quA){
                                        List<Record> bets= Db.find("select * from tbl_bet_record where quotation_id=? and match_id=? and status=? ",qu.getStr("id"),ls.getStr("id"),Constant.ONGOING);
                                        List<Record> records=new ArrayList<>();
                                        for(Record bet:bets){
                                            BigDecimal amount=bet.getBigDecimal("bet_amount");
                                            total=total.add(amount.multiply(qu.getBigDecimal("odds")));// 添加金额
                                            Record record=new Record();
                                            record.set("id",TmFunctions.getKeyStr("C")).set("amount",amount).set("major_id",bet.getStr("id"))
                                                    .set("type",Constant.TRANSACTION_BET).set("remark","比赛取消,退还投注金额").set("status",Constant.NO)
                                                    .set("create_time", System.currentTimeMillis());
                                            records.add(record);
                                            bet.set("remark","比赛取消,退还投注金额").set("status",Constant.CANCEL).set("update_time", System.currentTimeMillis());
                                        }
                                        //保存投注退回记录
                                        Db.batchSave("tbl_match_cancel_back_bond_record",records,records.size());
                                        Db.batchUpdate("tbl_bet_record",bets,bets.size());

                                        //保存开盘退回记录
                                        Record record=new Record();
                                        record.set("id",TmFunctions.getKeyStr("C")).set("amount",qu.getBigDecimal("surplus_bond").add(total))
                                                .set("major_id",qu.getStr("id"))
                                                .set("type",Constant.QUOTATION_QUOTATION).set("remark","比赛取消,退还开盘剩余金额:"+qu.getBigDecimal("surplus_bond")+"投注金额:"+total)
                                                .set("status",Constant.NO)
                                                .set("create_time", System.currentTimeMillis());
                                        Db.save("tbl_match_cancel_back_bond_record",record);

                                        qu.set("remark","比赛取消,退还开盘金额").set("status",Constant.CANCEL).set("update_time", System.currentTimeMillis())
                                        .set("surplus_bond",0);
                                        Db.update("tbl_quotation",qu);
                                    }

                                }

                                Db.update("tbl_match",ls);
                            }
//                        TODO 修改进行中的盘口 和 投注状态 ，后续定时结算
                            if(obj.getInteger("status_id")==Constant.MATCH_YWS&&obj.getInteger("status_id")!=status){
                                //修改进行中的盘口 和 投注状态 ，后续定时结算
                                Db.update("update tbl_quotation set status='"+Constant.WAITPAY+"' where match_id= '"+ls.getStr("id")+"' and status='"+Constant.ONGOING+"'");
                                Db.update("update tbl_bet_record set status='"+Constant.WAITPAY+"' where match_id= '"+ls.getStr("id")+"'and status='"+Constant.ONGOING+"'");
                            }
                            if(obj.getInteger("status_id")==Constant.MATCH_YWS){
                                Record rd=Db.findFirst("select * from tbl_match_finish_record where match_id=?",ls.getStr("id"));
                                if(rd==null){
                                    Record rdN=new Record();
                                    rdN.set("match_id",ls.getStr("id")).set("finish_time",System.currentTimeMillis());
                                    Db.save("tbl_match_finish_record",rdN);
                                }

                                Record mat=Db.findFirst("select * from tbl_match_address where match_id=?",ls.getStr("id"));
                                if(mat!=null&&mat.getLong("end_time")==null){
                                    Db.update("update tbl_match_address set end_time=? where id=?",System.currentTimeMillis(),mat.getStr("id"));
                                }
                            }

                        }else{
                            ls=new Record();
                            ls.set("id", obj.getInteger("id")).set("league_match",obj.getInteger("competition_id")).set("home_team_id",obj.getString("home_team_id"))
                                    .set("guest_team_id",obj.getString("away_team_id")).set("status",obj.getInteger("status_id")).set("match_time",obj.getLong("match_time")*1000)
                                    .set("update_time",obj.getLong("updated_at")*1000).set("create_time",System.currentTimeMillis());
                            if(homeTeam!=null){
                                ls.set("home_team",homeTeam.getStr("name")).set("home_team_en",homeTeam.getStr("name_en"))
                                        .set("home_team_zht",homeTeam.getStr("name_zht")).set("home_team_cover",homeTeam.getStr("cover"));
                            }
                            if(awayTeam!=null){
                                ls.set("guest_team",awayTeam.getStr("name")).set("guest_team_en",awayTeam.getStr("name_en"))
                                        .set("guest_team_zht",awayTeam.getStr("name_zht")).set("guest_team_cover",awayTeam.getStr("cover"));
                            }
                            JSONArray home_scores=obj.getJSONArray("home_scores");
                            JSONArray away_scores=obj.getJSONArray("away_scores");
                            //当加时比分为0时 比分为 常规比分+点球比分，不为0时 为  加时比分+点球比分
                            if(home_scores!=null&&home_scores.size()==7){
                                if(home_scores.getInteger(5)==0){//加时比分
                                    ls.set("home_score",home_scores.getInteger(0)+home_scores.getInteger(6));
                                }else{
                                    ls.set("home_score",home_scores.getInteger(5)+home_scores.getInteger(6));
                                }
                            }
                            if(away_scores!=null&&away_scores.size()==7){
                                if(home_scores.getInteger(5)==0){//加时比分
                                    ls.set("guest_score",away_scores.getInteger(0)+away_scores.getInteger(6));
                                }else{
                                    ls.set("guest_score",away_scores.getInteger(5)+away_scores.getInteger(6));
                                }
                            }
                            if(checkMatchClose(obj.getInteger("status_id"))){
                                ls.set("if_close",Constant.YES);
                            }else{
                                ls.set("if_close",Constant.NO);
                            }
                            Db.save("tbl_match",ls);
                        }
                    }

                }
                time=json.getJSONObject("query").getInteger("max_time");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkMatchClose(Integer statusId) {
        List<String> lst= Arrays.asList(Constant.closeMatchs.split(","));
        return lst.contains(String.valueOf(statusId));
    }

    private void addMatchAll() {
        try {
            JSONObject json = new JSONObject();
            Map<String, Object> params=new HashMap<>();
            params.put("user", Constant.user);
            params.put("secret",Constant.secret);
            params.put("id",start);
//            params.put("limit","1");

            json= HttpHelpUtil.sendRequestGet2Status("https://open.sportnanoapi.com/api/v5/football/recent/match/list",params);
            if("0".equals(json.getString("code"))){
                JSONArray arr=json.getJSONArray("results");
                for(int i=0;i<arr.size();i++){
                    JSONObject obj=arr.getJSONObject(i);
                    if(checkLeague(obj.getInteger("competition_id"))){
                        Record ls= Db.findFirst("select * from tbl_match where  id='"+obj.getInteger("id")+"'");
                        Record homeTeam =Db.findFirst("select * from tbl_competition_team where  id='"+obj.getInteger("home_team_id")+"'");
                        Record awayTeam =Db.findFirst("select * from tbl_competition_team where  id='"+obj.getInteger("away_team_id")+"'");
                        if(ls!=null){
                            if(ls.getLong("update_time")/1000<obj.getLong("updated_at")){
                                ls.set("league_match",obj.getInteger("competition_id")).set("home_team_id",obj.getString("home_team_id"))
                                        .set("guest_team_id",obj.getString("away_team_id")).set("status",obj.getInteger("status_id")).set("match_time",obj.getLong("match_time")*1000)
                                        .set("update_time",obj.getLong("updated_at")*1000);
                                if(homeTeam!=null){
                                    ls.set("home_team",homeTeam.getStr("name")).set("home_team_en",homeTeam.getStr("name_en"))
                                            .set("home_team_zht",homeTeam.getStr("name_zht")).set("home_team_cover",homeTeam.getStr("cover"));
                                }
                                if(awayTeam!=null){
                                    ls.set("guest_team",awayTeam.getStr("name")).set("guest_team_en",awayTeam.getStr("name_en"))
                                            .set("guest_team_zht",awayTeam.getStr("name_zht")).set("guest_team_cover",awayTeam.getStr("cover"));
                                }
                                JSONArray home_scores=obj.getJSONArray("home_scores");
                                JSONArray away_scores=obj.getJSONArray("away_scores");
                                //当加时比分为0时 比分为 常规比分+点球比分，不为0时 为  加时比分+点球比分
                                if(home_scores!=null&&home_scores.size()==7){
                                    if(home_scores.getInteger(5)==0){//加时比分
                                        ls.set("home_score",home_scores.getInteger(0)+home_scores.getInteger(6));
                                    }else{
                                        ls.set("home_score",home_scores.getInteger(5)+home_scores.getInteger(6));
                                    }
                                }
                                if(away_scores!=null&&away_scores.size()==7){
                                    if(home_scores.getInteger(5)==0){//加时比分
                                        ls.set("guest_score",away_scores.getInteger(0)+away_scores.getInteger(6));
                                    }else{
                                        ls.set("guest_score",away_scores.getInteger(5)+away_scores.getInteger(6));
                                    }
                                }
                                Db.update("tbl_match",ls);
                            }
                        }else{
                            ls=new Record();
                            ls.set("id", obj.getInteger("id")).set("league_match",obj.getInteger("competition_id")).set("home_team_id",obj.getString("home_team_id"))
                                    .set("guest_team_id",obj.getString("away_team_id")).set("status",obj.getInteger("status_id")).set("match_time",obj.getLong("match_time")*1000)
                                    .set("update_time",obj.getLong("updated_at")*1000).set("create_time",System.currentTimeMillis());
                            if(homeTeam!=null){
                                ls.set("home_team",homeTeam.getStr("name")).set("home_team_en",homeTeam.getStr("name_en"))
                                        .set("home_team_zht",homeTeam.getStr("name_zht")).set("home_team_cover",homeTeam.getStr("cover"));
                            }
                            if(awayTeam!=null){
                                ls.set("guest_team",awayTeam.getStr("name")).set("guest_team_en",awayTeam.getStr("name_en"))
                                        .set("guest_team_zht",awayTeam.getStr("name_zht")).set("guest_team_cover",awayTeam.getStr("cover"));
                            }
                            JSONArray home_scores=obj.getJSONArray("home_scores");
                            JSONArray away_scores=obj.getJSONArray("away_scores");
                            //当加时比分为0时 比分为 常规比分+点球比分，不为0时 为  加时比分+点球比分
                            if(home_scores!=null&&home_scores.size()==7){
                                if(home_scores.getInteger(5)==0){//加时比分
                                    ls.set("home_score",home_scores.getInteger(0)+home_scores.getInteger(6));
                                }else{
                                    ls.set("home_score",home_scores.getInteger(5)+home_scores.getInteger(6));
                                }
                            }
                            if(away_scores!=null&&away_scores.size()==7){
                                if(home_scores.getInteger(5)==0){//加时比分
                                    ls.set("guest_score",away_scores.getInteger(0)+away_scores.getInteger(6));
                                }else{
                                    ls.set("guest_score",away_scores.getInteger(5)+away_scores.getInteger(6));
                                }
                            }
                            ls.set("if_close",Constant.NO); //默认设置不封盘，后台定时执行 封盘
                            Db.save("tbl_match",ls);
                        }
                    }
                   
                }
                if(json.getJSONObject("query").getInteger("total")==1000){
                    start=json.getJSONObject("query").getInteger("max_id");
                    addMatchAll();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkLeague(Integer id) {
        List<String> lst= Arrays.asList(Constant.leagusMatchs.split(","));
        return lst.contains(String.valueOf(id));
    }

    private void addTeam() {
        try {
            JSONObject json = new JSONObject();
            Map<String, Object> params=new HashMap<>();
            params.put("user", Constant.user);
            params.put("secret",Constant.secret);
            Record maxTeam= Db.findFirst("SELECT max(update_time) as update_time FROM tbl_competition_team");
            params.put("time",maxTeam.getLong("update_time")/1000);
//            params.put("id",start);
//            params.put("limit","1");

            json= HttpHelpUtil.sendRequestGet2Status("https://open.sportnanoapi.com/api/v5/football/team/list",params);
            if("0".equals(json.getString("code"))){
                JSONArray arr=json.getJSONArray("results");
                for(int i=0;i<arr.size();i++){
                    JSONObject obj=arr.getJSONObject(i);
                    Record ls= Db.findFirst("select * from tbl_competition_team where  id='"+obj.getInteger("id")+"'");
                    if(ls!=null){
                        if(ls.getLong("update_time")/1000<obj.getLong("updated_at")){
                            ls.set("league_match",obj.getInteger("competition_id")).set("introduce",obj.getString("website"))
                                    .set("name",obj.getString("name_zh")).set("name_en",obj.getString("name_en")).set("name_zht",obj.getString("name_zht"))
                                    .set("cover",obj.getString("logo")).set("update_time", obj.getLong("updated_at")*1000);
                            Db.update("tbl_competition_team",ls);
                        }
                    }else{
                        ls=new Record();
                        ls.set("id", obj.getInteger("id")).set("league_match",obj.getInteger("competition_id")).set("introduce",obj.getString("website"))
                                .set("name",obj.getString("name_zh")).set("name_en",obj.getString("name_en")).set("name_zht",obj.getString("name_zht"))
                                .set("cover",obj.getString("logo")).set("update_time",obj.getLong("updated_at")*1000)
                                .set("create_time",System.currentTimeMillis());
                        Db.save("tbl_competition_team",ls);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void addTeamAll() {
        try {
            JSONObject json = new JSONObject();
            Map<String, Object> params=new HashMap<>();
            params.put("user", Constant.user);
            params.put("secret",Constant.secret);
            params.put("id",start);
//            params.put("limit","1");

            json= HttpHelpUtil.sendRequestGet2Status("https://open.sportnanoapi.com/api/v5/football/team/list",params);
            if("0".equals(json.getString("code"))){
                JSONArray arr=json.getJSONArray("results");
                for(int i=0;i<arr.size();i++){
                    JSONObject obj=arr.getJSONObject(i);
                    Record ls= Db.findFirst("select * from tbl_competition_team where  id='"+obj.getInteger("id")+"'");
                    if(ls!=null){
                        if(ls.getLong("update_time")/1000<obj.getLong("updated_at")){
                            ls.set("league_match",obj.getInteger("competition_id")).set("introduce",obj.getString("website"))
                                    .set("name",obj.getString("name_zh")).set("name_en",obj.getString("name_en")).set("name_zht",obj.getString("name_zht"))
                                    .set("cover",obj.getString("logo")).set("update_time", obj.getLong("updated_at")*1000);
                            Db.update("tbl_competition_team",ls);
                        }
                    }else{
                        ls=new Record();
                        ls.set("id", obj.getInteger("id")).set("league_match",obj.getInteger("competition_id")).set("introduce",obj.getString("website"))
                                .set("name",obj.getString("name_zh")).set("name_en",obj.getString("name_en")).set("name_zht",obj.getString("name_zht"))
                                .set("cover",obj.getString("logo")).set("update_time",obj.getLong("updated_at")*1000)
                        .set("create_time",System.currentTimeMillis());
                        Db.save("tbl_competition_team",ls);
                    }
                }
                if(json.getJSONObject("query").getInteger("total")==1000){
                    start=json.getJSONObject("query").getInteger("max_id");
                    addTeamAll();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //添加联赛信息
    private void addLeagueMatch() {
//        String[] lst=Constant.leagusMatchs.split(",");
        try {
            JSONObject json = new JSONObject();
            Map<String, Object> params=new HashMap<>();
            params.put("user",Constant.user);
            params.put("secret",Constant.secret);
//                params.put("id",id);
            Record maxTeam= Db.findFirst("SELECT max(update_time) as update_time FROM tbl_league_match");
            params.put("time",maxTeam.getLong("update_time")/1000);

            json= HttpHelpUtil.sendRequestGet2Status("https://open.sportnanoapi.com/api/v5/football/competition/list",params);
            if("0".equals(json.getString("code"))){
                JSONArray arr=json.getJSONArray("results");
                for(int i=0;i<arr.size();i++){
                    JSONObject obj=arr.getJSONObject(i);
                    Record ls=Db.findFirst("select * from tbl_league_match where  league_match_id='"+obj.getInteger("id")+"'");
                    if(ls!=null){
                        if(ls.getLong("update_time")/1000<obj.getLong("updated_at")){
                            ls.set("name",obj.getString("name_zh")).set("name_en",obj.getString("name_en")).set("name_zht",obj.getString("name_zht"))
                                    .set("logo",obj.getString("logo")).set("update_time",obj.getLong("updated_at")*1000);
                            Db.update("tbl_league_match",ls);
                        }
                    }else{
                        ls=new Record();
                        ls.set("id",TmFunctions.getKeyStr("")).set("league_match_id",obj.getInteger("id"))
                                .set("name",obj.getString("name_zh")).set("name_en",obj.getString("name_en")).set("name_zht",obj.getString("name_zht"))
                                .set("logo",obj.getString("logo")).set("update_time",obj.getLong("updated_at")*1000);
                        Db.save("tbl_league_match",ls);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void addLeagueMatchAll(){
        try {
            JSONObject json = new JSONObject();
            Map<String, Object> params=new HashMap<>();
            params.put("user", Constant.user);
            params.put("secret",Constant.secret);
            params.put("id",start);
//            params.put("limit","1");

            json= HttpHelpUtil.sendRequestGet2Status("https://open.sportnanoapi.com/api/v5/football/competition/list",params);
            if("0".equals(json.getString("code"))){
                JSONArray arr=json.getJSONArray("results");
                for(int i=0;i<arr.size();i++){
                    JSONObject obj=arr.getJSONObject(i);
                    Record ls= Db.findFirst("select * from tbl_league_match where  league_match_id='"+obj.getInteger("id")+"'");
                    if(ls!=null){
                        if(ls.getLong("update_time")/1000<obj.getLong("updated_at")){
                            ls.set("name",obj.getString("name_zh")).set("name_en",obj.getString("name_en")).set("name_zht",obj.getString("name_zht"))
                                    .set("logo",obj.getString("logo")).set("update_time", obj.getLong("updated_at")*1000);
                            Db.update("tbl_league_match",ls);
                        }
                    }else{
                        ls=new Record();
                        ls.set("id", TmFunctions.getKeyStr("")).set("league_match_id",obj.getInteger("id"))
                                .set("name",obj.getString("name_zh")).set("name_en",obj.getString("name_en")).set("name_zht",obj.getString("name_zht"))
                                .set("logo",obj.getString("logo")).set("update_time",obj.getLong("updated_at")*1000);
                        Db.save("tbl_league_match",ls);
                    }
                }
                if(json.getJSONObject("query").getInteger("total")==1000){
                    start=json.getJSONObject("query").getInteger("max_id");
                    addLeagueMatchAll();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void showmsg(String msg){
        System.out.println(msg);
        logger.debug(msg);
    }

//    public static void main(String[] args) {
//        JSONObject json = new JSONObject();
//        Map<String, Object> params=new HashMap<>();
//        params.put("user","yjkj");
//        params.put("secret","f38c879f26099bbd4877b7daf11011dc");
//        params.put("date","20220501");
//
//        json= HttpHelpUtil.sendRequestGet2Status(url,params);
//        System.out.println(json);
//        Record rd=new Record();
//        rd.set("id", TmFunctions.getKeyStr("")).set("res",json).set("create_time", System.currentTimeMillis());
//        Db.save("test_11",rd);
//    }
}
