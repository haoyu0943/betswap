package com.betswap.market.app.flow.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.betswap.market.app.flow.service.FlowService;
import com.betswap.market.client.common.vo.qry.CommonPageQry;
import com.betswap.market.client.flow.converter.FlowConverter;
import com.betswap.market.client.flow.dto.FlowCommonDTO;
import com.betswap.market.client.flow.dto.TransferRecordDTO;
import com.betswap.market.client.flow.vo.query.TransferRecordAllQuery;
import com.betswap.market.client.quotation.OrderStatusEnum;
import com.betswap.market.client.quotation.QuotationTypeEnum;
import com.betswap.market.client.quotation.TransactionTypeEnum;
import com.betswap.market.client.quotation.converter.BetRecordConverter;
import com.betswap.market.client.quotation.dto.BetRecordDTO;
import com.betswap.market.client.quotation.qry.BetRecordQuery;
import com.betswap.market.client.quotation.qry.ProfitQuery;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.flow.entity.BETRechargeRecordEntity;
import com.betswap.market.infrastruture.flow.entity.USDTRechargeRecordEntity;
import com.betswap.market.infrastruture.flow.entity.USDTWithdrawalRecordEntity;
import com.betswap.market.infrastruture.quotation.dao.BetDao;
import com.betswap.market.infrastruture.quotation.dao.CompetitionTeamDao;
import com.betswap.market.infrastruture.quotation.dao.MatchDao;
import com.betswap.market.infrastruture.quotation.dao.QuotationDao;
import com.betswap.market.infrastruture.quotation.entity.BetEntity;
import com.betswap.market.infrastruture.quotation.entity.CompetitionTeamEntity;
import com.betswap.market.infrastruture.quotation.entity.MatchEntity;
import com.betswap.market.infrastruture.quotation.entity.QuotationEntity;
import com.betswap.market.infrastruture.system.dao.SysParaDao;
import com.betswap.market.infrastruture.user.dao.UserDao;
import com.betswap.market.infrastruture.user.entity.UserEntity;
import com.betswap.market.infrastruture.utils.constant.ConstantUtil;
import com.betswap.market.infrastruture.utils.constant.TmDateUtil;
import com.betswap.market.infrastruture.utils.constant.TmFunctions;
import com.betswap.market.infrastruture.utils.http.HttpHelpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

/**
 * 手机记录
 */
@Service
@Slf4j
public class FlowServiceImpl implements FlowService {

    //trc20 查询路径
    private static final String token_trc20 = "https://apilist.tronscan.org/api/token_trc20/transfers";
    //trc10 查询路径
    private static final String token_trc10 = "https://apilist.tronscan.org/api/transfer";

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private SysParaDao sysParaDao;
    @Autowired
    private QuotationDao quotationDao;

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private CompetitionTeamDao competitionTeamDao;

    @Autowired
    private BetDao betDao;

    @Autowired
    private UserDao userDao;


    @Override
    public ServerResponse queryUSDTRechargeRecord(String userId, CommonPageQry qry) {
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" SELECT * FROM usdt_recharge_record  where 1=1 ");
            sqltotal.append(" SELECT  count(1 ) FROM  usdt_recharge_record where 1=1 ");
            if(StringUtils.isNotBlank(userId)){
                sqljoint.append(" and  t.user_id=  '"+userId+"'");
                sqltotal.append(" and  t.user_id=  '"+userId+"'");
            }
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, USDTRechargeRecordEntity.class);
            List<USDTRechargeRecordEntity> list=query.getResultList();
            List<FlowCommonDTO> dtos    = new ArrayList<>();
            for(USDTRechargeRecordEntity entity : list){
                FlowCommonDTO dto = FlowConverter.INSTANCE.entity2dto(entity);
                dtos.add(dto);
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("dtos",dtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
        }finally {
            em.close();
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse queryBETRechargeRecord(String userId, CommonPageQry qry) {
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" SELECT * FROM bet_recharge_record  where 1=1 ");
            sqltotal.append(" SELECT  count(1 ) FROM  bet_recharge_record where 1=1 ");
            if(StringUtils.isNotBlank(userId)){
                sqljoint.append(" and  t.user_id=  '"+userId+"'");
                sqltotal.append(" and  t.user_id=  '"+userId+"'");
            }
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, BETRechargeRecordEntity.class);
            List<BETRechargeRecordEntity> list=query.getResultList();
            List<FlowCommonDTO> dtos    = new ArrayList<>();
            for(BETRechargeRecordEntity entity : list){
                FlowCommonDTO dto = FlowConverter.INSTANCE.entity2dto(entity);
                dtos.add(dto);
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("dtos",dtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
        }finally {
            em.close();
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse queryUSDTWithdrawalRecord(String userId, CommonPageQry qry) {
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" SELECT * FROM usdt_withdrawal_record  where 1=1 ");
            sqltotal.append(" SELECT  count(1 ) FROM  usdt_withdrawal_record where 1=1 ");
            if(StringUtils.isNotBlank(userId)){
                sqljoint.append(" and  t.user_id=  '"+userId+"'");
                sqltotal.append(" and  t.user_id=  '"+userId+"'");
            }
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, USDTWithdrawalRecordEntity.class);
            List<USDTWithdrawalRecordEntity> list=query.getResultList();
            List<FlowCommonDTO> dtos    = new ArrayList<>();
            for(USDTWithdrawalRecordEntity entity : list){
                FlowCommonDTO dto = FlowConverter.INSTANCE.entity2dto(entity);
                dtos.add(dto);
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("dtos",dtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
        }finally {
            em.close();
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse queryBETWithdrawalRecord(String userId, CommonPageQry qry) {
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" SELECT * FROM bet_withdrawal_record  where 1=1 ");
            sqltotal.append(" SELECT  count(1 ) FROM  bet_withdrawal_record where 1=1 ");
            if(StringUtils.isNotBlank(userId)){
                sqljoint.append(" and  t.user_id=  '"+userId+"'");
                sqltotal.append(" and  t.user_id=  '"+userId+"'");
            }
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, BETRechargeRecordEntity.class);
            List<BETRechargeRecordEntity> list=query.getResultList();
            List<FlowCommonDTO> dtos    = new ArrayList<>();
            for(BETRechargeRecordEntity entity : list){
                FlowCommonDTO dto = FlowConverter.INSTANCE.entity2dto(entity);
                dtos.add(dto);
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("dtos",dtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
        }finally {
            em.close();
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse queryBetRecordByCondition(String userId, BetRecordQuery qry) {
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" SELECT t.* FROM tbl_bet_record as t LEFT JOIN tbl_match as m on t.match_id=m.id  where 1=1 ");
            sqltotal.append(" select count(1) from  tbl_bet_record as t  LEFT JOIN tbl_match as m on t.match_id=m.id  where 1=1 ");
            if(StringUtils.isNotBlank(userId)){
                sqljoint.append(" and  t.user_id=  '"+userId+"'");
                sqltotal.append(" and  t.user_id=  '"+userId+"'");
            }
            if(qry.getQuotationType()!=null){
                sqljoint.append(" and  t.quotation_type=  '"+qry.getQuotationType()+"'");
                sqltotal.append(" and  t.quotation_type=  '"+qry.getQuotationType()+"'");
            }
            if(StringUtils.isNotBlank(qry.getMatchStatus())){
                List<String> str=Arrays.asList(qry.getMatchStatus().split(","));
                if(str.size()>1){
                    sqljoint.append(" and  m.status  in (  '"+qry.getMatchStatus()+"')");
                    sqltotal.append(" and  m.status  in ( '"+qry.getMatchStatus()+"')");
                }else{
                    sqljoint.append(" and  m.status=  '"+qry.getMatchStatus()+"'");
                    sqltotal.append(" and  m.status=  '"+qry.getMatchStatus()+"'");
                }
            }
            if(qry.getStartTime()!=null){
                sqljoint.append(" and  t.create_time>=  "+qry.getStartTime()+")");
                sqltotal.append(" and  t.create_time>=  "+qry.getStartTime()+")");
            }
            if(qry.getEndTime()!=null){
                sqljoint.append(" and  t.create_time<=  "+qry.getEndTime()+")");
                sqltotal.append(" and  t.create_time<=  "+qry.getEndTime()+")");
            }
            sqljoint.append(" order by t.update_time desc");
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, BetEntity.class);
            List<BetEntity> lst=query.getResultList();
            List<BetRecordDTO> dtos    = new ArrayList<>();
            for(BetEntity entity : lst){

                dtos.add(entityToDto(entity));
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("dtos",dtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
            Integer winning=betDao.findTotalWinning(userId);
            Integer totalCount=betDao.countByUserIdAndStatus(userId,OrderStatusEnum.FINISHED.status);
            resultJson.put("winning",changeB(winning,totalCount));
        }finally {
            em.close();
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    private String changeB(Integer winning, Integer totalCount) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        if(totalCount==0)
            return "0";
        return numberFormat.format((float) winning / (float) totalCount * 100);
    }

    //将 model 转成 dto
    private BetRecordDTO entityToDto(BetEntity entity) {
//        String baseUrl=sysParaDao.findParaValueById(ConstantUtil.localURL);
        BetRecordDTO dto = BetRecordConverter.INSTANCE.entity2dto(entity);
        dto.setWalletAddress(userDao.findUserWalletAddressByUserId(entity.getUserId()));
        //比赛信息
        MatchEntity match=matchDao.findFirstById(dto.getMatchId());
        dto.setHomeTeam(match.getHomeTeam());
        dto.setHomeTeam_en(match.getHomeTeam_en());
        dto.setHomeTeam_zht(match.getHomeTeam_zht());
        dto.setHomeTeamId(match.getHomeTeamId());
        dto.setGuestTeam(match.getGuestTeam());
        dto.setGuestTeam_en(match.getGuestTeam_en());
        dto.setGuestTeam_zht(match.getGuestTeam_zht());
        dto.setGuestTeamId(match.getGuestTeamId());
        dto.setHomeCover(match.getHomeTeamCover());
        dto.setGuestCover(match.getGuestTeamCover());
        dto.setMatchTime(match.getMatchTime());
        dto.setHomeScore(match.getHomeScore());
        dto.setGuestScore(match.getGuestScore());
        dto.setEndTime(match.getEndTime());
        dto.setTeamStatus(match.getStatus());
        //盘口信息
        QuotationEntity quE=quotationDao.findFirstById(dto.getQuotationId());
        if(dto.getQuotationType()== QuotationTypeEnum.QUOTATION_QC.type){//全场
            dto.setWinTeamId(quE.getWinTeamId());
            if(StringUtils.isNotBlank(quE.getWinTeamId())){
                CompetitionTeamEntity ct= competitionTeamDao.findFirstById(quE.getWinTeamId());
                if(ct!=null){
                    dto.setWinTeamName(ct.getName());
                    dto.setWinTeamName_en(ct.getName_en());
                    dto.setWinTeamName_zht(ct.getName_zht());
                    if(StringUtils.isNotBlank(ct.getCover())){
                        dto.setWinTeamCover(ct.getCover());
                    }
                }
            }
        }else if(dto.getQuotationType()== QuotationTypeEnum.QUOTATION_RF.type){// 让分
            dto.setWinTeamId(quE.getWinTeamId());
            if(StringUtils.isNotBlank(quE.getWinTeamId())){
                CompetitionTeamEntity ct= competitionTeamDao.findFirstById(quE.getWinTeamId());
                if(ct!=null){
                    dto.setWinTeamName(ct.getName());
                    dto.setWinTeamName_en(ct.getName_en());
                    dto.setWinTeamName_zht(ct.getName_zht());
                    if(StringUtils.isNotBlank(ct.getCover())){
                        dto.setWinTeamCover(ct.getCover());
                    }
                }
            }
            if(match.getHomeTeamId().equals(quE.getWinTeamId())){
                dto.setGivePointsLeft(String.valueOf(-quE.getGivePoints()));
            }else{
                dto.setGivePointsLeft(String.valueOf(quE.getGivePoints()));
            }

        }else if(dto.getQuotationType()== QuotationTypeEnum.QUOTATION_DX.type){//比大小
            if(match.getHomeTeamId().equals(quE.getWinTeamId())){//如果是押的是主队
                dto.setGivePointsLeft(">"+quE.getSpecificSize());
            }else{
                dto.setGivePointsLeft("<"+quE.getSpecificSize());
            }
        }
        dto.setOdds(quE.getOdds());
        return dto;
    }

    @Override
    public ServerResponse queryBetRecordDetail(String userId, String id, int transactionType) {
        if(TransactionTypeEnum.TRANSACTION_BET.type==transactionType){
            BetEntity entity=betDao.findFirstById(id);
            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(entityToDto(entity));
        }
        QuotationEntity entity=quotationDao.findFirstById(id);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(entityToDtoQ(entity));
    }

    @Override
    public ServerResponse queryProfitByCondition(String userId, ProfitQuery qry) {
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" select * from  tbl_bet_record as t   where t.status= "+OrderStatusEnum.FINISHED.status);
            sqltotal.append(" select count(1) from  tbl_bet_record as t where  t.status= "+OrderStatusEnum.FINISHED.status);
            if(StringUtils.isNotBlank(userId)){
                sqljoint.append(" and  t.user_id=  '"+userId+"'");
                sqltotal.append(" and  t.user_id=  '"+userId+"'");
            }
            if(qry.getQuotationType()!=null){
                sqljoint.append(" and  t.quotation_type=  '"+qry.getQuotationType()+"'");
                sqltotal.append(" and  t.quotation_type=  '"+qry.getQuotationType()+"'");
            }
            if(qry.getStartTime()!=null){
                sqljoint.append(" and  t.create_time>=  "+qry.getStartTime()+")");
                sqltotal.append(" and  t.create_time>=  "+qry.getStartTime()+")");
            }
            if(qry.getEndTime()!=null){
                sqljoint.append(" and  t.create_time<=  "+qry.getEndTime()+")");
                sqltotal.append(" and  t.create_time<=  "+qry.getEndTime()+")");
            }
            sqljoint.append(" order by t.update_time desc");
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, BetEntity.class);
            List<BetEntity> lst=query.getResultList();
            List<BetRecordDTO> dtos    = new ArrayList<>();
            for(BetEntity entity : lst){

                dtos.add(entityToDto(entity));
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("dtos",dtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
        }finally {
            em.close();
        }
        List<List<Object>> lst= betDao.findTotalSituation(userId, OrderStatusEnum.FINISHED.status);
        if(lst!=null&&lst.size()>0){
            resultJson.put("totalOrder",TmFunctions.changeInt(lst.get(0).get(0)));
            resultJson.put("totalProfit",TmFunctions.changeBigDecimal(lst.get(0).get(1)));
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse queryTransferRecordPage(String userId, TransferRecordAllQuery qry) {
        JSONObject resultJson = new JSONObject();
        UserEntity user= userDao.findByUserId(userId);
        if(user==null||StringUtils.isBlank(user.getWalletAddress()))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.WALLET_NOT_SUCH);

        JSONObject json = new JSONObject();
        Map<String, Object> params=new HashMap<>();
        params.put("start", String.valueOf(qry.getPageNum()*qry.getPageSize()));
        params.put("limit", String.valueOf(qry.getPageSize()));
        params.put("count", String.valueOf(true));
        params.put("sort", "-timestamp");
        params.put("relatedAddress", user.getWalletAddress());//查询账户信息系
        params.put("confirm", qry.getConfirm());//支付结果

        if("1".equals(qry.getType())){//充值
            params.put("toAddress", user.getWalletAddress());
        }else{//提现
            params.put("fromAddress", user.getWalletAddress());
        }
        if(StringUtils.isNotBlank(qry.getPayType())){
            String token = "";
            String[] list=qry.getPayType().split(",");
            for(String payType:list){
                if("1".equals(payType)){//USDT
                    token+="TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"+",";
                }else if("2".equals(payType)){//BET
                    token+="TLa6cmHtguZiwdmvFdydXh9GpicSbnzVjt"+",";
                }
            }
            if(StringUtils.isNotBlank(token)){
                params.put("tokens", token.substring(0,token.length()-1));//截取最后一个逗号
            }
        }

        json= HttpHelpUtil.sendRequestGet2Status(token_trc20,params);
        List<TransferRecordDTO> lst=new ArrayList<>();
        if(json!=null){
            JSONArray array=json.getJSONArray("token_transfers");
            for(int i=0;i<array.size();i++){
                JSONObject obj=array.getJSONObject(i);
                TransferRecordDTO dto=new TransferRecordDTO();
                dto.setUserId(userId);
                String tokenDecimal=obj.getJSONObject("tokenInfo").getString("tokenDecimal");
                dto.setTransactionId(obj.getString("transaction_id"));
                dto.setPayTime(obj.getLong("block_ts"));
                dto.setAmount(obj.getBigDecimal("quant").divide(new BigDecimal(getCF(tokenDecimal))));
                dto.setPayTypeName(obj.getJSONObject("tokenInfo").getString("tokenAbbr"));
                dto.setContractType(obj.getJSONObject("tokenInfo").getString("tokenType"));
                lst.add(dto);
            }
            resultJson.put("dtos",lst);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",json.get("total"));
            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
        }else{
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
    }

    @Override
    public ServerResponse queryQuotationRecordByCondition(String userId, BetRecordQuery qry) {
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" SELECT t.* FROM tbl_quotation as t LEFT JOIN tbl_match as m on t.match_id=m.id  where 1=1 ");
            sqltotal.append(" select count(1) from  tbl_quotation as t  LEFT JOIN tbl_match as m on t.match_id=m.id  where 1=1 ");
            if(StringUtils.isNotBlank(userId)){
                sqljoint.append(" and  t.user_id=  '"+userId+"'");
                sqltotal.append(" and  t.user_id=  '"+userId+"'");
            }
            if(qry.getQuotationType()!=null){
                sqljoint.append(" and  t.quotation_type=  '"+qry.getQuotationType()+"'");
                sqltotal.append(" and  t.quotation_type=  '"+qry.getQuotationType()+"'");
            }
            if(StringUtils.isNotBlank(qry.getMatchStatus())){
                List<String> str=Arrays.asList(qry.getMatchStatus().split(","));
                if(str.size()>1){
                    sqljoint.append(" and  m.status  in (  '"+qry.getMatchStatus()+"')");
                    sqltotal.append(" and  m.status  in ( '"+qry.getMatchStatus()+"')");
                }else{
                    sqljoint.append(" and  m.status=  '"+qry.getMatchStatus()+"'");
                    sqltotal.append(" and  m.status=  '"+qry.getMatchStatus()+"'");
                }
            }
            if(qry.getStartTime()!=null){
                sqljoint.append(" and  t.create_time>=  "+qry.getStartTime()+")");
                sqltotal.append(" and  t.create_time>=  "+qry.getStartTime()+")");
            }
            if(qry.getEndTime()!=null){
                sqljoint.append(" and  t.create_time<=  "+qry.getEndTime()+")");
                sqltotal.append(" and  t.create_time<=  "+qry.getEndTime()+")");
            }
            sqljoint.append(" order by t.update_time desc");
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, QuotationEntity.class);
            List<QuotationEntity> lst=query.getResultList();
            List<BetRecordDTO> dtos    = new ArrayList<>();
            for(QuotationEntity entity : lst){

                dtos.add(entityToDtoQ(entity));
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("dtos",dtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
            Integer winning=betDao.findTotalWinning(userId);
            Integer totalCount=betDao.countByUserIdAndStatus(userId,OrderStatusEnum.FINISHED.status);
            resultJson.put("winning",changeB(winning,totalCount));
        }finally {
            em.close();
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse queryQuotationRProfitByCondition(String userId, ProfitQuery qry) {
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" select * from  tbl_quotation as t   where fee!=0 and t.status= "+OrderStatusEnum.FINISHED.status);
            sqltotal.append(" select count(1) from  tbl_quotation as t   where fee!=0  and t.status= "+OrderStatusEnum.FINISHED.status);
            if(StringUtils.isNotBlank(userId)){
                sqljoint.append(" and  t.user_id=  '"+userId+"'");
                sqltotal.append(" and  t.user_id=  '"+userId+"'");
            }
            if(qry.getQuotationType()!=null){
                sqljoint.append(" and  t.quotation_type=  '"+qry.getQuotationType()+"'");
                sqltotal.append(" and  t.quotation_type=  '"+qry.getQuotationType()+"'");
            }
            if(qry.getStartTime()!=null){
                sqljoint.append(" and  t.create_time>=  "+qry.getStartTime()+")");
                sqltotal.append(" and  t.create_time>=  "+qry.getStartTime()+")");
            }
            if(qry.getEndTime()!=null){
                sqljoint.append(" and  t.create_time<=  "+qry.getEndTime()+")");
                sqltotal.append(" and  t.create_time<=  "+qry.getEndTime()+")");
            }
            sqljoint.append(" order by t.update_time desc");
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, QuotationEntity.class);
            List<QuotationEntity> lst=query.getResultList();
            List<BetRecordDTO> dtos    = new ArrayList<>();
            for(QuotationEntity entity : lst){

                dtos.add(entityToDtoQ(entity));
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("dtos",dtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
        }finally {
            em.close();
        }
        List<List<Object>> lst= quotationDao.findTotalSituation(userId, OrderStatusEnum.FINISHED.status);
        if(lst!=null&&lst.size()>0){
            resultJson.put("totalOrder",TmFunctions.changeInt(lst.get(0).get(0)));
            resultJson.put("totalProfit",TmFunctions.changeBigDecimal(lst.get(0).get(1)));
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse queryTransferRecordTrxPage(String userId, TransferRecordAllQuery qry) {
        JSONObject resultJson = new JSONObject();
        UserEntity user= userDao.findByUserId(userId);
        if(user==null||StringUtils.isBlank(user.getWalletAddress()))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.WALLET_NOT_SUCH);

        JSONObject json = new JSONObject();
        Map<String, Object> params=new HashMap<>();
        params.put("start", String.valueOf(qry.getPageNum()*qry.getPageSize()));
        params.put("limit", String.valueOf(qry.getPageSize()));
        params.put("count", String.valueOf(true));
        params.put("sort", "-timestamp");
        params.put("address", user.getWalletAddress());//查询账户信息系
        params.put("confirm", qry.getConfirm());//支付结果
        params.put("tokens", "_");//trx

        if("1".equals(qry.getType())){//充值
            params.put("toAddress", user.getWalletAddress());
        }else{//提现
            params.put("fromAddress", user.getWalletAddress());
        }
//        if(StringUtils.isNotBlank(qry.getPayType())){
//            String token = "";
//            String[] list=qry.getPayType().split(",");
//            for(String payType:list){
//                if("3".equals(payType)){//USDT
//                    token+="TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"+",";
//                }else if("2".equals(payType)){//BET
//                    token+="TLa6cmHtguZiwdmvFdydXh9GpicSbnzVjt"+",";
//                }
//            }
//            if(StringUtils.isNotBlank(token)){
//                params.put("tokens", token.substring(0,token.length()-1));//截取最后一个逗号
//            }
//        }

        json= HttpHelpUtil.sendRequestGet2Status(token_trc10,params);
        List<TransferRecordDTO> lst=new ArrayList<>();
        if(json!=null){
            JSONArray array=json.getJSONArray("data");
            for(int i=0;i<array.size();i++){
                JSONObject obj=array.getJSONObject(i);
                TransferRecordDTO dto=new TransferRecordDTO();
                dto.setUserId(userId);
                String tokenDecimal=obj.getJSONObject("tokenInfo").getString("tokenDecimal");
                dto.setTransactionId(obj.getString("transactionHash"));
                dto.setPayTime(obj.getLong("timestamp"));
                dto.setAmount(obj.getBigDecimal("amount").divide(new BigDecimal(getCF(tokenDecimal))));//乘方
                dto.setPayTypeName(obj.getJSONObject("tokenInfo").getString("tokenAbbr"));
                dto.setContractType(obj.getJSONObject("tokenInfo").getString("tokenType"));
                lst.add(dto);
            }
            resultJson.put("dtos",lst);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",json.get("total"));
            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
        }else{
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
    }

    private BetRecordDTO entityToDtoQ(QuotationEntity quE) {
//        String baseUrl=sysParaDao.findParaValueById(ConstantUtil.localURL);
        BetRecordDTO dto = new BetRecordDTO();
        dto.setId(quE.getId());
        dto.setBlockHash(quE.getBlockHash());
        dto.setTransactionHash(quE.getTransactionHash());
        dto.setCreateTime(quE.getCreateTime());
        dto.setBetCount(betDao.countByQuotationId(quE.getId()));
        dto.setBetAmountSum(betDao.totalBetAmountByQuotationId(quE.getId()));
        dto.setUserId(quE.getUserId());
        dto.setLeagueMatch(quE.getLeagueMatch());
        dto.setMatchId(quE.getMatchId());
        dto.setQuotationType(quE.getQuotationType());
        dto.setStatus(quE.getStatus());
        dto.setProfitAmount(quE.getProfitAmount()==null?new BigDecimal(0): quE.getProfitAmount());
        dto.setBond(quE.getBond());
        dto.setBackBond(quE.getBackBond());
        dto.setFee(quE.getFee());
        dto.setQuotationId(quE.getId());
        dto.setTransactionType(TransactionTypeEnum.QUOTATION_QUOTATION.type);

        //比赛信息
        MatchEntity match=matchDao.findFirstById(quE.getMatchId());
        dto.setHomeTeam(match.getHomeTeam());
        dto.setHomeTeam_zht(match.getHomeTeam_zht());
        dto.setHomeTeam_en(match.getHomeTeam_en());
        dto.setHomeTeamId(match.getHomeTeamId());
        dto.setGuestTeam(match.getGuestTeam());
        dto.setGuestTeam_en(match.getGuestTeam_en());
        dto.setGuestTeam_zht(match.getGuestTeam_zht());
        dto.setGuestTeamId(match.getGuestTeamId());
        dto.setHomeCover(match.getHomeTeamCover());
        dto.setGuestCover(match.getGuestTeamCover());
        dto.setMatchTime(match.getMatchTime());
        dto.setHomeScore(match.getHomeScore());
        dto.setGuestScore(match.getGuestScore());
        dto.setEndTime(match.getEndTime());
        dto.setTeamStatus(match.getStatus());
        //盘口信息
        if(dto.getQuotationType()== QuotationTypeEnum.QUOTATION_QC.type){//全场
            dto.setWinTeamId(quE.getWinTeamId());
            if(StringUtils.isNotBlank(quE.getWinTeamId())){
                CompetitionTeamEntity ct= competitionTeamDao.findFirstById(quE.getWinTeamId());
                if(ct!=null){
                    dto.setWinTeamName(ct.getName());
                    dto.setWinTeamName_en(ct.getName_en());
                    dto.setWinTeamName_zht(ct.getName_zht());
                    if(StringUtils.isNotBlank(ct.getCover())){
                        dto.setWinTeamCover(ct.getCover());
                    }
                }
            }
        }else if(dto.getQuotationType()== QuotationTypeEnum.QUOTATION_RF.type){// 让分
            dto.setWinTeamId(quE.getWinTeamId());
            if(StringUtils.isNotBlank(quE.getWinTeamId())){
                CompetitionTeamEntity ct= competitionTeamDao.findFirstById(quE.getWinTeamId());
                if(ct!=null){
                    dto.setWinTeamName(ct.getName());
                    dto.setWinTeamName_en(ct.getName_en());
                    dto.setWinTeamName_zht(ct.getName_zht());
                    if(StringUtils.isNotBlank(ct.getCover())){
                        dto.setWinTeamCover(ct.getCover());
                    }
                }
            }
            if(match.getHomeTeamId().equals(quE.getWinTeamId())){
                dto.setGivePointsLeft(String.valueOf(-quE.getGivePoints()));
            }else{
                dto.setGivePointsLeft(String.valueOf(quE.getGivePoints()));
            }

        }else if(dto.getQuotationType()== QuotationTypeEnum.QUOTATION_DX.type){//比大小
            if(match.getHomeTeamId().equals(quE.getWinTeamId())){//如果是押的是主队
                dto.setGivePointsLeft(">"+quE.getSpecificSize());
            }else{
                dto.setGivePointsLeft("<"+quE.getSpecificSize());
            }
        }
        dto.setOdds(quE.getOdds());
        return dto;
    }

    private long  getCF(String tokenDecimal) {
        int n=Integer.valueOf(tokenDecimal);
        long result = 1L;
        for(int i=0;i<n;i++){
            result*=10;
        }
        return result;
    }


//    private String getCoverById(String homeTeamId, String baseUrl) {
//        CompetitionTeamEntity entity= competitionTeamDao.findFirstById(homeTeamId);
//        if(entity!=null&&entity.getCover()!=null){
//            return baseUrl+entity.getCover();
//        }
//        return null;
//    }

}
