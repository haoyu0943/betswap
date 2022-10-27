package com.betswap.market.app.user.service;


import com.betswap.market.client.flow.vo.query.FlowRecordAllQuery;
import com.betswap.market.client.flow.vo.query.MyRevenueCenterQuery;
import com.betswap.market.client.user.vo.cmd.*;
import com.betswap.market.client.user.vo.login.LoginUserByPasswordCmd;
import com.betswap.market.client.user.vo.login.LoginUserByPhoneCmd;
import com.betswap.market.client.user.vo.login.LoginUserBySmsVerifyCmd;
import com.betswap.market.client.user.vo.quey.*;
import com.betswap.market.client.user.vo.register.RegisterUserCmd;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.client.user.vo.UpdateUserInfoCmd;

public interface UserService {
    ServerResponse checkUserName(String userName);
    ServerResponse checkUserPhone(String userPhone, String phoneRegionNumber);
    ServerResponse checkUserPhoneVerify(String userPhone,String phoneRegionNumber,String messageContent);
    ServerResponse registerByPassword(RegisterUserCmd cmd);//保存、添加用户

    ServerResponse loginUserByPassword(LoginUserByPasswordCmd cmd);//登陆
    ServerResponse loginUserBySMS(LoginUserByPhoneCmd cmd);
    ServerResponse loginUserBySMSVerify(LoginUserBySmsVerifyCmd cmd);

    ServerResponse getUserInfo(String userId);
    ServerResponse updateUserInfo(String userId, UpdateUserInfoCmd cmd);

    ServerResponse updatePasswordByPhone(String userPhone, String newPassword, String phoneRegionNumber, String messageContent);


    ServerResponse queryBuyUserPage(UserQuery qry);

    ServerResponse qryQuestion(String userId, String pageNum, String pageSize, String stadu,String type);
    ServerResponse newQuestion(String userId,String question,String type,String title);
    ServerResponse replyQuestion(String userId,String questionId,String answer);

    ServerResponse queryUserPageByKeyword(UserKeywordQuery qry);

    ServerResponse findRemainNum(String userId);

    ServerResponse createWalletAddress(String userId);

    ServerResponse WithdrawalMoneyBet(String userId, WithdrawalMoneyCmd qry) ;

    ServerResponse transMoneyUsdt(String userId, TransMoneyCmd qry) ;

    ServerResponse findUserWalletAddress(String userId);


    ServerResponse findMybill(String userId, FlowRecordAllQuery qry);

    ServerResponse createRongyunToken();


    ServerResponse findRevenueCenter(String userId);

    ServerResponse findMyRevenueCenter(String userId, MyRevenueCenterQuery qry);

    ServerResponse checkMoneyPassword(String userId, String moneyPassword);

    ServerResponse updateMoneyPassword(String userId, String moneyPassword);

    ServerResponse checkMoneyPwd(String userId);

    ServerResponse Advertisement();

    ServerResponse qryNameByIP(String userId, String ip, String langN);

    ServerResponse test(String address);
}

