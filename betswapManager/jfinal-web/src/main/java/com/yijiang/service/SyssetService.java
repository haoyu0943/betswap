package com.yijiang.service;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.yijiang.model.*;
import com.yijiang.util.Md5Crypt;
import com.yijiang.util.NumberDefine;
import com.yijiang.util.TmDateUtil;
import com.yijiang.util.TzStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyssetService {
    public static SyssetService syssetService;
    Logger log = Logger.getLogger(SyssetService.class);

    public static SyssetService getInstance() {
        if (syssetService == null) {
            synchronized (SyssetService.class) {
                if (syssetService == null) {
                    syssetService = new SyssetService();
                }
            }
        }
        return syssetService;
    }

    public boolean initMm(String userid){
        SysuserModel rymod=SysuserModel.dao.findFirst("select * from sys_user where user_id=?",userid);
        boolean flag=rymod.set("password", Md5Crypt.EncoderByMd5("111111")).update();
        return flag;
    }

    public Map<String, Object> findPageCodeByPara(HashMap map) {
        String codetype= TzStringUtils.checkNull(map.get("codetype"));
        String draw = TzStringUtils.checkNull(map.get("draw"));
        int start = Integer.parseInt((String)map.get("start"));
        int length = Integer.parseInt((String)map.get("length"));
        int pageNo = start /  length + 1;
        String sql_from="from dictionary ";
        String sql_where="where dic_type='"+codetype+"'";
        sql_where+=" order by code";
        String sql="select * ";
        Page<Record> codePage = Db.paginate(pageNo, length, sql,sql_from+sql_where);
        List<Record> codelist = codePage.getList();
        for(int i=0;i<codelist.size();i++){
            codelist.get(i).set("rownum",start+i+1);
        }
        int count=codePage.getTotalRow();
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("data", codelist);
        info.put("recordsTotal", count);
        info.put("recordsFiltered", count);
        info.put("draw", draw);
        return info;
    }


}
