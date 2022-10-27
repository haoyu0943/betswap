package com.betswap.market.app.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.betswap.market.app.user.service.UserService;
import com.betswap.market.app.user.service.VerifyService;
import com.betswap.market.client.communicate.MessageReadTypeEnum;
import com.betswap.market.client.communicate.MessageTypeEnum;
import com.betswap.market.client.flow.PayTypeEnum;
import com.betswap.market.client.flow.UserRankTypeEnum;
import com.betswap.market.client.flow.converter.RevenueRecordConverter;
import com.betswap.market.client.flow.dto.FlowRecordDTO;
import com.betswap.market.client.flow.dto.RevenueCenterDto;
import com.betswap.market.client.flow.dto.RevenueRecordDTO;
import com.betswap.market.client.flow.vo.query.FlowRecordAllQuery;
import com.betswap.market.client.flow.vo.query.MyRevenueCenterQuery;
import com.betswap.market.client.quotation.OrderStatusEnum;
import com.betswap.market.client.system.enums.IPLangEnum;
import com.betswap.market.client.user.converter.*;
import com.betswap.market.client.user.dto.*;
import com.betswap.market.client.user.enums.UserPrivilegeEnum;
import com.betswap.market.client.user.enums.UserRankStatusEnum;
import com.betswap.market.client.user.vo.UpdateUserInfoCmd;
import com.betswap.market.client.user.vo.cmd.*;
import com.betswap.market.client.user.vo.dto.TransMoneyDto;
import com.betswap.market.client.user.vo.login.LoginUserByPasswordCmd;
import com.betswap.market.client.user.vo.login.LoginUserByPhoneCmd;
import com.betswap.market.client.user.vo.login.LoginUserBySmsVerifyCmd;
import com.betswap.market.client.user.vo.quey.*;
import com.betswap.market.client.user.vo.register.RegisterUserCmd;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.communicate.dao.MyMsgDao;
import com.betswap.market.infrastruture.communicate.dao.SysMsgDao;
import com.betswap.market.infrastruture.communicate.dao.SysMsgHaveReadDao;
import com.betswap.market.infrastruture.communicate.entity.MyMsgEntity;
import com.betswap.market.infrastruture.flow.dao.*;
import com.betswap.market.infrastruture.flow.entity.*;
import com.betswap.market.infrastruture.quotation.dao.QuotationDao;
import com.betswap.market.infrastruture.system.dao.DictionaryDao;
import com.betswap.market.infrastruture.system.dao.RoleBETDao;
import com.betswap.market.infrastruture.system.dao.SysIpCountryDao;
import com.betswap.market.infrastruture.system.dao.SysParaDao;
import com.betswap.market.infrastruture.system.entity.RoleBETEntity;
import com.betswap.market.infrastruture.system.entity.SysIpCountryEntity;
import com.betswap.market.infrastruture.team.dao.TeamMemberDao;
import com.betswap.market.infrastruture.team.entity.TeamMemberEntity;
import com.betswap.market.infrastruture.user.dao.*;
import com.betswap.market.infrastruture.user.entity.*;
import com.betswap.market.infrastruture.utils.Md5.DesUtil;
import com.betswap.market.infrastruture.utils.Md5.Md5Crypt;
import com.betswap.market.infrastruture.utils.String.IPUtil;
import com.betswap.market.infrastruture.utils.constant.*;
import com.betswap.market.infrastruture.utils.helper.idgenerator.IDGenerator;
import com.betswap.market.infrastruture.utils.helper.token.LoginTokenHelper;
import com.betswap.market.infrastruture.utils.language.LanguageUtils;
import com.betswap.market.infrastruture.utils.redis.SjustConstant;
import com.betswap.market.infrastruture.utils.transaction.TransactionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import io.rong.RongCloud;
import io.rong.methods.user.User;
import io.rong.models.*;
import io.rong.models.response.*;
import io.rong.models.user.UserModel;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private QualificationsAttachDao qualificationsAttachDao;
    @Autowired
    private LoginTokenHelper loginTokenHelper;
    @Autowired
    private VerifyService verifyService;
    @Autowired
    private UserQuestionDao userQuestionDao;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserWalletDao userWalletDao;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private MyMsgDao myMsgDao;

    @Autowired
    private TeamMemberDao teamMemberDao;

    @Autowired
    private BETWithdrawalRecordDao tBTWithdrawalRecordDao;

    @Autowired
    private USDTWithdrawalRecordDao usdtWithdrawalRecordDao;



    @Autowired
    private ToReleasedBETRecordDao toReleasedBETRecordDao;

    @Autowired
    private RoleBETDao roleBETDao;

    @Autowired
    private SysParaDao sysParaDao;

    @Autowired
    private RevenueRecordDao revenueRecordDao;

    @Autowired
    private UserRankRecordDao userRankRecordDao;

    @Autowired
    private QuotationDao quotationDao;

    @Autowired
    private SysMsgHaveReadDao sysMsgHaveReadDao;

    @Autowired
    private SysMsgDao sysMsgDao;

    @Autowired
    private AdvertisementDao advertisementDao;

    @Autowired
    private SysIpCountryDao sysIpCountryDao;

    @Transactional
    @Override
    public ServerResponse registerByPassword(RegisterUserCmd cmd) {
        boolean nameExists = userDao.existsByUserName(cmd.getUserName());
        if(nameExists)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.USERNAME_EXIST);

        ResponseEnum responseEnum = null;

        boolean phoneExists = userDao.existsByUserPhoneAndPhoneRegionNumber(cmd.getUserPhone(),cmd.getPhoneRegionNumber());
        if(phoneExists)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PHONE_EXIST);
        responseEnum = verifyService.checkPhoneCode(cmd.getUserPhone(),cmd.getPhoneRegionNumber(),cmd.getVerificationCode());
        if(ResponseEnum.SUCCESS != responseEnum){
            return ServerResponse.getInstance().fail().responseEnum(responseEnum);
        }

        String userId = IDGenerator.UserIDGenerate();


        //邀请码
        if(StringUtils.isNotBlank(cmd.getInvitationCode())){
            UserEntity parent=userDao.findFirstByInvitationCode(cmd.getInvitationCode());
            if(parent==null)
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.INVITATION_CODE_INVALID);
            String  initiatorId=parent.getUserId();
            TeamMemberEntity initiatorEntity=teamMemberDao.findByUserId(initiatorId);
            String teamId;
            if(initiatorEntity==null){//创建团队
                TeamMemberEntity team1= TeamMemberEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .createTime(System.currentTimeMillis())
                        .userId(initiatorId)
                        .lowerCount(1)
                        .teamId("TD"+PasswordUtils.randomPW(7))//团队
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

            checkAndUpdateUserRank(parent, UserRankTypeEnum.RANK_DEFAULT.type,userId);
        }



        /**
         * Cmd:
         *     userName;
         *     password;
         *     userPhone;
         *     userEmail;
         *     userAvatar;
         */
        UserEntity user = UserConverter.INSTANCE.cmd2entity(cmd);

        /**
         *     userID;
         *     userName;
         *     password;
         *     userPhone;
         *     userEmail;
         *     userAvatar;
         *
         *     balance;         //账户余额
         *     alreadyIdCheck;  //是否已经实名认证
         *     idCard;          //身份证
         *     privilege;       //用户特权级
         */
        user.setUserId(userId);
        user.setTotalBETRevenue(new BigDecimal(0));
        user.setTotalUSDTRevenue(new BigDecimal(0));
        user.setPrivilege(UserPrivilegeEnum.NORMAL.status);
        user.setPassword(Md5Crypt.EncoderByMd5(cmd.getPassword()));
        if(StringUtils.isNotBlank(cmd.getMoneyPassword())) {
            user.setMoneyPassword(Md5Crypt.EncoderByMd5(cmd.getMoneyPassword()));
        }
        user.setCreateTime( System.currentTimeMillis());
        user.setRank(UserRankStatusEnum.ORDINARY.status);
        String code=PasswordUtils.randomPW(7);
        user.setInvitationCode("M"+code);//邀请码
        user.setLatestOnlineTime(System.currentTimeMillis());
        user.setOnlineDays(1);
        //没有是在其他支付创建还是？
        try{
            JSONObject json=TransactionUtils.createTronAdr(sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
            if("SUCCESS".equals(json.getString("code"))){
                user.setWalkey(DesUtil.encrypt(json.getString("privateKey")));
                user.setWalletAddress(json.getString("address"));
            }else{
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }

        UserEntity result = userDao.save(user);
        if(result == null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);

        try{
            UserWalletEntity wallet=UserWalletEntity.builder()
                    .userId(user.getUserId())
                    .usdt(new BigDecimal(0))
                    .ownBet(new BigDecimal(0))
                    .build();
            wallet.setOwnBetKey(DesUtil.encryptWal(new BigDecimal(0)));
            UserWalletEntity walletE =userWalletDao.save(wallet);
            if(walletE == null)
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ABNORMAL_AMOUNT);
        }
        //
        JSONObject returnObject = new JSONObject();
        try {
            RongCloud rongCloud =  getRongCloud();
            User rongCloudUser = rongCloud.user;
            UserModel curuser = new UserModel()
                    .setId(user.getUserId())
                    .setName(user.getUserName())
                    .setPortrait("_");
            TokenResult rongCloudresult = rongCloudUser.register(curuser);
            String rongCloudToken=rongCloudresult.getToken();
            //保存
            if(rongCloudToken!=null&&!rongCloudToken.equals("")){
                user.setRyToken(rongCloudToken);
                userDao.save(user);
            }
            returnObject.put("rongCloudToken",rongCloudToken);
        }
        catch(Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.RONGCLOUDY_ERROR);

        }
        returnObject.put("userId",user.getUserId());
        String token = loginTokenHelper.createToken(user.getUserId());
        returnObject.put("userToken",token);



        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(returnObject);
    }


    //检查并更新用户等级
    private void checkAndUpdateUserRank(UserEntity parentUser,Integer rankType, String subordinateId) {
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
    public ServerResponse loginUserByPassword(LoginUserByPasswordCmd cmd) {
        UserEntity user = userDao.findByUserName(cmd.getAccount());

        //用户不存在
        if(user == null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ACCOUNT_NOT_FOUND);
        else if(user.getPrivilege() == UserPrivilegeEnum.FREEZE_LOGIN.status){
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ACCOUNT_PRIVILEGE_WRONG);
        }
        else if(user.getPassword() == null){
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PASSWORD_NULL);
        }
        //密码错误
        else if(!user.getPassword().equals(Md5Crypt.EncoderByMd5(cmd.getPassword()))){
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PASSWORD_WRONG);
        }
        else
        {
            String token = loginTokenHelper.createToken(user.getUserId());
            if (!StringUtils.isEmpty(token)){

                //记录在线信息
                Long time= user.getLatestOnlineTime();
                if(chenkSameDay(time)){
                    user.setOnlineDays(user.getOnlineDays()+1);
                }
                user.setLatestOnlineTime(System.currentTimeMillis());
                userDao.save(user);

                LoginUserDTO dto = UserConverter.INSTANCE.entity2loginDTO(user);
                if(user.getUserAvatar()!=null){
                    dto.setUserAvatar(sysParaDao.findParaValueById(ConstantUtil.localURL)+user.getUserAvatar());
                }
                dto.setUserToken(token);
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
            }
        }
        //内部错误
        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.INTERNAL_ERROR);
    }

    /**
     * 和当前时间不在同一天返回 true
     * @param time
     * @return
     */
    private boolean chenkSameDay(Long time) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        if(sdf.format(new Date()).equals(sdf.format(new Date(time)))){
            return false;
        }
        return  true;
    }

    @Override
    public ServerResponse loginUserBySMS(LoginUserByPhoneCmd cmd) {
        boolean result = userDao.existsByUserPhoneAndPhoneRegionNumber(cmd.getUserPhone(),cmd.getPhoneRegionNumber());
        if(!result)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PHONE_NOT_EXIST);
        return verifyService.checkPhone(cmd.getUserPhone(),cmd.getPhoneRegionNumber());
    }

    @Transactional
    @Override
    public ServerResponse loginUserBySMSVerify(LoginUserBySmsVerifyCmd cmd) {
        ResponseEnum responseEnum = verifyService.checkPhoneCode(cmd.getUserPhone(),cmd.getPhoneRegionNumber(), cmd.getMessageContent());
        if(ResponseEnum.SUCCESS== responseEnum){
            UserEntity user = userDao.findByUserPhoneAndPhoneRegionNumber(cmd.getUserPhone(),cmd.getPhoneRegionNumber());
            //用户不存在
            if(user == null)
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ACCOUNT_NOT_FOUND);
            else if(user.getPrivilege() == UserPrivilegeEnum.FREEZE_LOGIN.status){
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ACCOUNT_PRIVILEGE_WRONG);
            }
            String token = loginTokenHelper.createToken(user.getUserId());
            if (!StringUtils.isEmpty(token)){

                //记录在线信息
                Long time= user.getLatestOnlineTime();
                if(chenkSameDay(time)){
                    user.setOnlineDays(user.getOnlineDays()+1);
                }
                user.setLatestOnlineTime(System.currentTimeMillis());
                userDao.save(user);

                LoginUserDTO dto = UserConverter.INSTANCE.entity2loginDTO(user);
                if(user.getUserAvatar()!=null){
                    dto.setUserAvatar(sysParaDao.findParaValueById(ConstantUtil.localURL)+user.getUserAvatar());
                }
                dto.setUserToken(token);
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
            }
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.INTERNAL_ERROR);
        } else{
            return ServerResponse.getInstance().fail().responseEnum(responseEnum);
        }
    }

    @Override
    public ServerResponse checkUserName(String userName) {
        boolean result = userDao.existsByUserName(userName);
        if(result)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.USERNAME_EXIST);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
    }

    @Override
    public ServerResponse checkUserPhone(String userPhone, String phoneRegionNumber) {
        boolean result = userDao.existsByUserPhoneAndPhoneRegionNumber(userPhone,phoneRegionNumber);
        if(result)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PHONE_EXIST);
        return verifyService.checkPhone(userPhone,phoneRegionNumber);
    }

    @Override
    public ServerResponse checkUserPhoneVerify(String userPhone,String phoneRegionNumber, String messageContent) {
        return verifyService.verifyPhone(userPhone,phoneRegionNumber,messageContent);
    }

    @Override
    public ServerResponse updatePasswordByPhone(String userPhone, String newPassword, String phoneRegionNumber, String messageContent) {
        ResponseEnum responseEnum = verifyService.checkPhoneCode(userPhone, phoneRegionNumber,messageContent);
        if(ResponseEnum.SUCCESS != responseEnum)
            return ServerResponse.getInstance().fail().responseEnum(responseEnum);

        UserEntity userEntity = userDao.findByUserPhoneAndPhoneRegionNumber(userPhone,phoneRegionNumber);
        userEntity.setPassword(Md5Crypt.EncoderByMd5(newPassword));
        UserEntity result = userDao.save(userEntity);
        if(result == null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.INTERNAL_ERROR);
        else
            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
    }


    @Override
    public ServerResponse getUserInfo(String userId) {
        UserEntity userEntity = userDao.findByUserId(userId);
        if(userEntity == null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ACCOUNT_NOT_FOUND);

        UserInfoDTO dto = UserConverter.INSTANCE.entity2dto(userEntity);
        if(userEntity.getUserAvatar()!=null){
            dto.setUserAvatar(sysParaDao.findParaValueById(ConstantUtil.localURL)+userEntity.getUserAvatar());
        }
        dto.setRank(dictionaryDao.queryDicNameByCodeAndType(dto.getRank(),"defaultRank"));
        TeamMemberEntity team=teamMemberDao.findByUserId(userId);
        if(team!=null)
            dto.setParentInvitationCode(userDao.findInvittionCodeByUserId(team.getUserId()));

        //钱包信息
        UserWalletDTO walletDTO=new UserWalletDTO();
        JSONObject json= TransactionUtils.findUsdtBalance(userEntity.getWalletAddress(),sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
        if("SUCCESS".equals(json.get("code"))){
            walletDTO.setUsdt(json.getBigDecimal("amount"));
        }else{
            walletDTO.setUsdt(BigDecimal.valueOf(-1));
        }

        JSONObject json1= TransactionUtils.findBetBalance(userEntity.getWalletAddress(),sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
        if("SUCCESS".equals(json1.get("code"))){
            walletDTO.setOwnBet(json1.getBigDecimal("amount"));
        }else{
            walletDTO.setOwnBet(BigDecimal.valueOf(-1));
        }
        walletDTO.setLockingUsdt(quotationDao.findTotalLockingUsdt(userId, OrderStatusEnum.ONGOING.status));
        walletDTO.setWaitCurrencyBet(toReleasedBETRecordDao.findTotalSurplusNumberByUserId(userId));
        dto.setWalletDTO(walletDTO);
        dto.setUnReadCount(sysMsgDao.countSysMsg()-sysMsgHaveReadDao.countByUserId(userId));
        if(StringUtils.isNotBlank(userEntity.getMnemonicWords())){
            dto.setIfMnemonic(1);
        }else{
            dto.setIfMnemonic(0);
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
    }

    @Override
    public ServerResponse updateUserInfo(String userId, UpdateUserInfoCmd cmd) {
        ResponseEnum responseEnum;
        if(StringUtils.isNotBlank(cmd.getUserName())) {
            UserEntity userEntity=userDao.findByUserName(cmd.getUserName());
            if(userEntity!=null&&!userId.equals(userEntity.getUserId()))
             return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.USERNAME_EXIST);
            userDao.updateUserName(userId,cmd.getUserName());
        }
        if(StringUtils.isNotBlank(cmd.getUserAvatar())) userDao.updateUserAvatar(userId,cmd.getUserAvatar());
        UserEntity entity=userDao.findByUserId(userId);
        CommonUserInfoDTO dto=new CommonUserInfoDTO();
        if(entity!=null){
            dto= UserConverter.INSTANCE.entity2commonDTO(entity);
            if(entity.getUserAvatar()!=null){
                dto.setUserAvatar(sysParaDao.findParaValueById(ConstantUtil.localURL)+entity.getUserAvatar());
            }
        }
        //增加到融云的同步更新
        try {
            RongCloud rongCloud =  getRongCloud();
            User rongCloudUser = rongCloud.user;
            UserModel curuser = new UserModel()
                    .setId(userId)
                    .setName(cmd.getUserName());
            if(StringUtils.isNotBlank(cmd.getUserAvatar())){
                curuser.setPortrait(sysParaDao.findParaValueById(ConstantUtil.localURL)+"/"+cmd.getUserAvatar());
                System.out.println("userId="+sysParaDao.findParaValueById(ConstantUtil.localURL)+"/"+cmd.getUserAvatar());
            }
            Result refreshResult = rongCloudUser.update(curuser);
        }
        catch(Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.RONGCLOUDY_ERROR);

        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
    }

    @Override
    public ServerResponse queryBuyUserPage( UserQuery qry) {
        Sort sort = Sort.by("createTime").descending();
        PageRequest pageRequest = PageRequest.of(qry.getPageNum(),qry.getPageSize(),sort);
        Page<UserEntity> userPages   = userDao.findAll(pageRequest);
        List<UserEntity> users       = userPages.getContent();
        List<CommonUserInfoDTO> userDtos    = new ArrayList<>();

        for(UserEntity entity : users){
            CommonUserInfoDTO userDTO = UserConverter.INSTANCE.entity2commonDTO(entity);
            if(entity.getUserAvatar()!=null){
                userDTO.setUserAvatar(sysParaDao.findParaValueById(ConstantUtil.localURL)+entity.getUserAvatar());
            }
            userDtos.add(userDTO);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("users",userDtos);
        jsonObject.put("pageSize",qry.getPageSize());
        jsonObject.put("pageNum",qry.getPageNum());
        jsonObject.put("total",userPages.getTotalElements());
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(jsonObject);
    }


    @Override
    public ServerResponse qryQuestion(String userId, String pageNum, String pageSize, String stadu,String type){
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" select * from user_question as t where true ");
            sqltotal.append(" select count(1) from user_question as t where true ");
            if(StringUtils.isNoneBlank(userId)){
                sqljoint.append(" and  t.user_id='"+userId+"'");
                sqltotal.append(" and  t.user_id='"+userId+"'");
            }
            if(StringUtils.isNoneBlank(stadu)){
                sqljoint.append(" and  t.stadu=  "+stadu);
                sqltotal.append(" and  t.stadu=  "+stadu);
            }
            if(StringUtils.isNoneBlank(type)){
                sqljoint.append(" and  t.type=  "+type);
                sqltotal.append(" and  t.type=  "+type);
            }
            sqljoint.append(" order by t.create_time desc");
            sqljoint.append(" limit "+Integer.parseInt(pageNum)*Integer.parseInt(pageSize)+","+pageSize);
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, UserQuestionEntity.class);
            List<UserQuestionEntity> userquestList=query.getResultList();

            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("userquestList",userquestList);
            resultJson.put("pageSize",pageSize);
            resultJson.put("pageNum",pageNum);
            resultJson.put("total",total);
        }finally {
            em.close();
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse newQuestion(String userId,String question,String type,String title){
        boolean rb=true;
        JSONObject jsonObject = new JSONObject();
        UserEntity userEntity=userDao.findByUserId(userId);
        try {
            UserQuestionEntity userQuestionEntity = UserQuestionEntity.builder()
                    .questionId(TmFunctions.getKeyStr(""))
                    .question(question)
                    .createTime(System.currentTimeMillis())
                    .userName(userEntity.getUserName())
                    .userId(userEntity.getUserId())
                    .type(Integer.parseInt(type))
                    .title(title)
                    .build();
            userQuestionDao.save(userQuestionEntity);
        }
        catch(Exception e){
            e.printStackTrace();
            rb=false;
        }
        jsonObject.put("flg",rb);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(jsonObject);
    }

    @Override
    public ServerResponse replyQuestion(String userId,String questionId,String answer){

        boolean rb=true;
        JSONObject jsonObject = new JSONObject();
        UserEntity userEntity=userDao.findByUserId(userId);
        try {
            //System.out.println("questionId="+questionId);
            UserQuestionEntity userQuestionEntity = userQuestionDao.findByQuestionId(questionId);
            userQuestionEntity.setAnswer(answer);
            userQuestionEntity.setStadu(1);
            userQuestionEntity.setReplyUserId(userEntity.getUserId());
            userQuestionEntity.setReplyUserName(userEntity.getUserName());
            userQuestionEntity.setReplyTime(System.currentTimeMillis());
            userQuestionDao.save(userQuestionEntity);
        }
        catch(Exception e){
            e.printStackTrace();
            rb=false;
        }
        jsonObject.put("flg",rb);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(jsonObject);
    }


    @Override
    public ServerResponse queryUserPageByKeyword(UserKeywordQuery qry) {
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" select * from user as t where  1=1 ");
            sqltotal.append(" select count(1) from user as t where   1=1 ");
            if(StringUtils.isNoneBlank(qry.getKeyword())){
                sqljoint.append(" and concat(t.user_name,',',t.user_phone') like '%"+qry.getKeyword()+"%'  ");
                sqltotal.append(" and concat(t.user_name,',',t.user_phone') like '%"+qry.getKeyword()+"%'  ");
            }
//            sqljoint.append(" order by t.create_time desc");
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, UserEntity.class);
            List<UserEntity> list=query.getResultList();
            List<CommonUserInfoDTO> userDtos    = new ArrayList<>();
            for(UserEntity entity : list){
                CommonUserInfoDTO userDTO = UserConverter.INSTANCE.entity2commonDTO(entity);
                if(entity.getUserAvatar()!=null){
                    userDTO.setUserAvatar(sysParaDao.findParaValueById(ConstantUtil.localURL)+entity.getUserAvatar());
                }
                userDtos.add(userDTO);
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("users",userDtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
        }finally {
            em.close();
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }


    @Transactional
    @Override
    public ServerResponse findRemainNum(String userId) {
        UserEntity userEntity=userDao.findByUserId(userId);
        if(StringUtils.isBlank(userEntity.getWalletAddress()))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.WALLET_NOT_SUCH);
        UserWalletEntity entity=userWalletDao.findByUserId(userId);
        if(entity==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.WALLET_NOT_SUCH);
        UserWalletDTO walletDTO=new UserWalletDTO();
        JSONObject json= TransactionUtils.findUsdtBalance(userEntity.getWalletAddress(),sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
        if("SUCCESS".equals(json.get("code"))){
            walletDTO.setUsdt(json.getBigDecimal("amount"));
        }else{
            walletDTO.setUsdt(BigDecimal.valueOf(-1));
        }

        JSONObject json1= TransactionUtils.findBetBalance(userEntity.getWalletAddress(),sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
        if("SUCCESS".equals(json1.get("code"))){
            walletDTO.setOwnBet(json1.getBigDecimal("amount"));
        }else{
            walletDTO.setOwnBet(BigDecimal.valueOf(-1));
        }

        JSONObject jsontrx= TransactionUtils.findTrxBalance(userEntity.getWalletAddress(),sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
        if("SUCCESS".equals(jsontrx.get("code"))){
            walletDTO.setTrx(jsontrx.getBigDecimal("amount"));
        }else{
            walletDTO.setTrx(BigDecimal.valueOf(-1));
        }


        walletDTO.setLockingUsdt(quotationDao.findTotalLockingUsdt(userId, OrderStatusEnum.ONGOING.status));
        walletDTO.setWaitCurrencyBet(toReleasedBETRecordDao.findTotalSurplusNumberByUserId(userId));
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(walletDTO);
    }


    @Override
    public ServerResponse createWalletAddress(String userId) {
        UserEntity user=userDao.findByUserId(userId);
        if(StringUtils.isNotBlank(user.getWalletAddress()))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ALREADY_EXISTS_DATA);
        try{
            JSONObject json=TransactionUtils.createTronAdr(sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
            if("SUCCESS".equals(json.getString("code"))){
                user.setWalkey(DesUtil.encrypt(json.getString("privateKey")));
                user.setWalletAddress(json.getString("address"));
                userDao.save(user);
            }else{
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(user.getWalletAddress());
    }

    @Transactional
    @Override
    public ServerResponse WithdrawalMoneyBet(String userId, WithdrawalMoneyCmd qry){
        UserEntity user= userDao.findByUserId(userId);
        if(user==null||StringUtils.isBlank(user.getWalkey()))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.WALLET_NOT_SUCH);
        UserWalletEntity wallet=userWalletDao.findByUserId(userId);
        try{
            //余额小于提现金额
            if(wallet.getOwnBet().compareTo(qry.getAmount())==-1)
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.BALANCE_NOT_ENOUGH);
            BigDecimal oldAmount=wallet.getOwnBet();//原始金额
            BigDecimal amount=qry.getAmount();//交易金额
            BigDecimal nowAmount=wallet.getOwnBet().subtract(qry.getAmount());//交易后金额
            wallet.setOwnBet(nowAmount);
            userWalletDao.save(wallet);

            BETWithdrawalRecordEntity bet= BETWithdrawalRecordEntity.builder()
                    .id(TmFunctions.getKeyStr("TZ"))
                    .userId(userId)
                    .amount(amount)
                    .remark(qry.getRemark())
                    .createTime(System.currentTimeMillis())
                    .build();
            tBTWithdrawalRecordDao.save(bet);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ABNORMAL_AMOUNT);
        }

        try{
            JSONObject json=TransactionUtils.outToAddressBET(DesUtil.decrypt(SjustConstant.JST_12),user.getWalletAddress(), String.valueOf(qry.getAmount()),sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
            if("SUCCESS".equals(json.getString("code"))){
                TransMoneyDto dto=TransMoneyDto.builder()
                        .hash(json.getString("hash"))
                        .result(json.getString("result"))
                        .code(json.getString("code"))
                        .message(json.getString("message"))
                        .build();
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
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


    //USTD 直接通过系统转账
    @Transactional
    @Override
    public ServerResponse transMoneyUsdt(String userId, TransMoneyCmd qry){
        UserEntity user= userDao.findByUserId(userId);
        if(user==null||StringUtils.isBlank(user.getWalkey()))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.WALLET_NOT_SUCH);
        UserWalletEntity wallet=userWalletDao.findByUserId(userId);
        try{
            BigDecimal oldAmount=wallet.getOwnBet();//原始金额
            BigDecimal amount=qry.getAmount();//交易金额
            BigDecimal nowAmount=wallet.getOwnBet().subtract(qry.getAmount());//交易后金额

            USDTWithdrawalRecordEntity bet= USDTWithdrawalRecordEntity.builder()
                    .id(TmFunctions.getKeyStr("UZ"))
                    .userId(userId)
                    .oldAmount(oldAmount)
                    .amount(amount)
                    .nowAmount(nowAmount)
                    .remark(qry.getRemark())
                    .createTime(System.currentTimeMillis())
                    .build();
            usdtWithdrawalRecordDao.save(bet);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ABNORMAL_AMOUNT);
        }

        try{
            JSONObject json=TransactionUtils.outToAddressUsdt(DesUtil.decrypt(user.getWalkey()),qry.getToAdd(), String.valueOf(scTxsx(qry.getAmount(),6)),sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
            if("SUCCESS".equals(json.getString("code"))){
                TransMoneyDto dto=TransMoneyDto.builder()
                        .hash(json.getString("hash"))
                        .result(json.getString("result"))
                        .code(json.getString("code"))
                        .message(json.getString("message"))
                        .build();
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
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

    @Override
    public ServerResponse findUserWalletAddress(String userId) {
        String address=userDao.findUseWalletAddressByUserId(userId);
        if(StringUtils.isBlank(address))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.WALLET_NOT_SUCH);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(address);
    }


    //提现扣除手续费
    private BigDecimal scTxsx(BigDecimal amount,int num) throws Exception {
        String val=sysParaDao.findParaValueById(num);
        Float now=100-Float.valueOf(val);
        if(now>100||now<0) throw new Exception();
        return amount.multiply(BigDecimal.valueOf(now*0.01)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public ServerResponse findMybill(String userId, FlowRecordAllQuery qry) {
        String fb=sysParaDao.findParaValueById(1);
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" select id,user_id,amount,pay_type,type,create_time,order_id,buy_type from user_flow as t where  1=1 ");
            sqltotal.append(" select count(1) from user_flow as t where   1=1 ");
            if(StringUtils.isNotBlank(userId)){
                sqljoint.append(" and  t.user_id=  '"+userId+"'");
                sqltotal.append(" and  t.user_id=  '"+userId+"'");
            }

            if(StringUtils.isNotBlank(qry.getType())){
                sqljoint.append(" and  t.type=  '"+qry.getType()+"'");
                sqltotal.append(" and  t.type=  '"+qry.getType()+"'");
            }
            if(StringUtils.isNotBlank(qry.getPayType())){
                sqljoint.append(" and  t.pay_type=  '"+qry.getPayType()+"'");
                sqltotal.append(" and  t.pay_type=  '"+qry.getPayType()+"'");
            }
            if(qry.getStartTime()!=null){
                sqljoint.append(" and  t.create_time>=  "+qry.getStartTime()+")");
                sqltotal.append(" and  t.create_time>=  "+qry.getStartTime()+")");
            }
            if(qry.getEndTime()!=null){
                sqljoint.append(" and  t.create_time<=  "+qry.getEndTime()+")");
                sqltotal.append(" and  t.create_time<=  "+qry.getEndTime()+")");
            }
            sqljoint.append(" order by t.create_time desc");
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql);
            List lst=query.getResultList();
            List<FlowRecordDTO> dtos    = new ArrayList<>();
            for(Object row : lst){
                Object[] cells= (Object[]) row;
                FlowRecordDTO dto=new FlowRecordDTO();
                dto.setId(String.valueOf(cells[0]));
                dto.setUserId(String.valueOf(cells[1]));
                int payType=Integer.parseInt(String.valueOf(cells[3]));
                String dw="";
                if(payType==0)  dw=fb;
                if(payType==1)  dw="USDT";
                if(payType==2)  dw="BET";
                dto.setAmount(String.valueOf(cells[2])+dw);
                dto.setPayType(payType);
                dto.setType(String.valueOf(cells[4]));
                dto.setCreateTime(Long.valueOf(String.valueOf(cells[5])));
                dto.setOrderId(cells[6]==null?null:String.valueOf(cells[6]));
                dto.setBuyType(cells[7]==null?null:String.valueOf(cells[7]));
                dtos.add(dto);
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("flows",dtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
        }finally {
            em.close();
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse  createRongyunToken() {
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            StringBuilder sqljoint = new StringBuilder();
            sqljoint.append(" select * from user where ry_token is null ");
            String sql = sqljoint.toString();
            Query query = em.createNativeQuery(sql, UserEntity.class);
            List<UserEntity> lst = query.getResultList();
            RongCloud rongCloud =getRongCloud();
            User rongCloudUser = rongCloud.user;
            for (int i = 0; i < lst.size(); i++) {
                UserModel curuser = new UserModel()
                        .setId(lst.get(i).getUserId())
                        .setName(lst.get(i).getUserName());
                if (StringUtils.isNotBlank(lst.get(i).getUserAvatar())) {
                    curuser.setPortrait(sysParaDao.findParaValueById(ConstantUtil.localURL)+ "/" + lst.get(i).getUserAvatar());
                }
                TokenResult Result = rongCloudUser.register(curuser);
                if(Result!=null&&Result.getToken()!=null) {
                    lst.get(i).setRyToken(Result.getToken());
                    userDao.save(lst.get(i));

                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.RONGCLOUDY_ERROR);

        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
    }

    private RongCloud getRongCloud() {
        List<String> list=sysParaDao.findParaValueByIdIn("17,18,19");
        return  RongCloud.getInstance(list.get(0), list.get(1), list.get(2));
    }

    //收益中心
    @Override
    public ServerResponse findRevenueCenter(String userId) {

        UserEntity user=userDao.findByUserId(userId);
        if(StringUtils.isBlank(user.getWalletAddress()))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.WALLET_NOT_SUCH);
        UserWalletEntity entity=userWalletDao.findByUserId(userId);
        if(entity==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.WALLET_NOT_SUCH);
//        entity.setUsdt(new BigDecimal("1245"));
//        userWalletDao.save(entity);
        UserWalletDTO walletDto=new UserWalletDTO();
        try{
            walletDto.setOwnBet( entity.getOwnBet() );
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ABNORMAL_AMOUNT);
        }

//        UserWalletDTO walletDto= UserWalletConverter.INSTANCE.entity2walletDto(entity);
        JSONObject json= TransactionUtils.findUsdtBalance(user.getWalletAddress(),sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
        if("SUCCESS".equals(json.get("code"))){
            walletDto.setUsdt(json.getBigDecimal("amount"));
        }else{
            walletDto.setUsdt(BigDecimal.valueOf(-1));
        }
//        walletDto.setWaitCurrencyBet(user.getToReleasedMoviesBet().add(user.getToReleasedShopBet()));
        walletDto.setWaitCurrencyBet(toReleasedBETRecordDao.findTotalSurplusNumberByUserId(userId));


        RevenueCenterDto dto=new RevenueCenterDto();
        dto.setTotalFB(new BigDecimal(0));
        dto.setTotalUSDT(new BigDecimal(0));
        dto.setTotalBET(new BigDecimal(0));
        List<List<Object>> list= revenueRecordDao.findTotalGroupByType(userId);
        for(List<Object> lt:list){
            int payType=TmFunctions.changeInt(lt.get(0));
            BigDecimal val=TmFunctions.changeBigDecimal(lt.get(1));
            if(PayTypeEnum.PAY_FB.type==payType){
                dto.setTotalFB(val);
            }else if(PayTypeEnum.PAY_USDT.type==payType){
                dto.setTotalUSDT(val);
            }else if(PayTypeEnum.PAY_BET.type==payType){
                dto.setTotalBET(val);
            }
        }
        dto.setYesterdayProfit(revenueRecordDao.findTotalByDateAndType(userId,DateUtils.getBeginDayOfYesterday(),
                DateUtils.getEndDayOfYesterDay(),PayTypeEnum.PAY_FB.type));

        dto.setWeekProfit(revenueRecordDao.findTotalByDateAndType(userId,DateUtils.getBeginDayOfWeek(),
                DateUtils.getEndDayOfWeek(),PayTypeEnum.PAY_FB.type));

        dto.setMonthProfit(revenueRecordDao.findTotalByDateAndType(userId,DateUtils.getBeginDayOfMonth(),
                DateUtils.getEndDayOfMonth(),PayTypeEnum.PAY_FB.type));

        dto.setLastMonth(revenueRecordDao.findTotalByDateAndType(userId,DateUtils.getBeginDayOfLastMonth(),
                DateUtils.getEndDayOfLastMonth(),PayTypeEnum.PAY_FB.type));

        MyRevenueCenterQuery qry=new MyRevenueCenterQuery();
        qry.setPageNum(0);
        qry.setPageSize(10);
        qry.setType(0);
        ServerResponse res= findMyRevenueCenter(userId,qry);
        JSONObject resultJson = (JSONObject) res.getData();
        resultJson.put("revenueCenterDto",dto);
        resultJson.put("walletDto",walletDto);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse findMyRevenueCenter(String userId, MyRevenueCenterQuery qry) {
        String fb=sysParaDao.findParaValueById(1);
        Date start=new Date();
        Date end=new Date();
        switch (qry.getType()){
            case 0:start=DateUtils.getBeginDayOfYesterday(); end=DateUtils.getEndDayOfYesterDay();break;//昨日
            case 1:start=DateUtils.getBeginDayOfWeek(); end=DateUtils.getEndDayOfWeek();break;//本周
            case 2:start=DateUtils.getBeginDayOfMonth(); end=DateUtils.getEndDayOfMonth();break;//本月
            case 3:start=DateUtils.getBeginDayOfLastMonth(); end=DateUtils.getEndDayOfLastMonth();break;//上月
            case 4:start=DateUtils.getBeginDayOfYear(); end=DateUtils.getEndDayOfYear();break;//本年
            default:;
        }

        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" select * from revenue_record as t where  1=1 ");
            sqltotal.append(" select count(1) from revenue_record as t where   1=1 ");
            if(StringUtils.isNotBlank(userId)){
                sqljoint.append(" and  t.user_id=  '"+userId+"'");
                sqltotal.append(" and  t.user_id=  '"+userId+"'");
            }
            if(start!=null){
                sqljoint.append(" and  t.create_time>=  "+start.getTime()+")");
                sqltotal.append(" and  t.create_time>=  "+start.getTime()+")");
            }
            if(end!=null){
                sqljoint.append(" and  t.create_time<=  "+end.getTime()+")");
                sqltotal.append(" and  t.create_time<=  "+end.getTime()+")");
            }
            sqljoint.append(" order by t.create_time desc");
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql,RevenueRecordEntity.class);
            List<RevenueRecordEntity> lst=query.getResultList();
            List<RevenueRecordDTO> dtos    = new ArrayList<>();
            for(RevenueRecordEntity entity : lst){
                RevenueRecordDTO  dto= RevenueRecordConverter.INSTANCE.entity2dto(entity);
                int payType=dto.getPayType();
                String dw="";
                if(payType==0)  dw=fb;
                if(payType==1)  dw="USDT";
                if(payType==2)  dw="BET";
                dto.setAmountDw(dto.getAmount()+dw);
                dto.setRevenueType(dictionaryDao.findDescribeByDicTypeAndCode("revenueType",dto.getRevenueType()).getDicDescribe());
                dtos.add(dto);
            }
            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("flows",dtos);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
        }finally {
            em.close();
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse checkMoneyPassword(String userId, String moneyPassword) {
        UserEntity user = userDao.findByUserId(userId);
        if(user==null||StringUtils.isBlank(user.getMoneyPassword()))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ADDRESS_NOT_EXIST);
        if(!user.getMoneyPassword().equals(Md5Crypt.EncoderByMd5(moneyPassword)))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PASSWORD_WRONG);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
    }

    @Override
    public ServerResponse updateMoneyPassword(String userId, String moneyPassword) {
        UserEntity user = userDao.findByUserId(userId);
        if(user==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ADDRESS_NOT_EXIST);
        user.setMoneyPassword(Md5Crypt.EncoderByMd5(moneyPassword));
        userDao.save(user);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
    }

    @Override
    public ServerResponse checkMoneyPwd(String userId){
        UserEntity user = userDao.findByUserId(userId);
        JSONObject resultJson = new JSONObject();
        if(StringUtils.isNotBlank(user.getMoneyPassword())){
            resultJson.put("ifexist","1");
            resultJson.put("message","资金密码已经存证");
            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
        }
        else{
            resultJson.put("ifexist","0");
            resultJson.put("message","资金密码尚未设置");
            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
        }
    }

    @Override
    public ServerResponse Advertisement() {
        List<Object[]> tmp= advertisementDao.findAdOnTop();
        List<AdvertisementDTO> dtos=new ArrayList<>();
        for(Object[] arr:tmp){
            AdvertisementDTO dto=AdvertisementDTO.builder()
                    .typeFlg(Integer.valueOf(String.valueOf(arr[0])))
                    .coverImage(arr[1]==null?null:sysParaDao.findParaValueById(14)+arr[1])
                    .linkUrl(String.valueOf(arr[2]))
                    .relId(String.valueOf(arr[3]))
                    .build();
            dtos.add(dto);
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dtos);
    }

    @Override
    public ServerResponse qryNameByIP(String userId, String ip, String lang) {
        JSONObject jsonObject = new JSONObject();
        String countryName=null;
        UserEntity userEntity = userDao.findByUserId(userId);
        if(userEntity == null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ACCOUNT_NOT_FOUND);
        Long l=IPUtil.ipToLong(ip);
        SysIpCountryEntity opE=sysIpCountryDao.findFirstByIpLong(l);
        if(opE!=null){
            if(IPLangEnum.en_US.status.equals(lang)){
                countryName=opE.getCountryEn();
            }else if(IPLangEnum.zh_TW.status.equals(lang)){
                countryName=opE.getCountryAbbr();
            }else if(IPLangEnum.zh_CN.status.equals(lang)){
                countryName=opE.getCountryCn();
            }
            userEntity.setCountryName(countryName);
            userDao.save(userEntity);
        }
        jsonObject.put("countryName",countryName);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(jsonObject);
    }

    @Override
    public ServerResponse test(String address) {
        UserWalletDTO walletDTO=new UserWalletDTO();
        JSONObject json= TransactionUtils.findUsdtBalance(address,sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
        if("SUCCESS".equals(json.get("code"))){
            walletDTO.setUsdt(json.getBigDecimal("amount"));
        }else{
            walletDTO.setUsdt(BigDecimal.valueOf(-1));
        }

        JSONObject json1= TransactionUtils.findBetBalance(address,sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
        if("SUCCESS".equals(json1.get("code"))){
            walletDTO.setOwnBet(json1.getBigDecimal("amount"));
        }else{
            walletDTO.setOwnBet(BigDecimal.valueOf(-1));
        }

        JSONObject jsontrx= TransactionUtils.findTrxBalance(address,sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
        if("SUCCESS".equals(jsontrx.get("code"))){
            walletDTO.setTrx(jsontrx.getBigDecimal("amount"));
        }else{
            walletDTO.setTrx(BigDecimal.valueOf(-1));
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(walletDTO);
    }

}

