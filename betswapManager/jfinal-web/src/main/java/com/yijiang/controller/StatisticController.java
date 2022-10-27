package com.yijiang.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.yijiang.model.SysuserModel;
import com.yijiang.model.selfdefined.ShopStatisticDTO;
import com.yijiang.model.selfdefined.movieStatisticDTO;
import com.yijiang.util.TmDateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

public class StatisticController extends Controller {

    private final static String menuflg="13";

    public void index() {
        setAttr("menuflg", menuflg);
        SysuserModel ry = getSessionAttr("userSession");
        setAttr("user", ry);
        renderJsp("shop.jsp");
    }

    public void ShopStatisitc(){
        setAttr("menuflg",menuflg);
        SysuserModel ry=getSessionAttr("userSession");
        setAttr("user", ry);
        renderJsp("shop.jsp");
    }

    Date nextIter(Date d, int type){
        switch (type){
            case 1:
                return TmDateUtil.getDateAfterYears(d,1);
            case 2:
                return TmDateUtil.getDateAfterMonths(d,1);
            case 3:
                return TmDateUtil.getDateAfterDays(d,1);
        }
        return null;
    }

	public void shopstat(){
		String sdate=getPara("sdate");
		String edate=getPara("edate");
		String tjld=getPara("tjld");
		int unit=Integer.parseInt(tjld);


        Date startdate  = TmDateUtil.stringToDate(sdate,"yyyy-MM-dd");
        Date enddate    = TmDateUtil.stringToDate(edate,"yyyy-MM-dd");
        if(!enddate.after(startdate)) return;

        String format1,format2;
        if(unit==3){
            format1="yyyy-MM-dd";
            format2="'%Y-%m-%d'";
        }else if(unit==2){
            format1="yyyy-MM";
            format2="'%Y-%m'";
        }else{
            format1="yyyy";
            format2="'%Y'";
        }

        List<ShopStatisticDTO> statlist= new ArrayList<>();
        String endDateStr = new SimpleDateFormat(format1).format(enddate);
        for(Date iterdate=startdate;;iterdate=nextIter(iterdate,unit)){
            String thisDateStr = new SimpleDateFormat(format1).format(iterdate);
            statlist.add(new ShopStatisticDTO(thisDateStr));
            if(thisDateStr.equals(endDateStr)) break;
        }

        String moviesql   ="select count(*)        as c,DATE_FORMAT(opinion_time,"+format2+") as t from goods_apply_record          where opinion_time>='"+sdate+"' and opinion_time<='"+edate+"' GROUP BY DATE_FORMAT(opinion_time,"+format2+")";
        String shopfiatsql="select sum(amount)   as c,DATE_FORMAT(create_time,"+format2+") as t from buy_shop_record where pay_type=0 and create_time>='"+sdate+"' and create_time<='"+edate+"' GROUP BY DATE_FORMAT(create_time,"+format2+")";
        String shopusdtsql="select sum(amount) as c,DATE_FORMAT(create_time,"+format2+") as t from buy_shop_record where pay_type=1 and create_time>='"+sdate+"' and create_time<='"+edate+"' GROUP BY DATE_FORMAT(create_time,"+format2+")";

        List<Record> applyList=Db.find(moviesql);
        List<Record> fiatList=Db.find(shopfiatsql);
        List<Record> usdtList=Db.find(shopusdtsql);

        for(int i=0;i<statlist.size();i++){
            String thisdate=statlist.get(i).timestr;

            for(int j=0;j<applyList.size();j++){
                if(applyList.get(j).getStr("t").equals(thisdate)){
                    if(applyList.get(j).getInt("c")!=null) {
                        statlist.get(i).goodsApply = applyList.get(j).getInt("c");
                    }
                    else{
                        statlist.get(i).goodsApply=0;
                    }
                    applyList.remove(j);
                    break;
                }
            }
            for(int j=0;j<fiatList.size();j++){
                if(fiatList.get(j).getStr("t").equals(thisdate)){
                    if(fiatList.get(j).getInt("c")!=null) {
                        statlist.get(i).shopFiat = fiatList.get(j).getInt("c");
                    }
                    else{
                        statlist.get(i).shopFiat=0;
                    }
                    fiatList.remove(j);
                    break;
                }
            }
            for(int j=0;j<usdtList.size();j++){
                if(usdtList.get(j).getStr("t").equals(thisdate)){
                    if(usdtList.get(j).getInt("c")!=null) {
                        statlist.get(i).shopUsdt = usdtList.get(j).getInt("c");
                    }
                    else{
                        statlist.get(i).shopUsdt = 0;
                    }
                    usdtList.remove(j);
                    break;
                }
            }
        }
        setAttr("statlist",statlist);
        renderJson();
    }


	public void MovieStatisitc(){
        setAttr("menuflg", menuflg);
        SysuserModel ry = getSessionAttr("userSession");
        setAttr("user", ry);
        renderJsp("movie.jsp");

    }



    public void moviestat(){
        String sdate=getPara("sdate");
        String edate=getPara("edate");
        int unit=getParaToInt("tjld");

        Date startdate  = TmDateUtil.stringToDate(sdate,"yyyy-MM-dd");
        Date enddate    = TmDateUtil.stringToDate(edate,"yyyy-MM-dd");
        if(!enddate.after(startdate)) return;

        String format1,format2;
        if(unit==3){
            format1="yyyy-MM-dd";
            format2="'%Y-%m-%d'";
        }else if(unit==2){
            format1="yyyy-MM";
            format2="'%Y-%m'";
        }else{
            format1="yyyy";
            format2="'%Y'";
        }

        List<movieStatisticDTO> statlist= new ArrayList<>();
        for(Date iterdate=startdate;!iterdate.after(enddate);iterdate=nextIter(iterdate,unit)){
            statlist.add(new movieStatisticDTO(new SimpleDateFormat(format1).format(iterdate)));
        }

        String fiatsql="select sum(amount) as s,DATE_FORMAT(create_time,"+format2+") as t from buy_movie_record where pay_type=0 and create_time>='"+sdate+"' and create_time<='"+edate+"' GROUP BY DATE_FORMAT(create_time,"+format2+")";
        String usdtsql="select sum(amount) as s,DATE_FORMAT(create_time,"+format2+") as t from buy_movie_record where pay_type=1 and create_time>='"+sdate+"' and create_time<='"+edate+"' GROUP BY DATE_FORMAT(create_time,"+format2+")";

        List<Record> fiatList=Db.find(fiatsql);
        List<Record> usdtList=Db.find(usdtsql);

        for(int i=0;i<statlist.size();i++){
            String thisdate=statlist.get(i).timestr;

            for(int j=0;j<fiatList.size();j++){
                if(fiatList.get(j).getStr("t").equals(thisdate)){
                    statlist.get(i).Fiat=fiatList.get(j).getInt("s");
                    fiatList.remove(j);
                    break;
                }
            }
            for(int j=0;j<usdtList.size();j++){
                if(usdtList.get(j).getStr("t").equals(thisdate)){
                    statlist.get(i).Usdt=usdtList.get(j).getInt("s");
                    usdtList.remove(j);
                    break;
                }
            }
        }
        setAttr("statlist",statlist);
        renderJson();
    }

	public void UserStatisitc(){
        setAttr("menuflg", menuflg);
        setAttr("user", getSessionAttr("userSession"));
        renderJsp("user.jsp");
    }

    public void UserStat(){
        int nuser = Db.find("SELECT COUNT(*)as c FROM user").get(0).getInt("c");
        int nshop = Db.find("SELECT COUNT(*)as c FROM shop").get(0).getInt("c");
        List<Record> movie = Db.find("SELECT movie_rank as r,COUNT(*)as c FROM user GROUP BY movie_rank");
        List<Record> shop  = Db.find("SELECT shop_rank  as r,COUNT(*)as c FROM user GROUP BY shop_rank");
        setAttr("nuser", nuser);
        setAttr("nshop", nshop);
        setAttr("movie", Record2DTO(movie));
        setAttr("shop",  Record2DTO(shop));
        renderJson();
    }

    int[] Record2DTO(List<Record> l){
        int[] n= new int[7];
        for(Record r:l){
            n[r.getInt("r")]=r.getInt("c");
        }
        return n;
    }

}














