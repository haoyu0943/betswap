package com.betswap.market.app.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.betswap.market.app.user.service.WalletService;
import com.betswap.market.client.common.YesOrNoStatusEnum;
import com.betswap.market.client.communicate.MessageReadTypeEnum;
import com.betswap.market.client.communicate.MessageTypeEnum;
import com.betswap.market.client.flow.UserRankTypeEnum;
import com.betswap.market.client.quotation.LeagueMatchEnum;
import com.betswap.market.client.quotation.WithdrawalTypeEnum;
import com.betswap.market.client.quotation.dto.WithdrawalTypeDTO;
import com.betswap.market.client.user.enums.UserPrivilegeEnum;
import com.betswap.market.client.user.enums.UserRankStatusEnum;
import com.betswap.market.client.wallet.vo.cmd.RegisterWalleAddresstCmd;
import com.betswap.market.client.wallet.vo.cmd.RegisterWalletCmd;
import com.betswap.market.client.wallet.vo.cmd.RegisterWalletKeyCmd;
import com.betswap.market.client.wallet.vo.cmd.WithdrawalWalletCmd;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.communicate.dao.MyMsgDao;
import com.betswap.market.infrastruture.communicate.entity.MyMsgEntity;
import com.betswap.market.infrastruture.quotation.dao.LeagueMatchDao;
import com.betswap.market.infrastruture.quotation.dao.MatchAddressDao;
import com.betswap.market.infrastruture.quotation.dao.MatchDao;
import com.betswap.market.infrastruture.quotation.entity.LeagueMatchEntity;
import com.betswap.market.infrastruture.quotation.entity.MatchAddressEntity;
import com.betswap.market.infrastruture.quotation.entity.MatchEntity;
import com.betswap.market.infrastruture.system.dao.RoleBETDao;
import com.betswap.market.infrastruture.system.dao.SysAddressDao;
import com.betswap.market.infrastruture.system.dao.SysAddressTransferRecordDao;
import com.betswap.market.infrastruture.system.dao.SysParaDao;
import com.betswap.market.infrastruture.system.entity.RoleBETEntity;
import com.betswap.market.infrastruture.system.entity.SysAddressEntity;
import com.betswap.market.infrastruture.system.entity.SysAddressTransferRecordEntity;
import com.betswap.market.infrastruture.team.dao.TeamMemberDao;
import com.betswap.market.infrastruture.team.entity.TeamMemberEntity;
import com.betswap.market.infrastruture.user.dao.UserDao;
import com.betswap.market.infrastruture.user.dao.UserRankRecordDao;
import com.betswap.market.infrastruture.user.dao.UserWalletDao;
import com.betswap.market.infrastruture.user.entity.UserEntity;
import com.betswap.market.infrastruture.user.entity.UserRankRecordEntity;
import com.betswap.market.infrastruture.user.entity.UserWalletEntity;
import com.betswap.market.infrastruture.utils.Md5.DesUtil;
import com.betswap.market.infrastruture.utils.Md5.Md5Crypt;
import com.betswap.market.infrastruture.utils.constant.ConstantUtil;
import com.betswap.market.infrastruture.utils.constant.PasswordUtils;
import com.betswap.market.infrastruture.utils.constant.TmDateUtil;
import com.betswap.market.infrastruture.utils.constant.TmFunctions;
import com.betswap.market.infrastruture.utils.helper.idgenerator.IDGenerator;
import com.betswap.market.infrastruture.utils.helper.token.LoginTokenHelper;
import com.betswap.market.infrastruture.utils.language.LanguageUtils;
import com.betswap.market.infrastruture.utils.transaction.TransactionUtils;
import io.rong.RongCloud;
import io.rong.methods.user.User;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    @Autowired
    private SysParaDao sysParaDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MyMsgDao myMsgDao;

    @Autowired
    private TeamMemberDao teamMemberDao;

    @Autowired
    private RoleBETDao roleBETDao;

    @Autowired
    private LoginTokenHelper loginTokenHelper;

    @Autowired
    private UserRankRecordDao userRankRecordDao;

    @Autowired
    private UserWalletDao userWalletDao;

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private MatchAddressDao matchAddressDao;

    @Autowired
    private SysAddressDao sysAddressDao;
    @Autowired
    private SysAddressTransferRecordDao sysAddressTransferRecordDao;

    @Override
    public ServerResponse generateMnemonicWords() {
        try{
            JSONObject json= TransactionUtils.generateMnemonicWords(sysParaDao.findParaValueById(ConstantUtil.tronZJCUrlVal));
            if("SUCCESS".equals(json.getString("code"))){
                JSONObject returnObject = new JSONObject();
                returnObject.put("mnemonicWords",json.getString("mnemonicWords"));
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(returnObject);
            }else{
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
    }

    @Override
    public ServerResponse generateTrxPathAndMnemonic(RegisterWalletCmd cmd) {
        try{
            JSONObject json= TransactionUtils.generateTrxByMnemonic(sysParaDao.findParaValueById(ConstantUtil.tronZJCUrlVal), cmd.getMnemonic());
            if("SUCCESS".equals(json.getString("code"))){
                JSONObject returnObject = new JSONObject();
                UserEntity user =userDao.findFirstByWalletAddress(json.getString("address"));
                String userId=null;
                if(user==null){//新用户
                    user=new UserEntity();
                    userId = IDGenerator.UserIDGenerate();
                    //创建用户
                    user.setUserId(userId);
                    user.setTotalBETRevenue(new BigDecimal(0));
                    user.setTotalUSDTRevenue(new BigDecimal(0));
                    user.setPrivilege(UserPrivilegeEnum.NORMAL.status);
                    user.setMoneyPassword(Md5Crypt.EncoderByMd5(cmd.getPassword()));
                    user.setPasswordPrompt(cmd.getPasswordPrompt());
                    user.setUserName(cmd.getWalletName());
                    user.setCreateTime( System.currentTimeMillis());
                    user.setRank(UserRankStatusEnum.ORDINARY.status);
                    String code=PasswordUtils.randomPW(7);
                    user.setInvitationCode("M"+code);//邀请码
                    user.setLatestOnlineTime(System.currentTimeMillis());
                    user.setOnlineDays(1);
                    //钱包信息
                    user.setMnemonicWords(DesUtil.encrypt(cmd.getMnemonic()));
                    user.setWalkey(DesUtil.encrypt(checkOx(json.getString("privateKey"))));
                    user.setWalletAddress(json.getString("address"));
                    user.setPublicKey(json.getString("publicKey"));
                    userDao.save(user);

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

                        checkAndUpdateUserRank(parent, UserRankTypeEnum.RANK_DEFAULT.type,userId);
                    }


                    //获取融云账户
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


                }else{//已有用户
                    user.setMoneyPassword(Md5Crypt.EncoderByMd5(cmd.getPassword()));
                    user.setPasswordPrompt(cmd.getPasswordPrompt());
                    user.setUserName(cmd.getWalletName());
                    returnObject.put("rongCloudToken",user.getRyToken());
                }
                UserEntity result = userDao.save(user);
                if(result == null)
                    return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);

                returnObject.put("userId",user.getUserId());
                String token = loginTokenHelper.createToken(user.getUserId());
                returnObject.put("userToken",token);
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(returnObject);
            }else{
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
    }

    //私钥包含Ox 的问题
    private String checkOx(String privateKey) {
        if(privateKey.length()>=2){
            String str=privateKey.substring(0,2);
            if("0x".equals(str)){
                return privateKey.substring(2,privateKey.length());
            }
        }
        return privateKey;
    }

    @Override
    public ServerResponse importByPrivateKey(RegisterWalletKeyCmd cmd) {
        try{
            JSONObject json= TransactionUtils.getAddressByPrivateKey(sysParaDao.findParaValueById(ConstantUtil.tronZJCUrlVal), cmd.getPrivateKey());
            if("SUCCESS".equals(json.getString("code"))){
                JSONObject returnObject = new JSONObject();
                UserEntity user =userDao.findFirstByWalletAddress(json.getString("address"));
                String userId=null;
                if(user==null){//新用户
                    user=new UserEntity();
                    userId = IDGenerator.UserIDGenerate();
                    //创建用户
                    user.setUserId(userId);
                    user.setTotalBETRevenue(new BigDecimal(0));
                    user.setTotalUSDTRevenue(new BigDecimal(0));
                    user.setPrivilege(UserPrivilegeEnum.NORMAL.status);
                    user.setMoneyPassword(Md5Crypt.EncoderByMd5(cmd.getPassword()));
                    user.setPasswordPrompt(cmd.getPasswordPrompt());
                    user.setUserName(cmd.getWalletName());
                    user.setCreateTime( System.currentTimeMillis());
                    user.setRank(UserRankStatusEnum.ORDINARY.status);
                    String code=PasswordUtils.randomPW(7);
                    user.setInvitationCode("M"+code);//邀请码
                    user.setLatestOnlineTime(System.currentTimeMillis());
                    user.setOnlineDays(1);
                    //钱包信息
                    user.setWalkey(DesUtil.encrypt(checkOx(cmd.getPrivateKey())));
                    user.setWalletAddress(json.getString("address"));
                    user.setPublicKey(json.getString("publicKey"));
                    userDao.save(user);


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

                        checkAndUpdateUserRank(parent, UserRankTypeEnum.RANK_DEFAULT.type,userId);
                    }


                    //获取融云账户
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

                }else{//已有用户
                    user.setMoneyPassword(Md5Crypt.EncoderByMd5(cmd.getPassword()));
                    user.setPasswordPrompt(cmd.getPasswordPrompt());
                    user.setUserName(cmd.getWalletName());
                    returnObject.put("rongCloudToken",user.getRyToken());
                }
                UserEntity result = userDao.save(user);
                if(result == null)
                    return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);

                returnObject.put("userId",user.getUserId());
                String token = loginTokenHelper.createToken(user.getUserId());
                returnObject.put("userToken",token);
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(returnObject);
            }else{
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
    }

    @Override
    public ServerResponse exportPrivateKey(String userId, String moneyPassword) {
        UserEntity user = userDao.findByUserId(userId);
        if(user==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.DATA_NOT_EXIST);
        if(!user.getMoneyPassword().equals(Md5Crypt.EncoderByMd5(moneyPassword)))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PASSWORD_WRONG);
        JSONObject resultJson = new JSONObject();
        try {
            resultJson.put("privateKey",DesUtil.decrypt(user.getWalkey()));
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse exportMnemonicWords(String userId, String moneyPassword) {
        UserEntity user = userDao.findByUserId(userId);
        if(user==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.DATA_NOT_EXIST);
        if(!user.getMoneyPassword().equals(Md5Crypt.EncoderByMd5(moneyPassword)))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PASSWORD_WRONG);
        JSONObject resultJson = new JSONObject();
        if(user.getMnemonicWords()==null){
            resultJson.put("mnemonicWords",null);
        }else{
            try {
                resultJson.put("mnemonicWords",DesUtil.decrypt(user.getMnemonicWords()));
            } catch (Exception e) {
                e.printStackTrace();
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }

        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse importByAddress(RegisterWalleAddresstCmd cmd) {
        try{
            JSONObject returnObject = new JSONObject();
            UserEntity user =userDao.findFirstByWalletAddress(cmd.getAddress());
            String userId=null;
            if(user==null){//新用户
                user=new UserEntity();
                userId = IDGenerator.UserIDGenerate();
                //创建用户
                user.setUserId(userId);
                user.setTotalBETRevenue(new BigDecimal(0));
                user.setTotalUSDTRevenue(new BigDecimal(0));
                user.setPrivilege(UserPrivilegeEnum.NORMAL.status);
//                user.setMoneyPassword(Md5Crypt.EncoderByMd5(cmd.getPassword()));
//                user.setPasswordPrompt(cmd.getPasswordPrompt());
//                user.setUserName(cmd.getWalletName());
                user.setCreateTime( System.currentTimeMillis());
                user.setRank(UserRankStatusEnum.ORDINARY.status);
                String code=PasswordUtils.randomPW(7);
                user.setInvitationCode("M"+code);//邀请码
                user.setLatestOnlineTime(System.currentTimeMillis());
                user.setOnlineDays(1);
                //钱包信息
//                user.setWalkey(DesUtil.encrypt(cmd.getPrivateKey()));
                user.setWalletAddress(cmd.getAddress());
//                user.setPublicKey(json.getString("publicKey"));
                userDao.save(user);


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

                    checkAndUpdateUserRank(parent, UserRankTypeEnum.RANK_DEFAULT.type,userId);
                }


                //获取融云账户
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

            }else{//已有用户
//                user.setMoneyPassword(Md5Crypt.EncoderByMd5(cmd.getPassword()));
//                user.setPasswordPrompt(cmd.getPasswordPrompt());
//                user.setUserName(cmd.getWalletName());
                returnObject.put("rongCloudToken",user.getRyToken());
            }
            UserEntity result = userDao.save(user);
            if(result == null)
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);

            returnObject.put("userId",user.getUserId());
            String token = loginTokenHelper.createToken(user.getUserId());
            returnObject.put("userToken",token);
            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(returnObject);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
    }

    @Transactional
    @Override
    public ServerResponse findAddressById(String matchId) {
        String tronUrl=sysParaDao.findParaValueById(ConstantUtil.tronUrlVal);//钱包服务地址
        //判断并创建比赛账户--并转账TRX
        MatchEntity match=matchDao.findFirstById(matchId);
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
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(matchAddress.getWalletAddress());


//        LeagueMatchEntity entity=leagueMatchDao.findFirstById(eagueMatchId);
//        if(entity==null)
//            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
//        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(entity.getWalletAddress());
    }

    @Override
    public ServerResponse withdrawal(String userId, WithdrawalWalletCmd cmd) {
       UserEntity user= userDao.findByUserId(userId);
       if(user==null||StringUtils.isBlank(user.getMoneyPassword()))
           return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
        if(!user.getMoneyPassword().equals(Md5Crypt.EncoderByMd5(cmd.getMoneyPassword())))
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PASSWORD_WRONG);
        JSONObject json=new JSONObject();
        if(cmd.getType()==WithdrawalTypeEnum.WITHDRAWAL_USDT.type){
            try {
                json= TransactionUtils.outToAddressUsdt(DesUtil.decrypt(user.getWalkey()),cmd.getToAddress(),TmFunctions.changeString(cmd.getAmount()),sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
            } catch (Exception e) {
                e.printStackTrace();
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }else if(cmd.getType()==WithdrawalTypeEnum.WITHDRAWAL_TRX.type){
            try {
                json= TransactionUtils.outToAddressTRX(DesUtil.decrypt(user.getWalkey()),cmd.getToAddress(),TmFunctions.changeString(cmd.getAmount()),sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
            } catch (Exception e) {
                e.printStackTrace();
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }else if(cmd.getType()==WithdrawalTypeEnum.WITHDRAWAL_BET.type){
            try {
                json= TransactionUtils.outToAddressBET(DesUtil.decrypt(user.getWalkey()),cmd.getToAddress(),TmFunctions.changeString(cmd.getAmount()),sysParaDao.findParaValueById(ConstantUtil.tronUrlVal));
            } catch (Exception e) {
                e.printStackTrace();
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }
        if("SUCCESS".equals(json.get("code"))){
            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
        }
        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
    }

    @Override
    public ServerResponse findWithdrawalType() {
        List<WithdrawalTypeDTO>lt=new ArrayList<>();
        for(WithdrawalTypeEnum key: WithdrawalTypeEnum.values()){
            WithdrawalTypeDTO dto=new WithdrawalTypeDTO();
            dto.setType(key.type);
            dto.setName(key.description);
            lt.add(dto);
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(lt);
    }


    //检查并更新用户等级
    private void checkAndUpdateUserRank(UserEntity parentUser,Integer rankType, String subordinateId) {
        int count=teamMemberDao.countByParentId(parentUser.getUserId());
        RoleBETEntity rankSet= roleBETDao.findFirstBySubordinateCount(count);
        BigDecimal totalUsdt= teamMemberDao.findTotalUsdt(parentUser.getUserId());
        if(rankSet!=null&&totalUsdt.compareTo(new BigDecimal(rankSet.getThresholdIncome()))==1){
            int userRank= TmFunctions.changeInt(rankSet.getAgentRank());
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

    private RongCloud getRongCloud() {
        List<String> list=sysParaDao.findParaValueByIdIn("17,18,19");
        return  RongCloud.getInstance(list.get(0), list.get(1), list.get(2));
    }

}

