package com.betswap.market.app.system.impl;

import cn.hutool.json.JSONObject;
import com.betswap.market.app.system.service.SystemService;
import com.betswap.market.client.quotation.MatchStatusEnum;
import com.betswap.market.client.system.converter.DictionaryConverter;
import com.betswap.market.client.system.converter.SysUserConverter;
import com.betswap.market.client.system.converter.SysVersionUpgradeConverter;
import com.betswap.market.client.system.dto.DictionaryDTO;
import com.betswap.market.client.system.dto.SysVersionUpgradeDTO;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.quotation.dao.LeagueMatchDao;
import com.betswap.market.infrastruture.quotation.entity.LeagueMatchEntity;
import com.betswap.market.infrastruture.system.dao.*;
import com.betswap.market.infrastruture.system.entity.*;
import com.betswap.market.infrastruture.utils.String.DelTagsUtil;
import com.betswap.market.infrastruture.utils.constant.ConstantUtil;
import com.betswap.market.infrastruture.utils.constant.TmDateUtil;
import com.betswap.market.infrastruture.utils.helper.file.PictureHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.*;


@Service
@Slf4j
public class SystemServiceImpl implements SystemService {
    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private SysParaDao sysParaDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysArticleDao sysArticleDao;

    @Autowired
    private PictureHelper pictureHelper;

    @Autowired
    private SysLogDao sysLogDao;

    @Autowired
    private SysProtocolDao sysProtocolDao;
    @Autowired
    private LeagueMatchDao leagueMatchDao;

    @Autowired
    private SysVersionUpgradeDao sysVersionUpgradeDao;

    @Override
    public ServerResponse queryDic(String userId, String dicType) {
        List<DictionaryEntity> list=new ArrayList<>();
        if(StringUtils.isNotBlank(dicType)){
            list=dictionaryDao.findAllByDicTypeIn( dicType.split(","));
        }else{
           list= dictionaryDao.findAll();
        }
        List<DictionaryDTO> lt=new ArrayList<>();
        for(DictionaryEntity dic:list){
            DictionaryDTO dto= DictionaryConverter.INSTANCE.entity2dto(dic);
            lt.add(dto);
        }
        return  ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(lt);
    }

    @Override
    public ServerResponse queryDicNameByCodeAndType(String code, String type) {
        String name=dictionaryDao.queryDicNameByCodeAndType(code,type);
        return  ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(name);
    }

    @Override
    public ServerResponse findSysParaEntityById(int id) {
        SysParaEntity sys=sysParaDao.findSysParaEntityById(id);
        if(sys==null)
             return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
        return  ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(sys);
    }

    @Override
    public ServerResponse findCustomerService(String typeFlg) {
        SysUserEntity user= sysUserDao.findByTypeFlg(typeFlg);
        if(user==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(SysUserConverter.INSTANCE.entity2dto(user));
    }

    @Override
    public ServerResponse saveArticle(String typeFlag,MultipartFile file,String id,String title,String subtitle,String webUrl,String content,String userId){
        String coverURL="";
        if(file!=null) {
            coverURL = pictureHelper.savePicture(file, "otherService", "article");
        }
        SysUserEntity sysUserEntity=sysUserDao.findSysUserEntityByUserId(userId);
        boolean flag=true;
        SysArticleEntity sysArticleEntity=null;
        boolean isNewrecord=StringUtils.isBlank(id);
        String editInfo="";
        if(isNewrecord){
            id= DelTagsUtil.getJlbh("W");
            sysArticleEntity = SysArticleEntity.builder()
                    .id(id)
                    .title(title)
                    .subtitle(subtitle)
                    .cover(coverURL)
                    .typeFlag(typeFlag)
                    .createTime(System.currentTimeMillis())
                    .userId(userId)
                    .userName(sysUserEntity.getUserName())
                    .userPhone(sysUserEntity.getUserPhone())
                    .build();
            if(typeFlag.equals("0")){
                sysArticleEntity.setContent(content);
            }
            else{
                sysArticleEntity.setWebUrl(webUrl);
            }
        }
        else{
            sysArticleEntity = sysArticleDao.findSysArticleEntityById(id);
            //先进行修改值的判断
            //if(sysArticleEntity.getContent()==null||!sysArticleEntity.getContent().equals(content)){
                //editInfo+="content:"+sysArticleEntity.getContent()+"-->"+content;
            //}
            if(sysArticleEntity.getWebUrl()==null||!sysArticleEntity.getWebUrl().equals(webUrl)){
                editInfo+="web_url:"+sysArticleEntity.getWebUrl()+"-->"+content;
            }
            if(!sysArticleEntity.getTypeFlag().equals(typeFlag)){
                editInfo+="type_flag:"+sysArticleEntity.getTypeFlag()+"-->"+typeFlag;
            }
            if(StringUtils.isNoneBlank(coverURL)&&!sysArticleEntity.getCover().equals(coverURL)){
                editInfo+="cover:"+sysArticleEntity.getCover()+"-->"+coverURL;
            }
            if(!sysArticleEntity.getTitle().equals(title)){
                editInfo+="title:"+sysArticleEntity.getTitle()+"-->"+title;
            }
            if(sysArticleEntity.getSubtitle()==null||!sysArticleEntity.getSubtitle().equals(subtitle)){
                editInfo+="subtitle:"+sysArticleEntity.getSubtitle()+"-->"+subtitle;
            }
            //然后赋值
            sysArticleEntity.setTitle(title);
            sysArticleEntity.setSubtitle(subtitle);
            sysArticleEntity.setUpdateTime(System.currentTimeMillis());
            sysArticleEntity.setTypeFlag(typeFlag);
            if(typeFlag.equals("0")){
                sysArticleEntity.setContent(content);
            }
            else{
                sysArticleEntity.setWebUrl(webUrl);
            }
            if(StringUtils.isNoneBlank(coverURL)){
                sysArticleEntity.setCover(coverURL);
            }
        }
        try {
            sysArticleDao.save(sysArticleEntity);
            //写日志
            SysLogEntity sysLogEntity= SysLogEntity.builder()
                    .id(DelTagsUtil.getJlbh(""))
                    .optTable("sys_article")
                    .optKeyId(id)
                    .optTime(System.currentTimeMillis())
                    .delFlg(0)
                    .optUserName(sysUserEntity.getUserName())
                    .keyword(isNewrecord?"new record":"edit record")
                    .build();
            if(!editInfo.equals("")){
                sysLogEntity.setContent(editInfo);
            }
            sysLogDao.save(sysLogEntity);
        }
        catch(Exception e){
            e.printStackTrace();
            flag=false;
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("id",id);
        resultJson.put("flag",flag);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse findParaValueByTypeFlag(int typeFlag) {
        String sys=sysProtocolDao.findParaValueByTypeFlag(typeFlag);
        if(sys==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
        return  ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(sys);
    }

    @Override
    public ServerResponse findMatchStatus() {
        Map<Integer, String> map=new HashMap<>();
        MatchStatusEnum[] arr=MatchStatusEnum.values();
        for(MatchStatusEnum key:arr){
            map.put(key.type,key.description);
        }
        return  ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(map);
    }

    @Override
    public ServerResponse findLeagueMatchS() {
        String[] strs=ConstantUtil.leagusMatchs.split(",");
        List<LeagueMatchEntity> lst= leagueMatchDao.findAllByMatchIDIn(Arrays.asList(strs));
        return  ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(lst);
    }

    @Override
    public ServerResponse findLatestVersion() {
        SysVersionUpgradeEntity entity =sysVersionUpgradeDao.findLatestVersion();
        SysVersionUpgradeDTO dto= SysVersionUpgradeConverter.INSTANCE.entity2dto(entity);
        return  ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
    }
}
