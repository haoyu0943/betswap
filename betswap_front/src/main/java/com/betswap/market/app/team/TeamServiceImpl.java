package com.betswap.market.app.team;

import com.alibaba.fastjson.JSONObject;
import com.betswap.market.client.common.YesOrNoStatusEnum;
import com.betswap.market.client.communicate.MessageReadTypeEnum;
import com.betswap.market.client.communicate.MessageTypeEnum;
import com.betswap.market.client.flow.PayTypeEnum;
import com.betswap.market.client.flow.UserRankTypeEnum;
import com.betswap.market.client.flow.converter.RevenueRecordConverter;
import com.betswap.market.client.flow.dto.RevenueRecordDTO;
import com.betswap.market.client.team.dto.TeamDTO;
import com.betswap.market.client.team.qry.TeamRankingQuery;
import com.betswap.market.client.team.qry.TeamRevenueQuery;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.communicate.dao.MyMsgDao;
import com.betswap.market.infrastruture.communicate.entity.MyMsgEntity;
import com.betswap.market.infrastruture.flow.entity.RevenueRecordEntity;
import com.betswap.market.infrastruture.system.dao.RoleBETDao;
import com.betswap.market.infrastruture.system.entity.RoleBETEntity;
import com.betswap.market.infrastruture.team.dao.TeamMemberDao;
import com.betswap.market.infrastruture.team.entity.TeamMemberEntity;
import com.betswap.market.infrastruture.user.dao.UserDao;
import com.betswap.market.infrastruture.user.dao.UserRankRecordDao;
import com.betswap.market.infrastruture.user.entity.UserEntity;
import com.betswap.market.infrastruture.user.entity.UserRankRecordEntity;
import com.betswap.market.infrastruture.utils.constant.DateUtils;
import com.betswap.market.infrastruture.utils.constant.PasswordUtils;
import com.betswap.market.infrastruture.utils.constant.TmDateUtil;
import com.betswap.market.infrastruture.utils.constant.TmFunctions;
import com.betswap.market.infrastruture.utils.language.LanguageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMemberDao teamMemberDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MyMsgDao myMsgDao;

    @Autowired
    private RoleBETDao roleBETDao;

    @Autowired
    private UserRankRecordDao userRankRecordDao;


    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;


    @Override
    public ServerResponse teamMember(String userId, Integer ifLoss) {
        UserEntity user=userDao.findByUserId(userId);
        if(user==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.DATA_NOT_EXIST);
        JSONObject resultJson = new JSONObject();
        TeamMemberEntity team=teamMemberDao.findByUserId(userId);
        List<TeamDTO> dtos=new ArrayList<>();
        if(team==null){
            resultJson.put("teamId",null);//团队id
            resultJson.put("persons",dtos);//团队成员排名
            resultJson.put("totalRevenueUSDT",0);//团队总收益--USDT
            resultJson.put("totalRevenueBET",0);//团队总收益--BET
            resultJson.put("todayProfitUSDT",0);//团队今日收益-usdt
            resultJson.put("todayProfitBET",0);//团队今日收益-bet
            resultJson.put("subordinateCount",0);//直属下级数量-直推人数
            resultJson.put("todayAddCount",0);//今日新增
            resultJson.put("totalPerson",0);//团队总人数
        }else{
            TeamRankingQuery qry=new TeamRankingQuery();
            qry.setPageNum(0);
            qry.setPageSize(10);
            qry.setIfLoss(ifLoss);
            qry.setTeamId(team.getTeamId());
            ServerResponse res= findTeamRanking(userId,qry);
            resultJson = (JSONObject) res.getData();
            resultJson.put("teamId",team.getTeamId());//团队id
            resultJson.put("totalRevenueUSDT",getTeamSYByCondition(team.getTeamId(),PayTypeEnum.PAY_USDT.type,null,null,ifLoss));//团队总收益--USDT
            resultJson.put("totalRevenueBET",getTeamSYByCondition(team.getTeamId(),PayTypeEnum.PAY_BET.type,null,null, ifLoss));//团队总收益--BET
            resultJson.put("todayProfitUSDT",getTeamSYByCondition(team.getTeamId(),PayTypeEnum.PAY_USDT.type, DateUtils.getDayBegin(),DateUtils.getDayEnd(), ifLoss));//团队今日收益-usdt
            resultJson.put("todayProfitBET",getTeamSYByCondition(team.getTeamId(),PayTypeEnum.PAY_USDT.type,DateUtils.getDayBegin(),DateUtils.getDayEnd(), ifLoss));//团队今日收益-bet
            resultJson.put("subordinateCunt",teamMemberDao.countByParentId(userId));//直属下级数量
            resultJson.put("todayAddCount",teamMemberDao.countByTodayAddCount(team.getTeamId()));//今日新增
            resultJson.put("totalPerson",resultJson.get("total"));//团队总人数
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    //根据添加查询流水
    @Override
    public ServerResponse findTeamRanking(String userId, TeamRankingQuery qry) {
        com.alibaba.fastjson.JSONObject resultJson = new com.alibaba.fastjson.JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append("select * from ( ");
            sqljoint.append(" select team.team_id,team.user_id,IFNULL(sumUSDT,0) as sumUSDT,IFNULL(sumBET,0) as sumBET from team_member as team  LEFT JOIN ( ");
            sqljoint.append(" select team_id as teamId,user_id as userId, sum(case pay_type when 1 then amount ELSE 0 END ) as sumUSDT,sum(case pay_type when 2 then amount ELSE 0 END ) as sumBET " +
                    "from  revenue_record t where 1=1 ");
            sqltotal.append(" select count(1) from team_member as t where   1=1 ");
            if(StringUtils.isNotBlank(qry.getTeamId())){
                sqljoint.append(" and  t.team_id=  '"+qry.getTeamId()+"'");
                sqltotal.append(" and  t.team_id=  '"+qry.getTeamId()+"'");
            }
            if(qry.getIfLoss()== YesOrNoStatusEnum.NO.status){
                sqljoint.append(" and  t.amount>0");
            }
            sqljoint.append(" group by  t.team_id,t.user_id ");
            sqljoint.append("  ) as user_team on team.user_id=user_team.userId ");
            sqljoint.append(" ) as t order by t.sumUSDT desc ");
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql);
            List list=query.getResultList();
            List<TeamDTO> dtos    = new ArrayList<>();
            for(Object row : list){
                Object[] cells= (Object[]) row;
                TeamDTO dto1=new TeamDTO();
                dto1.setTeamId(TmFunctions.changeString(cells[0]));
                dto1.setUserId(TmFunctions.changeString(cells[1]));
                dto1.setInvitationCode(userDao.findInvittionCodeByUserId(dto1.getUserId()));
                dto1.setUsdtNumber(TmFunctions.changeBigDecimalT(cells[2]));
                dto1.setBetNumber(TmFunctions.changeBigDecimalT(cells[3]));
                dtos.add(dto1);
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("persons",dtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
        }finally {
            em.close();
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse findTeamRevenue(String userId, TeamRevenueQuery qry) {
        com.alibaba.fastjson.JSONObject resultJson = new com.alibaba.fastjson.JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" select * from  revenue_record as t   where 1=1 ");
            sqltotal.append(" select count(1) from  revenue_record as t   where 1=1 ");
            if(StringUtils.isNotBlank(qry.getTeamId())){
                sqljoint.append(" and  t.team_id=  '"+qry.getTeamId()+"'");
                sqltotal.append(" and  t.team_id=  '"+qry.getTeamId()+"'");
            }
            if(qry.getIfLoss()== YesOrNoStatusEnum.NO.status){
                sqljoint.append(" and  t.amount>0");
                sqltotal.append(" and  t.amount>0");
            }
            if(qry.getPayType()!=null){
                sqljoint.append(" and  t.pay_type=  '"+qry.getPayType()+"'");
                sqltotal.append(" and  t.pay_type=  '"+qry.getPayType()+"'");
            }
            if(qry.getStartTime()!=null){
                sqljoint.append(" and  t.create_time>=  "+qry.getStartTime()+"");
                sqltotal.append(" and  t.create_time>=  "+qry.getStartTime()+"");
            }
            if(qry.getEndTime()!=null){
                sqljoint.append(" and  t.create_time<=  "+qry.getEndTime()+"");
                sqltotal.append(" and  t.create_time<=  "+qry.getEndTime()+"");
            }
            sqljoint.append(" order by t.create_time desc");
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, RevenueRecordEntity.class);
            List<RevenueRecordEntity> lst=query.getResultList();
            List<RevenueRecordDTO> dtos    = new ArrayList<>();
            for(RevenueRecordEntity entity : lst){
                RevenueRecordDTO dto= RevenueRecordConverter.INSTANCE.entity2dto(entity);
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
    public ServerResponse myTeamMember(String userId) {
        UserEntity user=userDao.findByUserId(userId);
        if(user==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.DATA_NOT_EXIST);
        JSONObject resultJson = new JSONObject();
        TeamMemberEntity team=teamMemberDao.findByUserId(userId);
        List<TeamDTO> dtos=new ArrayList<>();
        RoleBETEntity roleBet= roleBETDao.findFirstByAgentRank(String.valueOf(user.getRank()+1));

        if(team==null){
            resultJson.put("teamId",null);//团队id
            resultJson.put("persons",dtos);//团队成员排名
            resultJson.put("totalRevenueUSDT",0);//团队总收益--USDT
            resultJson.put("todayProfitUSDT",0);//团队今日收益-usdt
            resultJson.put("subordinateCunt",0);//直属下级数量
            resultJson.put("todayAddCount",0);//今日新增
            resultJson.put("totalPerson",0);//团队总人数
        }else{
            TeamRankingQuery qry=new TeamRankingQuery();
            qry.setPageNum(0);
            qry.setPageSize(10);
            qry.setIfLoss(0);
//            qry.setTeamId(team.getTeamId());
            ServerResponse res= findTeamRanking(userId,qry);
            resultJson = (JSONObject) res.getData();
            resultJson.put("teamId",team.getTeamId());//团队id
            resultJson.put("totalRevenueUSDT",getTeamSYByCondition(team.getTeamId(),PayTypeEnum.PAY_USDT.type,null,null,0));//团队总收益--USDT
            resultJson.put("todayProfitUSDT",getTeamSYByCondition(team.getTeamId(),PayTypeEnum.PAY_USDT.type, DateUtils.getDayBegin(),DateUtils.getDayEnd(), 0));//团队今日收益-usdt
            resultJson.put("subordinateCunt",teamMemberDao.countByParentId(userId));//直属下级数量
            resultJson.put("todayAddCount",teamMemberDao.countByTodayAddCount(team.getTeamId()));//今日新增
            resultJson.put("totalPerson",resultJson.get("total"));//团队总人数
        }
        if(roleBet!=null){
            resultJson.put("upgradePerson",roleBet.getSubordinateCount());//升级需要人数
            resultJson.put("upgradeUSDT",roleBet.getThresholdIncome());//升级需要金额
        }else{
            resultJson.put("upgradePerson",null);//升级需要人数
            resultJson.put("upgradeUSDT",null);//升级需要金额
        }
        resultJson.put("rank",user.getRank());//用户等级
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    /**
     *
     * @param teamId
     * @param payType
     * @param start
     * @param end
     * @param ifLoss
     * @return
     */
    private BigDecimal getTeamSYByCondition(String teamId, Integer payType, Date start, Date end, Integer ifLoss) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            sqljoint.append(" select IFNULL(SUM(t.amount),0) from revenue_record t where  1=1 ");
            if(StringUtils.isNotBlank(teamId)){
                sqljoint.append(" and  t.team_id=  '"+teamId+"'");
            }
            if(payType!=null){
                sqljoint.append(" and  t.pay_type=  '"+payType+"'");
            }
            if(start!=null){
                sqljoint.append(" and  t.create_time>=  "+start.getTime()+"");
            }
            if(end!=null){
                sqljoint.append(" and  t.create_time<=  "+end.getTime()+"");
            }
            if(ifLoss== YesOrNoStatusEnum.NO.status){
                sqljoint.append(" and  t.amount>0");
            }
            Query query = em.createNativeQuery(sqljoint.toString());
            List lst=query.getResultList();
            return  TmFunctions.changeBigDecimal(lst.get(0));
        }finally {
            em.close();
        }
    }


    //检查并更新用户等级
    private void checkAndUpdateUserRank(UserEntity parentUser, Integer rankType, String subordinateId) {
        int count=teamMemberDao.countByParentId(parentUser.getUserId());
        RoleBETEntity rankSet= roleBETDao.findFirstBySubordinateCount(count);
        BigDecimal totalUsdt= teamMemberDao.findTotalUsdt(parentUser.getUserId());
        if(rankSet!=null&&totalUsdt.compareTo(new BigDecimal(rankSet.getThresholdIncome()))==1){
            int userRank=TmFunctions.changeInt(rankSet.getAgentRank());
            if( UserRankTypeEnum.RANK_DEFAULT.type==rankType){
                parentUser.setRank(userRank);
                userDao.save(parentUser);
                UserRankRecordEntity record=UserRankRecordEntity.builder()
                        .rank(userRank)
                        .userId(parentUser.getUserId())
                        .subordinateId(subordinateId)
                        .createTime(System.currentTimeMillis())
                        .build();
                userRankRecordDao.save(record);
            }
        }
    }

    @Transactional
    @Override
    public ServerResponse joinTeamByInviter(String userId, String code) {

        UserEntity user=userDao.findFirstByInvitationCode(code);
        if(user==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.INVITATION_CODE_INVALID);
        if(userId.equals(user.getUserId()))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.INVITATION_CODE_INVALID);
        String  initiatorId=user.getUserId();
        TeamMemberEntity entity=teamMemberDao.findByUserId(userId);
        if(entity!=null)//该用户已加入了团队
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ALREADY_EXISTS_DATA);

        TeamMemberEntity initiatorEntity=teamMemberDao.findByUserId(initiatorId);
        String teamId;
        if(initiatorEntity==null){//创建团队
            TeamMemberEntity team1= TeamMemberEntity.builder()
                    .id(UUID.randomUUID().toString())
                    .createTime(System.currentTimeMillis())
                    .userId(initiatorId)
                    .lowerCount(1)
                    .teamId("TD"+ PasswordUtils.randomPW(7))//团队
                    .build();
            teamMemberDao.save(team1);
            teamId=team1.getTeamId();
        }else{
            teamId=initiatorEntity.getTeamId();
            //修改直推人数
            initiatorEntity.setLowerCount(initiatorEntity.getLowerCount()+1);
            teamMemberDao.save(initiatorEntity);
        }
        teamMemberDao.save(TeamMemberEntity.builder()
                .id(UUID.randomUUID().toString())
                .createTime(System.currentTimeMillis())
                .parentId(initiatorId)
                .userId(userId)
                .lowerCount(0)
                .betNumber(new BigDecimal(0))
                .payCount(0)
                .teamId(teamId)
                .build());

        // 生成消息
        MyMsgEntity my=MyMsgEntity.builder()
                .userId(initiatorId)
                .initiator(userId)
                .content(LanguageUtils.changeLanguage("describe_0003"))
                .readStatus(MessageReadTypeEnum.UNREAD.type)
                .type(MessageTypeEnum.ACCOUNT.type)
                .createTime(System.currentTimeMillis())
                .build();
        myMsgDao.save(my);

        checkAndUpdateUserRank(user, UserRankTypeEnum.RANK_DEFAULT.type,userId);

        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);

    }

}
