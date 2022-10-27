package com.betswap.market.app.quotation;

import com.alibaba.fastjson.JSONObject;
import com.betswap.market.client.common.YesOrNoStatusEnum;
import com.betswap.market.client.quotation.MatchStatusEnum;
import com.betswap.market.client.quotation.OrderStatusEnum;
import com.betswap.market.client.quotation.QuotationTypeEnum;
import com.betswap.market.client.quotation.cmd.BetPayCmd;
import com.betswap.market.client.quotation.cmd.BetPayWebCmd;
import com.betswap.market.client.quotation.cmd.QuotationCmd;
import com.betswap.market.client.quotation.converter.MatchConverter;
import com.betswap.market.client.quotation.converter.QuotationConverter;
import com.betswap.market.client.quotation.dto.*;
import com.betswap.market.client.quotation.qry.BetQuery;
import com.betswap.market.client.quotation.qry.MatchAllQuery;
import com.betswap.market.client.user.vo.dto.TransMoneyDto;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.quotation.dao.*;
import com.betswap.market.infrastruture.quotation.entity.*;
import com.betswap.market.infrastruture.system.dao.SysAddressDao;
import com.betswap.market.infrastruture.system.dao.SysAddressTransferRecordDao;
import com.betswap.market.infrastruture.system.dao.SysParaDao;
import com.betswap.market.infrastruture.system.entity.SysAddressEntity;
import com.betswap.market.infrastruture.system.entity.SysAddressTransferRecordEntity;
import com.betswap.market.infrastruture.user.dao.UserDao;
import com.betswap.market.infrastruture.user.entity.UserEntity;
import com.betswap.market.infrastruture.utils.Md5.DesUtil;
import com.betswap.market.infrastruture.utils.constant.ConstantUtil;
import com.betswap.market.infrastruture.utils.constant.DateUtils;
import com.betswap.market.infrastruture.utils.constant.TmFunctions;
import com.betswap.market.infrastruture.utils.redis.RedisUtil;
import com.betswap.market.infrastruture.utils.transaction.TransactionUtils;
import io.rong.models.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class QuotationServiceImpl implements QuotationService {


    public final static Map quMap = new HashMap();

    @Autowired
    private QuotationDao quotationDao;

    @Autowired
    private SysParaDao sysParaDao;

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private CompetitionTeamDao competitionTeamDao;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BetDao betDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private LeagueMatchDao leagueMatchDao;

    @Autowired
    private MatchAddressDao matchAddressDao;

    @Autowired
    private SysAddressDao sysAddressDao;
    @Autowired
    private SysAddressTransferRecordDao sysAddressTransferRecordDao;

    @Autowired
    private PlatformTransactionsRecordDao platformTransactionsRecordDao;

    @Transactional
    @Override
    public ServerResponse createQuotations(String userId, List<QuotationCmd> cmds) {
        UserEntity user= userDao.findByUserId(userId);
        if(user==null||StringUtils.isBlank(user.getWalkey()))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.WALLET_NOT_SUCH);
        Long t = System.currentTimeMillis();
        List<QuotationEntity> lst=new ArrayList<>();
        BigDecimal total=new BigDecimal(0);
        String s=sysParaDao.findParaValueById(ConstantUtil.timeOut);//截止时间
//        String ptWalletAddress=sysParaDao.findParaValueById(ConstantUtil.tronAddressVal);//平台钱包地址

        String tronUrl=sysParaDao.findParaValueById(ConstantUtil.tronUrlVal);//钱包服务地址
        for(QuotationCmd cmd:cmds){
            String matchId=cmd.getMatchId();
            if(!checkTime(s,matchId))
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.TIME_OUT);

            QuotationEntity entity=QuotationConverter.INSTANCE.cmd2entity(cmd);
            BigDecimal bond= new BigDecimal(entity.getUnitAmount()).multiply(cmd.getOdds());
            entity.setBond(bond);
            entity.setSurplusBond(bond);
            entity.setStatus(OrderStatusEnum.ORDER_INCHAIN.status);
            entity.setCouNum(0);
            total=total.add(bond);
            entity.setUserId(userId);
            if(StringUtils.isBlank(cmd.getId())){
                entity.setId(TmFunctions.getKeyStr("PK"));
                entity.setCreateTime(t);
            }else{
                entity.setUpdateTime(t);
            }
            lst.add(entity);
        }
        //总的保证金 小于等于0 时
        if(total.compareTo(new BigDecimal(0))<=0)
            return  ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ABNORMAL_AMOUNT);

        //判断并创建比赛账户--并转账TRX
        MatchEntity match=matchDao.findFirstById(lst.get(0).getMatchId());
        if(match==null)
            return  ServerResponse.getInstance().fail().responseEnum(ResponseEnum.DATA_NOT_EXIST);
        MatchAddressEntity matchAddress= matchAddressDao.findFirstByMatchId(match.getId());
        if(matchAddress==null){
            SysAddressEntity sysA =sysAddressDao.findAddressByType(2);//TRx类型 私钥
            if(sysA==null||StringUtils.isBlank(sysA.getWalkey()))
                return  ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ADDRESS_NOT_EXIST);
            try{
                JSONObject json= TransactionUtils.createTronAdr(tronUrl);
                if("SUCCESS".equals(json.getString("code"))){
                    matchAddress=new MatchAddressEntity();
                    matchAddress.setId(TmFunctions.getKeyStr("A"));
                    matchAddress.setHomeTeam(match.getHomeTeam());
                    matchAddress.setHomeTeam_en(match.getHomeTeam_en());
                    matchAddress.setHomeTeam_zht(match.getHomeTeam_zht());
                    matchAddress.setGuestTeam(match.getGuestTeam());
                    matchAddress.setGuestTeam_en(match.getGuestTeam_en());
                    matchAddress.setGuestTeam_zht(match.getGuestTeam_zht());
                    matchAddress.setMatchId(match.getId());
                    matchAddress.setMatchTime(match.getMatchTime());
                    matchAddress.setCreateTime(System.currentTimeMillis());
                    matchAddress.setWalkey(DesUtil.encrypt(json.getString("privateKey")));
                    matchAddress.setWalletAddress(json.getString("address"));
                    matchAddress.setStatus(YesOrNoStatusEnum.NO.status);
                    matchAddress.setIntoGASStatus(YesOrNoStatusEnum.NO.status);
                    matchAddressDao.save(matchAddress);

                    //转账固定TRX 到该账户里
                    String trxV= sysParaDao.findParaValueById(ConstantUtil.addressTRXVal);
                    JSONObject json1= TransactionUtils.outToAddressTRX(DesUtil.decrypt(sysA.getWalkey()),matchAddress.getWalletAddress(), TmFunctions.changeString(trxV),tronUrl);
                    if("SUCCESS".equals(json1.getString("code"))){
                        //创建转账记录
                        SysAddressTransferRecordEntity rd=new  SysAddressTransferRecordEntity();
                        rd.setId(TmFunctions.getKeyStr("SR"));
                        rd.setAmount(TmFunctions.changeBigDecimalT(trxV));
                        rd.setInOrOut("2");// 转出
                        rd.setType("2");//TRX
                        rd.setSysAddressId(sysA.getId());
                        rd.setFromAddress(sysA.getWalletAddress());
                        rd.setToAddress(matchAddress.getWalletAddress());
                        rd.setCreateTime(System.currentTimeMillis());
                        rd.setRemark("首次创建比赛账户充值");
                        sysAddressTransferRecordDao.save(rd);
                    }else{
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PAY_FAILED);
                    }

                }
            }catch (Exception e){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }


//        String ptWalletAddress=leagueMatchDao.findAddressById(lst.get(0).getLeagueMatch());//平台钱包地址--默认 是一次盘口的
        try{
            JSONObject json= TransactionUtils.outToAddressUsdt(DesUtil.decrypt(user.getWalkey()),matchAddress.getWalletAddress(), TmFunctions.changeString(total),tronUrl);
            if("SUCCESS".equals(json.getString("code"))){
                TransMoneyDto dto=TransMoneyDto.builder()
                        .hash(json.getString("hash"))
                        .result(json.getString("result"))
                        .code(json.getString("code"))
                        .message(json.getString("message"))
                        .build();
                //赋值并保存
                for(QuotationEntity entity:lst){
                    entity.setTransactionHash(json.getString("transactionHash"));
                }
                quotationDao.saveAll(lst);
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
            }else{
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PAY_FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PAY_FAILED);
        }
    }

    @Override
    public ServerResponse queryMathByCondition(MatchAllQuery qry) {
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();

        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" select t.id,t.create_time,t.end_time,t.guest_score,t.guest_team_id,t.home_score,t.home_team_id," +
                    " t.league_match,t.match_time,t.status,t.update_time,t.guest_team,t.home_team,t.if_hot,t.if_close" +
                    ",t.guest_team_en,t.guest_team_zht,t.home_team_en,t.home_team_zht,t.guest_team_cover,t.home_team_cover " +
                    "  from match_bet as t where  t.status !="+MatchStatusEnum.MATCH_YWS.type);
            sqltotal.append(" select count(1) from match_bet as t where  t.status !="+MatchStatusEnum.MATCH_YWS.type);

            sqljoint.append(" and  t.if_close=  '"+YesOrNoStatusEnum.NO.status+"'");
            sqltotal.append(" and  t.if_close=  '"+YesOrNoStatusEnum.NO.status+"'");

            if(qry.getIfHot()!=null&&qry.getIfHot()==1){
                sqljoint.append(" and  t.if_hot=  '"+qry.getIfHot()+"'");
                sqltotal.append(" and  t.if_hot=  '"+qry.getIfHot()+"'");
            }
            if(StringUtils.isNoneBlank(qry.getKeyword())){
                sqljoint.append(" and concat(t.home_team,',',t.guest_team) like '%"+qry.getKeyword()+"%'  ");
                sqltotal.append(" and concat(t.home_team,',',t.guest_team)  like '%"+qry.getKeyword()+"%'  ");
            }
            if(qry.getLeagueMatch()!=null){
                sqljoint.append(" and  t.league_match=  '"+qry.getLeagueMatch()+"'");
                sqltotal.append(" and  t.league_match=  '"+qry.getLeagueMatch()+"'");
            }

            if(qry.getStartTime()!=null){
                sqljoint.append(" and  t.match_time>=  "+qry.getStartTime()+"");
                sqltotal.append(" and  t.match_time>=  "+qry.getStartTime()+"");
            }
            if(qry.getEndTime()!=null){
                sqljoint.append(" and  t.match_time<  "+qry.getEndTime()+"");
                sqltotal.append(" and  t.match_time<  "+qry.getEndTime()+"");
            }
//            if(qry.getMatchTimeType()!=null){
//                Date start=new Date();
//                Date end=new Date();
//                switch (qry.getMatchTimeType()){
//                    case 1:start= DateUtils.getDayBegin(); end=DateUtils.getDayEnd();break;//今天
//                    case 2:start=DateUtils.getDayEnd(); end=DateUtils.getEndDayOfSomeDay(1);break;//明天
//                    case 3:start=DateUtils.getEndDayOfSomeDay(1); end=DateUtils.getEndDayOfSomeDay(2);break;//后天
//                    case 4:start=DateUtils.getDayBegin(); end=DateUtils.getEndDayOfSomeDay(7);break;//未来一周
//                    case 5:start=DateUtils.getDayBegin(); end=DateUtils.getEndDayOfSomeDay(30);break;//未来一月
//                    case 6:start=DateUtils.getEndDayOfSomeDay(30); end=DateUtils.getEndDayOfSomeDay(365);break;//更远 先设置为1年
//                    default:
//                }
//                if(start!=null){
//                    sqljoint.append(" and  t.match_time>=  "+start.getTime()+"");
//                    sqltotal.append(" and  t.match_time>=  "+start.getTime()+"");
//                }
//                if(end!=null){
//                    sqljoint.append(" and  t.match_time<  "+end.getTime()+"");
//                    sqltotal.append(" and  t.match_time<  "+end.getTime()+"");
//                }
//            }
            if(qry.getOrderType()==0){
                sqljoint.append("  ORDER BY t.amount desc,t.count desc");
            }else{
                sqljoint.append("  ORDER BY t.match_time asc");
            }

            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, MatchEntity.class);
            List<MatchEntity> list=query.getResultList();
            List<MatchDTO> dtos    = new ArrayList<>();
//            String baseUrl=sysParaDao.findParaValueById(ConstantUtil.localURL);
            int day=Integer.valueOf(sysParaDao.findParaValueById(ConstantUtil.openDay));
            for(MatchEntity entity : list){
                MatchDTO dto = MatchConverter.INSTANCE.entity2dto(entity);
                dto.setIfBetAndQ(checkIfBetAndQ(dto.getMatchTime(),day));
//                //赔率计算
//                List<List<Object>> lst=quotationDao.findFullCourtMaxoddsByMatchId(entity.getId());
                dto.setTieOdds(betDao.findFullCourtHotOddsByMatchIdAndTie(entity.getId()));//平局
                dto.setHomeOdds(betDao.findFullCourtHotOddsByMatchIdAndWinTeamId(entity.getId(),entity.getHomeTeamId()));
                dto.setGuestOdds(betDao.findFullCourtHotOddsByMatchIdAndWinTeamId(entity.getId(),entity.getGuestTeamId()));

                //让分
                List<List<Object>> rfHome =betDao.findRFHotOddsByMatchId(entity.getId(),entity.getHomeTeamId());
                List<List<Object>> rfGuest =betDao.findRFHotOddsByMatchId(entity.getId(),entity.getGuestTeamId());
                if(rfHome!=null&&rfHome.size()>0){
                    dto.setRfHomeOdds(TmFunctions.changeBigDecimal(rfHome.get(0).get(0)));
                    dto.setHomeGivePoints(-TmFunctions.changFloat(rfHome.get(0).get(1)));
                }

                if(rfGuest!=null&&rfGuest.size()>0){
                    dto.setRfGuestOdds(TmFunctions.changeBigDecimal(rfGuest.get(0).get(0)));
                    dto.setGuestGivePoints(TmFunctions.changFloat(rfGuest.get(0).get(1)));
                }

                //比大小
                List<List<Object>> dxHome =betDao.findDxHotOddsByMatchId(entity.getId(),entity.getHomeTeamId());//大于 时存主队
                List<List<Object>> dxGuest =betDao.findDxHotOddsByMatchId(entity.getId(),entity.getGuestTeamId());//小于存客队

                if(dxHome!=null&&dxHome.size()>0){
                    dto.setDxHomeOdds(TmFunctions.changeBigDecimal(dxHome.get(0).get(0)));
                    dto.setHomeSpecificSize(">"+TmFunctions.changFloat(dxHome.get(0).get(1)));
                }
                if(dxGuest!=null&&dxGuest.size()>0){
                    dto.setDxGuestOdds(TmFunctions.changeBigDecimal(dxGuest.get(0).get(0)));
                    dto.setGuestSpecificSize("<"+TmFunctions.changFloat(dxGuest.get(0).get(1)));
                }


                dto.setHomeCover(entity.getHomeTeamCover());
                dto.setGuestCover(entity.getGuestTeamCover());
                dtos.add(dto);
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("matchs",dtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
        }finally {
            em.close();
        }

        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    private Integer checkIfBetAndQ(Long matchTime, int day) {
        Long time=matchTime-new Date().getTime();
        if((time/(24*60*60*1000))>day){
            return YesOrNoStatusEnum.NO.status;
        }
        return YesOrNoStatusEnum.YES.status;
    }

    @Override
    public ServerResponse queryQuotationByMatchId(String matchId, Integer quotationType, Integer oddsType) {
        JSONObject resultJson = new JSONObject();
        //查询基本信息
//        String baseUrl=sysParaDao.findParaValueById(ConstantUtil.localURL);
        MatchEntity entity= matchDao.findFirstById(matchId);
        MatchDTO  dto=MatchConverter.INSTANCE.entity2dto(entity);
        dto.setHomeCover(entity.getHomeTeamCover());
        dto.setGuestCover(entity.getGuestTeamCover());
        resultJson.put("match",dto);

        if(oddsType==0){
            //查询开盘信息
            if(quotationType== QuotationTypeEnum.QUOTATION_QC.type){//全场
                List<QuotationDTO> leftList=cutOutList(getWinList(matchId,entity.getHomeTeamId()));
                List<QuotationDTO> centerList=cutOutList(getWinList(matchId,null));
                List<QuotationDTO> rightList=cutOutList(getWinList(matchId,entity.getGuestTeamId()));
                resultJson.put("qcList",changeList(leftList,centerList,rightList));
            }else if(quotationType== QuotationTypeEnum.QUOTATION_RF.type){// 让分
                List<List<Object>>  lst= quotationDao.findWinTeamQutationByRF(matchId);//获胜
                resultJson.put("rfList",getRFList(lst,entity.getHomeTeamId(),quotationType));
            }else if(quotationType== QuotationTypeEnum.QUOTATION_DX.type){//比大小
                List<List<Object>>  lst= quotationDao.findWinTeamQutationByDX(matchId);//获胜
                resultJson.put("dxList",getRFList(lst,"1",quotationType));
            }
        }else if(oddsType==1){//TODO 最佳赔率
            //查询开盘信息
            if(quotationType== QuotationTypeEnum.QUOTATION_QC.type){//全场
                List<QuotationDTO> leftList=cutOutList(getWinListZJ(matchId,entity.getHomeTeamId()));
                List<QuotationDTO> centerList=cutOutList(getWinListZJ(matchId,null));
                List<QuotationDTO> rightList=cutOutList(getWinListZJ(matchId,entity.getGuestTeamId()));
                resultJson.put("qcList",changeList(leftList,centerList,rightList));
            }else if(quotationType== QuotationTypeEnum.QUOTATION_RF.type){// 让分
                List<List<Object>>  lst= betDao.findRFHotOddsByMatchIdAll(matchId);//获胜
                resultJson.put("rfList",getRFList(lst,entity.getHomeTeamId(),quotationType));
            }else if(quotationType== QuotationTypeEnum.QUOTATION_DX.type){//比大小
                List<List<Object>>  lst= betDao.findDxHotOddsByMatchIdAll(matchId);//获胜
                resultJson.put("dxList",getRFList(lst,"1",quotationType));
            }
        }

        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    //截取list
    private List<QuotationDTO> cutOutList(List<QuotationDTO> list) {
        boolean bn=false;
        if(list==null||list.size()<=10)
            return changeBond(list);
        for(QuotationDTO  dto:list){
            //剩余保证金为0 删除
            if(dto.getSurplusBond().compareTo(new BigDecimal(0))==0){
                list.remove(dto);
                bn=true;
                break;
            }
        }
        if(bn){
            cutOutList(list);
        }
        return changeBond(list);
    }

    //TODO  有疑问 貌似注释的内容没用
    private List<QuotationDTO> changeBond(List<QuotationDTO> list) {
        for(QuotationDTO dto:list ){
            dto.setSurplusUnitAmount(changeToBet(dto.getSurplusBond(),dto.getOdds()));
            dto.setUnitAmount(changeToBet(dto.getBond(),dto.getOdds()));
        }
        return list;
    }

    private List<QcDTO> changeList(List<QuotationDTO> leftList, List<QuotationDTO> centerList, List<QuotationDTO> rightList) {
        List<QcDTO> lst=new ArrayList<>();
        int left=leftList==null?0:leftList.size();
        int center=centerList==null?0:centerList.size();
        int right=rightList==null?0:rightList.size();
        int  numL=getMax(left,center,right);
        for(int i=0;i<numL;i++){
            QcDTO dto=new  QcDTO();
            if(left>i) dto.setLeft(leftList.get(i));
            if(center>i) dto.setCenter(centerList.get(i));
            if(right>i) dto.setRight(rightList.get(i));
            lst.add(dto);
        }
        return lst;
    }

    private int getMax(int a, int b, int c){
        int max = a;//计算过程

        if(max < b)

            max = b;

        if(max < c)

            max = c;

        return max;
    }

    private List<QcDTO> changeList(List<QuotationDTO> leftList, List<QuotationDTO> rightList) {
        List<QcDTO> lst=new ArrayList<>();
        int left=leftList==null?0:leftList.size();
        int right=rightList==null?0:rightList.size();
        int  numL=getMax(left,right);
        for(int i=0;i<numL;i++){
            QcDTO dto=new  QcDTO();
            if(left>i) dto.setLeft(leftList.get(i));
            if(right>i) dto.setRight(rightList.get(i));
            lst.add(dto);
        }
        return lst;
    }

    private int getMax(int a, int b){
        int max = a;//计算过程

        if(max < b)

            max = b;

        return max;
    }


//    @Override
//    public ServerResponse queryBetPage(BetQuery qry) {
//        Integer quotationType=qry.getQuotationType();
////        MatchEntity entity= matchDao.findFirstById(qry.getMatchId());
//        QuotationEntity quotation = null;
//        if(quotationType== QuotationTypeEnum.QUOTATION_QC.type){//全场
//            if(StringUtils.isNotBlank(qry.getWinTeamId())){
//                quotation=quotationDao.findFirstQuotationForQC(qry.getMatchId(),qry.getOdds(),qry.getWinTeamId());
//            }else{
//                quotation=quotationDao.findFirstQuotationForQCPJ(qry.getMatchId(),qry.getOdds());
//            }
//
//        }else if(quotationType== QuotationTypeEnum.QUOTATION_RF.type){// 让分
//            quotation=quotationDao.findFirstQuotationForRF(qry.getMatchId(),qry.getOdds(),qry.getWinTeamId(),qry.getGivePoints());
//        }else if(quotationType== QuotationTypeEnum.QUOTATION_DX.type){//比大小
//            String specificSize=qry.getSpecificSize();
//            quotation=quotationDao.findFirstQuotationForDX(qry.getMatchId(),qry.getOdds(),qry.getWinTeamId(),TmFunctions.changFloat(specificSize.substring(1,specificSize.length())));
//        }
//        if(quotation==null)
//            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.SHORT_MARGIN);
//        QuotationBitDTO bit= QuotationConverter.INSTANCE.entity2dto(quotation);
//        bit.setUserAddress(userDao.findUserWalletAddressByUserId(bit.getUserId()));
//        bit.setFee(TmFunctions.changeBigDecimal(sysParaDao.findParaValueById(ConstantUtil.fee)));
//        bit.setMaxBetAmount(quotation.getSurplusBond().divide((quotation.getOdds()),0,BigDecimal.ROUND_DOWN));
//        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(bit);
//    }

    @Override
    public ServerResponse queryBetPage(BetQuery qry) {
        Integer quotationType=qry.getQuotationType();
//        MatchEntity entity= matchDao.findFirstById(qry.getMatchId());
        List<QuotationEntity> quotations = null;
        if(quotationType== QuotationTypeEnum.QUOTATION_QC.type){//全场
            if(StringUtils.isNotBlank(qry.getWinTeamId())){
                quotations=quotationDao.findQuotationForQC(qry.getMatchId(),qry.getOdds(),qry.getWinTeamId());
            }else{
                quotations=quotationDao.findQuotationForQCPJ(qry.getMatchId(),qry.getOdds());
            }

        }else if(quotationType== QuotationTypeEnum.QUOTATION_RF.type){// 让分
            quotations=quotationDao.findQuotationForRF(qry.getMatchId(),qry.getOdds(),qry.getWinTeamId(),qry.getGivePoints());
        }else if(quotationType== QuotationTypeEnum.QUOTATION_DX.type){//比大小
            String specificSize=qry.getSpecificSize();
            quotations=quotationDao.findQuotationForDX(qry.getMatchId(),qry.getOdds(),qry.getWinTeamId(),TmFunctions.changFloat(specificSize.substring(1,specificSize.length())));
        }
        if(quotations==null||quotations.size()<1)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.SHORT_MARGIN);
        QuotationBitDTO bit=new QuotationBitDTO();
        BigDecimal bond=new BigDecimal(0);
        BigDecimal surplusBond=new BigDecimal(0);
        List<String> address=new ArrayList<>();
        List<String> ids=new ArrayList<>();
        for(QuotationEntity qu:quotations){
            address.add(userDao.findUserWalletAddressByUserId(qu.getUserId()));
            ids.add(qu.getId());
            bond= bond.add(qu.getBond());
            surplusBond=surplusBond.add(qu.getSurplusBond());
        }
        bit.setId(String.join(",",ids));
        bit.setUserAddress(String.join(",",address));
        bit.setFee(TmFunctions.changeBigDecimal(sysParaDao.findParaValueById(ConstantUtil.fee)));
        bit.setBond(bond);
        bit.setSurplusBond(surplusBond);
        bit.setUnitAmount(new BigDecimal(1));
//        bit.setMaxBetAmount(bit.getSurplusBond().divide((qry.getOdds()),0,BigDecimal.ROUND_DOWN));
        bit.setMaxBetAmount(changeToBet(bit.getSurplusBond(),qry.getOdds()));
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(bit);
    }


    @Transactional
    @Override
    public ServerResponse betPay(String userId, BetPayCmd cmd) {
        List<QuotationEntity>  lst= quotationDao.findByIdIn(Arrays.asList(cmd.getQuotationId().split(",")));
        UserEntity user= userDao.findByUserId(userId);
        if(user==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
        if(cmd.getBetAmount().compareTo(new BigDecimal(0))<=0)
            return  ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ABNORMAL_AMOUNT);
        if(lst==null||lst.size()<0)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
        String s=sysParaDao.findParaValueById(ConstantUtil.timeOut);//截止时间
        QuotationEntity qu=lst.get(0);
        if(!checkTime(s,qu.getMatchId()))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.TIME_OUT);
        BigDecimal surplusBondTotal=new BigDecimal(0);
        for(QuotationEntity quotation:lst) {
            surplusBondTotal = surplusBondTotal.add(quotation.getSurplusBond());
        }
        BigDecimal pfje=changeToSurplusBond(cmd.getBetAmount(),qu.getOdds());//对应保证金金额
        if(surplusBondTotal.compareTo(pfje)==-1)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ABNORMAL_AMOUNT);

        BigDecimal betAmount=cmd.getBetAmount();//投注金额
        List<BetEntity> lt=new ArrayList<>();
        for(QuotationEntity quotation:lst){
            //剩余保证金 不大于0
            if(quotation.getSurplusBond().compareTo(new BigDecimal(0))!=1){
                continue;
            }

            BigDecimal canUserBond=changeToBet(quotation.getSurplusBond(),quotation.getOdds());//盘口可用对应投注金额

            if(canUserBond.compareTo(betAmount)==-1){// 盘口小于投注
                BetEntity bet= new BetEntity();
                bet.setBetAmount(canUserBond);
                bet.setUserId(userId);
                bet.setId(TmFunctions.getKeyStr("TZ"));
                bet.setLeagueMatch(quotation.getLeagueMatch());
                bet.setMatchId(quotation.getMatchId());
                bet.setQuotationType(quotation.getQuotationType());
                bet.setQuotationId(quotation.getId());
                bet.setStatus(OrderStatusEnum.ORDER_INCHAIN.status);
                bet.setCouNum(0);
                bet.setCreateTime(System.currentTimeMillis());
                betDao.save(bet);
                lt.add(bet);
                //修改盘口剩余金额
                quotation.setSurplusBond(BigDecimal.valueOf(0));
                quotation.setUpdateTime(System.currentTimeMillis());
                quotationDao.save(quotation);
                betAmount=betAmount.subtract(canUserBond);

            }else{
                BetEntity bet= new BetEntity();
                bet.setBetAmount(betAmount);
                bet.setUserId(userId);
                bet.setId(TmFunctions.getKeyStr("TZ"));
                bet.setLeagueMatch(quotation.getLeagueMatch());
                bet.setMatchId(quotation.getMatchId());
                bet.setQuotationType(quotation.getQuotationType());
                bet.setQuotationId(quotation.getId());
                bet.setStatus(OrderStatusEnum.ORDER_INCHAIN.status);
                bet.setCouNum(0);
                bet.setCreateTime(System.currentTimeMillis());
                betDao.save(bet);
                lt.add(bet);
                //修改盘口剩余金额
                quotation.setSurplusBond(quotation.getSurplusBond().subtract(changeToSurplusBond(betAmount,quotation.getOdds())));
                quotation.setUpdateTime(System.currentTimeMillis());
                quotationDao.save(quotation);
                break;
            }
        }

        if(StringUtils.isNotBlank(cmd.getTxId())){
            BetEntity betO=betDao.findFirstByTxId(cmd.getTxId());
            if(betO!=null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ADDRESS_EXIST);
            }
            for(BetEntity entity:lt){
                entity.setTransactionHash(cmd.getTxId());
            }
            betDao.saveAll(lt);
            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
        }else{
//            String ptWalletAddress=leagueMatchDao.findAddressById(qu.getLeagueMatch());//平台钱包地址
            MatchAddressEntity matchAddress= matchAddressDao.findFirstByMatchId(qu.getMatchId());
            String ptWalletAddress=matchAddress.getWalletAddress();//平台钱包地址
            String tronUrl=sysParaDao.findParaValueById(ConstantUtil.tronUrlVal);//钱包服务地址
            try{
                JSONObject json= TransactionUtils.outToAddressUsdt(DesUtil.decrypt(user.getWalkey()),ptWalletAddress, TmFunctions.changeString(cmd.getBetAmount()),tronUrl);
                if("SUCCESS".equals(json.getString("code"))){
                    TransMoneyDto dto=TransMoneyDto.builder()
                            .hash(json.getString("hash"))
                            .result(json.getString("result"))
                            .code(json.getString("code"))
                            .message(json.getString("message"))
                            .build();
                    for(BetEntity entity:lt){
                        entity.setTransactionHash(json.getString("transactionHash"));
                    }
                    betDao.saveAll(lt);
                    return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
                }else{
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PAY_FAILED);
                }
            }catch (Exception e){
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PAY_FAILED);
            }
        }


    }

    @Transactional
    @Override
    public ServerResponse cancelQuotations(String userId, String quotationId) {
        UserEntity user=userDao.findByUserId(userId);
        if(user==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.DATA_NOT_EXIST);
        QuotationEntity quotation=quotationDao.findFirstById(quotationId);
        if(quotation==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
        if(quotation.getStatus()==OrderStatusEnum.CANCEL.status)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.DATA_NON_EXECUTABLE);

        Long count =betDao.countByQuotationId(quotationId);
        if(count>0)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.CANNOT_CANCEL_BET);
        MatchEntity match= matchDao.findFirstById(quotation.getMatchId());
        if(match==null|| MatchStatusEnum.MATCH_WKS.type!=match.getStatus())
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.CANNOT_CANCEL_STATUS);

//        String walkey=leagueMatchDao.findWalKeyById(quotation.getLeagueMatch());//平台钱包地址
        MatchAddressEntity matchAddress= matchAddressDao.findFirstByMatchId(match.getId());
        String walkey=matchAddress.getWalkey();//平台钱包地址
        String tronUrl=sysParaDao.findParaValueById(ConstantUtil.tronUrlVal);//钱包服务地址
        Integer stock= (Integer) quMap.get("Lock"+quotationId);
        if(stock!=null&&stock==1)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.DATA_NON_EXECUTABLE);
        quMap.put("Lock"+quotationId,1);
        try {
            JSONObject json= TransactionUtils.outToAddressUsdt(DesUtil.decrypt(walkey),user.getWalletAddress(),TmFunctions.changeString(quotation.getBond()),tronUrl);
            if("SUCCESS".equals(json.getString("code"))){

                //保存操作记录
                PlatformTransactionsRecordEntity recordEntity= PlatformTransactionsRecordEntity.builder().amount(quotation.getBond())
                        .id(TmFunctions.getKeyStr("R"))
                        .couNum(0)
                        .leagueMatchId(String.valueOf(quotation.getLeagueMatch()))
                        .matchId(quotation.getMatchId())
                        .majorKey(quotation.getId())
                        .remark("开盘人取消开盘保证金退回")
                        .status(YesOrNoStatusEnum.NO.status)
                        .tableName("tbl_quotation")
                        .toAddress(user.getWalletAddress())
                        .transactionHash(json.getString("transactionHash"))
                        .walkey(walkey)
                        .amount(quotation.getBond())
                        .createTime(System.currentTimeMillis())
                        .build();
                platformTransactionsRecordDao.save(recordEntity);

                quotation.setStatus(OrderStatusEnum.CANCEL.status);
                quotation.setUpdateTime(System.currentTimeMillis());
                quotation.setSurplusBond(BigDecimal.valueOf(0));
                quotationDao.save(quotation);

                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
            }else{
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PAY_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PAY_FAILED);
        }finally {
            quMap.put("Lock"+quotationId,0);
        }

    }

    @Override
    public ServerResponse queryMathFruit(MatchAllQuery qry) {
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" select t.id,t.create_time,t.end_time,t.guest_score,t.guest_team_id,t.home_score,t.home_team_id," +
                    " t.league_match,t.match_time,t.status,t.update_time,t.guest_team,t.home_team,t.if_hot,t.if_close" +
                    ",t.guest_team_en,t.guest_team_zht,t.home_team_en,t.home_team_zht,t.guest_team_cover,t.home_team_cover " +
                    " from match_bet as t where    t.status ="+MatchStatusEnum.MATCH_YWS.type); //除去未开赛的 TODO
            sqltotal.append(" select count(1) from match_bet as t where     t.status ="+MatchStatusEnum.MATCH_YWS.type);
            if(StringUtils.isNoneBlank(qry.getKeyword())){
                sqljoint.append(" and concat(t.home_team,',',t.guest_team) like '%"+qry.getKeyword()+"%'  ");
                sqltotal.append(" and concat(t.home_team,',',t.guest_team)  like '%"+qry.getKeyword()+"%'  ");
            }
            if(qry.getLeagueMatch()!=null){
                sqljoint.append(" and  t.league_match=  '"+qry.getLeagueMatch()+"'");
                sqltotal.append(" and  t.league_match=  '"+qry.getLeagueMatch()+"'");
            }
//            Date start= DateUtils.getDayBegin();
//            if(start!=null){
//                    sqljoint.append(" and  t.match_time>=  "+start.getTime()+"");
//                    sqltotal.append(" and  t.match_time>=  "+start.getTime()+"");
//            }
            if(qry.getStartTime()!=null){
                sqljoint.append(" and  t.match_time>=  "+qry.getStartTime()+"");
                sqltotal.append(" and  t.match_time>=  "+qry.getStartTime()+"");
            }
            if(qry.getEndTime()!=null){
                sqljoint.append(" and  t.match_time<  "+qry.getEndTime()+"");
                sqltotal.append(" and  t.match_time<  "+qry.getEndTime()+"");
            }
            if(qry.getOrderType()==0){
                sqljoint.append("  ORDER BY t.amount desc,t.count desc");
            }else{
                sqljoint.append("  ORDER BY t.match_time desc");
            }

            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, MatchEntity.class);
            List<MatchEntity> list=query.getResultList();
            List<MatchDTO> dtos    = new ArrayList<>();
//            String baseUrl=sysParaDao.findParaValueById(ConstantUtil.localURL);
            for(MatchEntity entity : list){
                MatchDTO dto = MatchConverter.INSTANCE.entity2dto(entity);
                dto.setHomeCover(entity.getHomeTeamCover());
                dto.setGuestCover(entity.getGuestTeamCover());
                dtos.add(dto);
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("matchs",dtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
        }finally {
            em.close();
        }

        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse queryMathCount(Long startTime) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("todayCount",matchDao.findMatchCountByTime(startTime,startTime+24*60*60*1000L));
        resultJson.put("zpCount",matchDao.findMatchCountByTime(startTime,startTime+365*24*60*60*1000L));
        resultJson.put("winCount",0);//TODO
        resultJson.put("hotCount",0);
        resultJson.put("resultCount",matchDao.findMatchReasultCountByTime(startTime,startTime+24*60*60*1000L));

//        for(LeagueMatchEnum key: LeagueMatchEnum.values()){
//            resultJson.put("leagueMatch"+key.type,matchDao.findMatchCountByTimeAndLS(DateUtils.getDayBegin().getTime(),DateUtils.getEndDayOfSomeDay(7).getTime(),key.type));
//        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse queryWinMath(MatchAllQuery qry) {
        List<WinMatchDTO> dtos    = new ArrayList<>();
        JSONObject resultJson = new JSONObject();
        resultJson.put("winMatchs",dtos);
        resultJson.put("pageSize",qry.getPageSize());
        resultJson.put("pageNum",qry.getPageNum());
        resultJson.put("total",0);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Transactional
    @Override
    public ServerResponse betPayBefore(String userId, BetPayCmd cmd) {
        List<QuotationEntity>  lst= quotationDao.findByIdIn(Arrays.asList(cmd.getQuotationId().split(",")));
        UserEntity user= userDao.findByUserId(userId);
        if(user==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
        if(cmd.getBetAmount().compareTo(new BigDecimal(0))<=0)
            return  ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ABNORMAL_AMOUNT);
//        QuotationEntity quotation= quotationDao.findFirstById(cmd.getQuotationId());
        if(lst==null||lst.size()<0)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
        String s=sysParaDao.findParaValueById(ConstantUtil.timeOut);//截止时间
        QuotationEntity qu=lst.get(0);
        if(!checkTime(s,qu.getMatchId()))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.TIME_OUT);
        BigDecimal surplusBondTotal=new BigDecimal(0);
        for(QuotationEntity quotation:lst) {
            surplusBondTotal = surplusBondTotal.add(quotation.getSurplusBond());
        }
        BigDecimal pfje=changeToSurplusBond(cmd.getBetAmount(),qu.getOdds());//对应保证金金额
        if(surplusBondTotal.compareTo(pfje)==-1)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ABNORMAL_AMOUNT);

        List<String> orders=new ArrayList<>();

        BigDecimal betAmount=cmd.getBetAmount();//投注金额
        for(QuotationEntity quotation:lst){
            //剩余保证金 不大于0
            if(quotation.getSurplusBond().compareTo(new BigDecimal(0))!=1){
                continue;
            }
            BigDecimal canUserBond=changeToBet(quotation.getSurplusBond(),quotation.getOdds());//盘口可用对应投注金额
            if(canUserBond.compareTo(betAmount)==-1) {// 盘口小于投注
                OrderEntity order= new OrderEntity();
                order.setBetAmount(canUserBond);
                order.setUserId(userId);
                order.setId(TmFunctions.getKeyStr("O"));
                order.setLeagueMatch(quotation.getLeagueMatch());
                order.setMatchId(quotation.getMatchId());
                order.setQuotationType(quotation.getQuotationType());
                order.setQuotationId(quotation.getId());
                order.setStatus(OrderStatusEnum.WAITPAY.status);
                order.setCreateTime(System.currentTimeMillis());
                orderDao.save(order);
                //修改盘口剩余金额
                quotation.setSurplusBond(BigDecimal.valueOf(0));
                quotation.setUpdateTime(System.currentTimeMillis());
                quotationDao.save(quotation);
                betAmount=betAmount.subtract(canUserBond);
                orders.add(order.getId());
            }else{
                OrderEntity order= new OrderEntity();
                order.setBetAmount(betAmount);
                order.setUserId(userId);
                order.setId(TmFunctions.getKeyStr("O"));
                order.setLeagueMatch(quotation.getLeagueMatch());
                order.setMatchId(quotation.getMatchId());
                order.setQuotationType(quotation.getQuotationType());
                order.setQuotationId(quotation.getId());
                order.setStatus(OrderStatusEnum.WAITPAY.status);
                order.setCreateTime(System.currentTimeMillis());
                orderDao.save(order);
                //修改盘口剩余金额
                quotation.setSurplusBond(quotation.getSurplusBond().subtract(changeToSurplusBond(betAmount,quotation.getOdds())));
                quotation.setUpdateTime(System.currentTimeMillis());
                quotationDao.save(quotation);
                orders.add(order.getId());
            }
        }


        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(String.join(",",orders));
    }

    @Transactional
    @Override
    public ServerResponse betPayFinish(String userId, BetPayWebCmd cmd) {
        BetEntity betO=betDao.findFirstByTxId(cmd.getTxId());
        if(betO!=null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ADDRESS_EXIST);
        UserEntity user= userDao.findByUserId(userId);
        if(user==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
        List<OrderEntity> lst=orderDao.findByIdIn(Arrays.asList(cmd.getOrderId().split(",")));
//        OrderEntity order=orderDao.findFirstById(cmd.getOrderId());
        if(lst==null||lst.size()<0)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
        BigDecimal totalBet=new BigDecimal(0);
        for(OrderEntity order:lst){
            totalBet =totalBet.add(order.getBetAmount());
            QuotationEntity quotation= quotationDao.findFirstById(order.getQuotationId());
            if(quotation==null)
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
            String s=sysParaDao.findParaValueById(ConstantUtil.timeOut);//截止时间
            if(!checkTime(s,quotation.getMatchId()))
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.TIME_OUT);
        }
        if(cmd.getBetAmount().compareTo(totalBet)!=0)
            return  ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ABNORMAL_AMOUNT);
        BetEntity bb= betDao.findFirstByTxId(cmd.getTxId());
        if(bb!=null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ALREADY_EXISTS_DATA);
        List<BetEntity> lt=new ArrayList<>();
        for(OrderEntity order:lst){
            BetEntity bet= new BetEntity();
            bet.setBetAmount(cmd.getBetAmount());
            bet.setUserId(userId);
            bet.setId(TmFunctions.getKeyStr("TZ"));
            bet.setLeagueMatch(order.getLeagueMatch());
            bet.setMatchId(order.getMatchId());
            bet.setQuotationType(order.getQuotationType());
            bet.setQuotationId(order.getQuotationId());
            bet.setStatus(OrderStatusEnum.ONGOING.status);
            bet.setCouNum(0);
            bet.setCreateTime(System.currentTimeMillis());
            bet.setTxId(cmd.getTxId());
            bet.setTransactionHash(cmd.getTxId());
            betDao.save(bet);
            lt.add(bet);
        }
//        betDao.saveAll(lt);
//        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);

        String tronUrl=sysParaDao.findParaValueById(ConstantUtil.tronUrlVal);//钱包服务地址
        MatchAddressEntity matchAddress= matchAddressDao.findFirstByMatchId(lst.get(0).getMatchId());
        String ptWalletAddress=matchAddress.getWalletAddress();//平台钱包地址
        try{
            JSONObject json= TransactionUtils.getTransactionById(cmd.getTxId(),tronUrl);
            if("SUCCESS".equals(json.getString("code"))){
                BigDecimal amount=json.getBigDecimal("amount");
                if(amount.compareTo(totalBet)!=0||!json.getString("to_address").equals(ptWalletAddress)
                ||!json.getString("owner_address").equals(user.getWalletAddress())){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return  ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ABNORMAL_AMOUNT);
                }
                for(OrderEntity order:lst){
                    order.setStatus(OrderStatusEnum.FINISHED.status);
                    order.setUpdateTime(System.currentTimeMillis());
                    orderDao.save(order);
                }
                for(BetEntity entity :lt){
                    entity.setTransactionHash(json.getString("transactionHash"));
                    entity.setBlockHash(json.getString("blockHash"));
                }
                betDao.saveAll(lt);
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
            }else{
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
    }

    @Transactional
    @Override
    public ServerResponse betPayCancel(String userId, String orderId) {
        List<OrderEntity> lst=orderDao.findByIdIn(Arrays.asList(orderId.split(",")));
//        OrderEntity order=orderDao.findByUserIdAndOrderId(userId,orderId);
        if(lst==null||lst.size()<0)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.CANNT_FINDORDER);
        for(OrderEntity order:lst){
            if(order.getStatus()!=OrderStatusEnum.WAITPAY.status)
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.CANNT_CANCELORDER);
        }
        for(OrderEntity order:lst){
            order.setStatus(OrderStatusEnum.CANCEL.status);
            order.setUpdateTime(System.currentTimeMillis());
            orderDao.save(order);
            QuotationEntity quotation= quotationDao.findFirstById(order.getQuotationId());
            //修改盘口剩余金额
            quotation.setSurplusBond(quotation.getSurplusBond().add(order.getBetAmount()));
            quotation.setUpdateTime(System.currentTimeMillis());
            quotationDao.save(quotation);
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
    }

    @Override
    public ServerResponse createQuotationsWeb(String userId, List<QuotationCmd> cmds, String txId) {
        BigDecimal amount=new BigDecimal(0);
//        JSONObject json=new JSONObject();
//        String tronUrl=sysParaDao.findParaValueById(ConstantUtil.tronUrlVal);//钱包服务地址
//        try{
//            json= TransactionUtils.getTransactionById(txId,tronUrl);
//            if("SUCCESS".equals(json.getString("code"))){
//                amount=json.getBigDecimal("amount");
//            }else{
//                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
//        }

        UserEntity user= userDao.findByUserId(userId);
        if(user==null||StringUtils.isBlank(user.getWalletAddress()))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.WALLET_NOT_SUCH);
        Long t = System.currentTimeMillis();
        List<QuotationEntity> lst=new ArrayList<>();
        BigDecimal total=new BigDecimal(0);
        String s=sysParaDao.findParaValueById(ConstantUtil.timeOut);//截止时间
        for(QuotationCmd cmd:cmds){
            String matchId=cmd.getMatchId();
            if(!checkTime(s,matchId)){
//                OrderEntity order= new OrderEntity();
//                order.setBetAmount(amount);
//                order.setUserId(userId);
//                order.setId(TmFunctions.getKeyStr("O"));
//                order.setLeagueMatch(cmd.getLeagueMatch());
//                order.setMatchId(cmd.getMatchId());
//                order.setQuotationType(cmd.getQuotationType());
////                order.setQuotationId(quotation.getId());
//                order.setStatus(OrderStatusEnum.WAITPAY.status);
//                order.setCreateTime(System.currentTimeMillis());
//                orderDao.save(order);
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.TIME_OUT);
            }
            QuotationEntity qu= quotationDao.findFirstByTxId(txId);
            if(qu!=null)
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ALREADY_EXISTS_DATA);
            QuotationEntity entity=QuotationConverter.INSTANCE.cmd2entity(cmd);
            BigDecimal bond=  new BigDecimal(entity.getUnitAmount()).multiply(cmd.getOdds());
            entity.setBond(bond);
            entity.setSurplusBond(bond);
            entity.setStatus(OrderStatusEnum.ORDER_INCHAIN.status);
            entity.setCouNum(0);
            total=total.add(bond);
            entity.setUserId(userId);
            entity.setTxId(txId);
            entity.setTransactionHash(txId);
            if(StringUtils.isBlank(cmd.getId())){
                entity.setId(TmFunctions.getKeyStr("PK"));
                entity.setCreateTime(t);
            }else{
                entity.setUpdateTime(t);
            }
            lst.add(entity);
        }
//        //总的保证金 小于等于0 时
//        if(total.compareTo(new BigDecimal(0))<=0||amount.compareTo(total)!=0||!json.getString("to_address").equals(leagueMatchDao.findAddressById( cmds.get(0).getLeagueMatch()))//都是一个联赛下的
//                ||!json.getString("owner_address").equals(user.getWalletAddress()))
//            return  ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ABNORMAL_AMOUNT);
        quotationDao.saveAll(lst);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
    }

    @Override
    public ServerResponse test(String txId) {
        BigDecimal amount=new BigDecimal(0);
        JSONObject json=new JSONObject();
        String tronUrl=sysParaDao.findParaValueById(ConstantUtil.tronUrlVal);//钱包服务地址
        try{
            json= TransactionUtils.getTransactionById(txId,tronUrl);
            if("SUCCESS".equals(json.getString("code"))){
                amount=json.getBigDecimal("amount");
            }else{
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(json);
    }

    @Override
    public ServerResponse testIds(String ids,int count) {
        List<QuotationDTO> lt=new ArrayList<>();
        for(int i=-3;i<count;i++){
            QuotationDTO dto=new QuotationDTO();
            dto.setWinTeamId("id"+i);
            dto.setSurplusBond(new BigDecimal(i));
            lt.add(dto);
        }
        cutOutList(lt);

        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(lt);
    }

    @Override
    public ServerResponse queryBetMessage(String userId, BigDecimal betAmount, String matchId) {
        UserEntity user=userDao.findByUserId(userId);
        if(user==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
        MatchAddressEntity matchAddress= matchAddressDao.findFirstByMatchId(matchId);
        String address=matchAddress.getWalletAddress();//平台钱包地址
//        MatchEntity match=matchDao.findFirstById(matchId);
//        String address=leagueMatchDao.findAddressById(match.getLeagueMatch());
        if(StringUtils.isBlank(address))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ADDRESS_NOT_EXIST);
        USDTMessageDTO  dto=new USDTMessageDTO();
        dto.setBetAmount(betAmount);
        dto.setUsdtContract(ConstantUtil.usdtContract);
        dto.setBetContract(ConstantUtil.betContract);
        dto.setUsdtAbi(ConstantUtil.usdtAbi);
        dto.setBetAbi(ConstantUtil.betAbi);
        dto.setUserName(user.getUserName());
        dto.setUserAddress(user.getWalletAddress());
        dto.setPtAddress(address);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
    }


    /**
     * /将投注金额 转换成 赔付金额
     * @param betAmount 投注金额
     * @param odds  剩余保证金
     * @return
     */
    private BigDecimal changeToSurplusBond(BigDecimal betAmount, BigDecimal odds) {
        return betAmount.multiply(odds);
    }

    /**
     * /将保证金转换成 投注金额--投注是1USDT 为一注，所以不会有小数
     * @param bondAmount 赔付金额
     * @param odds  剩余保证金
     * @return
     */
    private BigDecimal changeToBet(BigDecimal bondAmount, BigDecimal odds) {
//        return bondAmount.divide(odds,0,BigDecimal.ROUND_DOWN);
        if(bondAmount.compareTo(new BigDecimal(0))==0)
            return new BigDecimal(0);
        return bondAmount.divide(odds,0,BigDecimal.ROUND_DOWN);
    }

    /**
     * 将数据库查询结果 分组展示
     * @param lst  库数据
     * @param homeTeamId 主队id 或者是比大小 的1  代表左侧
     * @param type   让分 和 比大小玩法
     * @return
     */
    private List<QuotationRFDTO> getRFList(List<List<Object>> lst, String homeTeamId, int type) {
        List<QuotationRFDTO> list=new ArrayList<>();
        if(lst!=null&&lst.size()>0){
            Float givePoints=null;//分组中 givePoints 不为null
            BigDecimal bondLeft=new BigDecimal(0);
            BigDecimal surplusBondLeft=new BigDecimal(0);
            BigDecimal bondRight=new BigDecimal(0);
            BigDecimal surplusBondRight=new BigDecimal(0);
            BigDecimal oddsTotal=new BigDecimal(0);
            QuotationRFDTO dto=new QuotationRFDTO();
            List<QuotationDTO> quotationsLeft=new ArrayList<>();
            List<QuotationDTO> quotationsRight=new ArrayList<>();
            for(List<Object> lt:lst){
                Float point=TmFunctions.changFloat(lt.get(0));// 让分分数
                BigDecimal odds=TmFunctions.changeBigDecimal(lt.get(1));//赔率
                BigDecimal bond=TmFunctions.changeBigDecimal(lt.get(2));//保证金
                BigDecimal surplusBond=TmFunctions.changeBigDecimal(lt.get(3));//剩余保证金
                String winTeam=TmFunctions.changeString(lt.get(4));//获胜队伍
                if(point==null)
                    continue;
                //不等时 为一个新的开始
                if(givePoints==null||givePoints.compareTo(point)!=0){
                    //写入分组的值
                    if(givePoints!=null){
//                        dto.setBondLeft(bondLeft);
//                        dto.setSurplusBondLeft(surplusBondLeft);
//                        dto.setUnitAmountLeft(changeToBet(bondLeft,oddsTotal));// 总投注数量
//                        dto.setSurplusUnitAmountLeft(changeToBet(surplusBondLeft,oddsTotal));//可投注数量

                        //取第一个值
                        dto.setQuotationsLeft(cutOutList(quotationsLeft));
                        if(dto.getQuotationsLeft()!=null&&dto.getQuotationsLeft().size()>0){
                            QuotationDTO left=dto.getQuotationsLeft().get(0);
                            dto.setBondLeft(left.getBond());
                            dto.setSurplusBondLeft(left.getSurplusBond());
                            dto.setUnitAmountLeft(left.getUnitAmount());
                            dto.setSurplusUnitAmountLeft(left.getSurplusUnitAmount());
                        }
//                        dto.setBondRight(bondRight);
//                        dto.setSurplusBondRight(surplusBondRight);
//                        dto.setUnitAmountRight(changeToBet(bondRight,oddsTotal));//总投注数量
//                        dto.setSurplusUnitAmountRight(changeToBet(surplusBondRight,oddsTotal));//可投注数量
                        dto.setQuotationsRight(cutOutList(quotationsRight));
                        if(dto.getQuotationsRight()!=null&&dto.getQuotationsRight().size()>0){
                            QuotationDTO right=dto.getQuotationsRight().get(0);
                            dto.setBondRight(right.getBond());
                            dto.setSurplusBondRight(right.getSurplusBond());
                            dto.setUnitAmountRight(right.getUnitAmount());
                            dto.setSurplusUnitAmountRight(right.getSurplusUnitAmount());
                        }
                        dto.setQcDTO(changeList(cutOutList(quotationsLeft),cutOutList(quotationsRight)));
                        list.add(dto);//保存上一个

                        //重置 原来的参数
                        bondLeft=new BigDecimal(0);
                        surplusBondLeft=new BigDecimal(0);
                        bondRight=new BigDecimal(0);
                        surplusBondRight=new BigDecimal(0);
                        dto=new QuotationRFDTO();
                        quotationsLeft=new ArrayList<>();
                        quotationsRight=new ArrayList<>();
                    }
                    //首先写入分组固定的值 ，分数 和最高的赔率
                    if(homeTeamId.equals(winTeam)){
                        dto.setOddsLeft(odds);
                    }else{

                        dto.setOddsRight(odds);
                    }
                    oddsTotal=odds;
                    givePoints=point;
                }
                //写入盘口
                QuotationDTO qdto=new QuotationDTO();
                qdto.setWinTeamId(winTeam);
                CompetitionTeamEntity team=competitionTeamDao.findFirstById(winTeam);
                if(team!=null){
                    qdto.setWinTeam(team.getName());
                    qdto.setWinTeam_en(team.getName_en());
                    qdto.setWinTeam_zht(team.getName_zht());
                }
                qdto.setSurplusBond(surplusBond);
                qdto.setOdds(odds);
                qdto.setBond(bond);

                if(homeTeamId.equals(winTeam)){//主队获胜盘口
                    if(type==QuotationTypeEnum.QUOTATION_RF.type){
                        dto.setGivePointsLeft(String.valueOf(-point));
                    }else{
                        dto.setGivePointsLeft(">"+String.valueOf(point));
                    }
                    quotationsLeft.add(qdto);
                    bondLeft=bondLeft.add(bond);
                    surplusBondLeft=surplusBondLeft.add(surplusBond);
                }else{
                    if(type==QuotationTypeEnum.QUOTATION_RF.type){
                        dto.setGivePointsRight(String.valueOf(point));
                    }else{
                        dto.setGivePointsRight("<"+String.valueOf(point));
                    }
                    quotationsRight.add(qdto);
                    bondRight=bondRight.add(bond);
                    surplusBondRight=surplusBondRight.add(surplusBond);
                }

            }
            //写入最后一个分组的值
            if(givePoints!=null){
//                dto.setBondLeft(bondLeft);
//                dto.setSurplusBondLeft(surplusBondLeft);
//                dto.setUnitAmountLeft(changeToBet(bondLeft,oddsTotal));// 总投注数量
//                dto.setSurplusUnitAmountLeft(changeToBet(surplusBondLeft,oddsTotal));//可投注数量
                dto.setQuotationsLeft(cutOutList(quotationsLeft));
                if(dto.getQuotationsLeft()!=null&&dto.getQuotationsLeft().size()>0){
                    QuotationDTO left=dto.getQuotationsLeft().get(0);
                    dto.setBondLeft(left.getBond());
                    dto.setSurplusBondLeft(left.getSurplusBond());
                    dto.setUnitAmountLeft(left.getUnitAmount());
                    dto.setSurplusUnitAmountLeft(left.getSurplusUnitAmount());
                }
//                dto.setBondRight(bondRight);
//                dto.setSurplusBondRight(surplusBondRight);
//                dto.setUnitAmountRight(changeToBet(bondRight,oddsTotal));//总投注数量
//                dto.setSurplusUnitAmountRight(changeToBet(surplusBondRight,oddsTotal));//可投注数量
                dto.setQuotationsRight(cutOutList(quotationsRight));
                if(dto.getQuotationsRight()!=null&&dto.getQuotationsRight().size()>0){
                    QuotationDTO right=dto.getQuotationsRight().get(0);
                    dto.setBondRight(right.getBond());
                    dto.setSurplusBondRight(right.getSurplusBond());
                    dto.setUnitAmountRight(right.getUnitAmount());//总投注数量
                    dto.setSurplusUnitAmountRight(right.getSurplusUnitAmount());//可投注数量
                }
                dto.setQcDTO(changeList(cutOutList(quotationsLeft),cutOutList(quotationsRight)));
                list.add(dto);
            }
        }
        return list;
    }


    private List<QuotationDTO> getWinList(String matchId, String teamId) {
        List<QuotationDTO> list=new ArrayList<>();
        List<List<Object>>  lst=new ArrayList<>();
        if(StringUtils.isNotBlank(teamId)){
            lst= quotationDao.findWinTeamQutationByQC(matchId,teamId);//获胜
        }else{
            lst= quotationDao.findTieTeamQutationByQC(matchId);//平局
        }
        if(lst!=null&&lst.size()>0){
            for(List<Object> lt:lst){
                QuotationDTO dto= new QuotationDTO();
                dto.setWinTeamId(teamId);
                CompetitionTeamEntity team=competitionTeamDao.findFirstById(teamId);
                if(team!=null){
                    dto.setWinTeam(team.getName());
                    dto.setWinTeam_en(team.getName_en());
                    dto.setWinTeam_zht(team.getName_zht());
                }
                dto.setOdds(TmFunctions.changeBigDecimal(lt.get(0)));
                dto.setBond(TmFunctions.changeBigDecimal(lt.get(1)));
                dto.setSurplusBond(TmFunctions.changeBigDecimal(lt.get(2)));
                list.add(dto);
            }
        }
        return list;
    }

    private List<QuotationDTO> getWinListZJ(String matchId, String teamId) {
        List<QuotationDTO> list=new ArrayList<>();
        List<List<Object>>  lst=new ArrayList<>();
        if(StringUtils.isNotBlank(teamId)){
            lst= betDao.findFullCourtHotOddsByMatchIdAndWinTeamIdAll(matchId,teamId);//获胜
        }else{
            lst= betDao.findFullCourtHotOddsByMatchIdAndTieAll(matchId);//平局
        }
        if(lst!=null&&lst.size()>0){
            for(List<Object> lt:lst){
                QuotationDTO dto= new QuotationDTO();
                dto.setWinTeamId(teamId);
                CompetitionTeamEntity team=competitionTeamDao.findFirstById(teamId);
                if(team!=null){
                    dto.setWinTeam(team.getName());
                    dto.setWinTeam_en(team.getName_en());
                    dto.setWinTeam_zht(team.getName_zht());
                }
                dto.setOdds(TmFunctions.changeBigDecimal(lt.get(0)));
                dto.setBond(TmFunctions.changeBigDecimal(lt.get(1)));
                dto.setSurplusBond(TmFunctions.changeBigDecimal(lt.get(2)));
                list.add(dto);
            }
        }
        return list;
    }

//    private String findTeamById(String teamId) {
//        CompetitionTeamEntity entity=competitionTeamDao.findFirstById(teamId);
//        if(entity!=null)
//            return entity.getName();
//        return null;
//    }

//    private String getCoverById(String homeTeamId, String baseUrl) {
//        CompetitionTeamEntity entity= competitionTeamDao.findFirstById(homeTeamId);
//        if(entity!=null&&entity.getCover()!=null){
//            return baseUrl+entity.getCover();
//        }
//        return null;
//    }

    //当前时间减去开始时间 小于 设置时间 返回 true
    private boolean checkTime(String s, String matchId) {

        MatchEntity entity=matchDao.findFirstById(matchId);
        if(entity==null)
            return false;
        if((new Date().getTime()-entity.getMatchTime())<Long.valueOf(s)*1000){
            return true;
        }
        return false;

    }

}
