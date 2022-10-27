package com.betswap.market.adapter.user;


import com.betswap.market.app.user.service.UserService;
import com.betswap.market.client.user.vo.CurrentUserVO;
import com.betswap.market.client.user.vo.UpdateUserInfoCmd;
import com.betswap.market.client.user.vo.login.LoginUserByPasswordCmd;
import com.betswap.market.client.user.vo.login.LoginUserByPhoneCmd;
import com.betswap.market.client.user.vo.login.LoginUserBySmsVerifyCmd;
import com.betswap.market.client.user.vo.register.RegisterUserCmd;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.config.annotation.CurrentUser;
import com.betswap.market.infrastruture.config.annotation.DisableToken;
import com.betswap.market.infrastruture.utils.helper.file.PictureHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Random;


@RestController
@RequestMapping("/user")
@Api(value = "UserController", tags = { "/user 用户API" })
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PictureHelper pictureHelper;



    @ApiOperation(value = "检查手机号是否已存在，并发送验证码--判断手机号不存在后发送验证码")
    @DisableToken
    @PostMapping(value = "/check/checkPhone")
    public ServerResponse checkUserPhone(@RequestParam String userPhone,@RequestParam String phoneRegionNumber){
        return userService.checkUserPhone(userPhone,phoneRegionNumber);
    }

    /**
     * /register
     */

    @Value("${user.default.avatar}")
    private String defaultAvatarURL;

    @ApiOperation(value = "通过用户名密码注册")
    @DisableToken
    @PostMapping(value = "/register/ByPassword")
    public ServerResponse registerByPassword(@Valid RegisterUserCmd cmd,
                                             @ApiParam(value = "avatar")
                                             @RequestParam(value = "avatar",required = false) MultipartFile avatar){
        if(avatar != null){
            String coverURL = pictureHelper.savePicture(avatar,"cover", String.valueOf(new Random().nextInt(10000)));
            cmd.setUserAvatar(coverURL);
        }else{
            cmd.setUserAvatar(defaultAvatarURL);
        }
        return userService.registerByPassword(cmd);
    }

    @ApiOperation(value = "通过用户名+密码登录")
    @DisableToken
    @PostMapping(value = "/login/ByUserName")
    public ServerResponse loginUserByPassword(@Valid LoginUserByPasswordCmd cmd){
        return userService.loginUserByPassword(cmd);
    }

    /**
     * 和上一个的区别是 对手机号的判断，一个是有手机号，一个是没有手机号
     * /login
     */
    @ApiOperation(value = "获取手机验证码--手机号登录--判断手机号存在后发送验证码")
    @DisableToken
    @PostMapping(value = "/login/BySms")
    public ServerResponse loginUserBySMS(@Valid LoginUserByPhoneCmd cmd){
        return userService.loginUserBySMS(cmd);
    }


    @ApiOperation(value = "手机号+验证码登录")
    @DisableToken
    @PostMapping(value = "/login/verifySms")
    public ServerResponse loginUserBySmsVerify(@Valid LoginUserBySmsVerifyCmd cmd){
        return userService.loginUserBySMSVerify(cmd);
    }



    @ApiOperation(value = "手机号+验证码 重置密码")
    @DisableToken
    @PostMapping(value = "/find/updatePasswordByPhone")
    public ServerResponse updatePasswordByPhone(@RequestParam String userPhone,@RequestParam String password,@RequestParam String phoneRegionNumber,@RequestParam String messageContent){
        return userService.updatePasswordByPhone(userPhone,password,phoneRegionNumber,messageContent);
    }


    @ApiOperation(value = "修改用户信息")
    @PostMapping(value = "/info/updateUserInfo")
    public ServerResponse updateUserInfo(@CurrentUser CurrentUserVO currentUser, @Valid UpdateUserInfoCmd cmd,
                                         @ApiParam(value = "avatar")
                                         @RequestParam(value = "avatar",required = false) MultipartFile avatar){
        if(avatar != null){
            String coverURL = pictureHelper.savePicture(avatar,"cover", String.valueOf(new Random().nextInt(10000)));
            cmd.setUserAvatar(coverURL);
        }
        return userService.updateUserInfo(currentUser.getUserId(),cmd);
    }


    @ApiOperation(value = "为没有融云token的账号补充融云token")
    @DisableToken
    @PostMapping(value = "/createRongyunToken")
    public ServerResponse createRongyunToken(){
        return userService.createRongyunToken();
    }


    /**
     * /info
     */
    @ApiOperation(value = "查询用户详情")
    @PostMapping(value = "/userDetail")
    public ServerResponse getUserInfo(@CurrentUser CurrentUserVO currentUser){
        return userService.getUserInfo(currentUser.getUserId());
    }


    /**
     * /info
     */
    @ApiOperation(value = "根据IP查询所在地")
    @PostMapping(value = "/qryNameByIP")
    public ServerResponse qryNameByIP(@CurrentUser CurrentUserVO currentUser,@RequestParam String ip,@RequestParam String langN){
        return userService.qryNameByIP(currentUser.getUserId(),ip,langN);
    }



    /**
     * /info
     */
    @ApiOperation(value = "创建用户钱包地址")
    @PostMapping(value = "/createWalletAddress")
    public ServerResponse createWalletAddress(@CurrentUser CurrentUserVO currentUser){
        return userService.createWalletAddress(currentUser.getUserId());
    }

    @ApiOperation(value = "查询钱包信息")
    @PostMapping(value = "/moneybag/findRemainNum")
    public ServerResponse findRemainNum(@CurrentUser CurrentUserVO currentUser){
        return userService.findRemainNum(currentUser.getUserId());
    }

    @DisableToken
    @ApiOperation(value = "查询钱包信息")
    @PostMapping(value = "/moneybag/test")
    public ServerResponse test( @RequestParam(value = "avatar",required = true) String address){
        return userService.test(address);
    }


    @ApiOperation(value = "返回广告")
    @DisableToken
    @PostMapping(value = "/Advertisement")
    public ServerResponse Advertisement(){
        return userService.Advertisement();
    }


    /********** end **************/








//    @ApiOperation(value = "校验资金密码")
//    //@DisableToken
//    @PostMapping(value = "/check/checkMoneyPassword")
//    public ServerResponse checkMoneyPassword(@CurrentUser CurrentUserVO currentUser,@RequestParam String moneyPassword){
//        return userService.checkMoneyPassword(currentUser.getUserId(),moneyPassword);
//    }
//
//    @ApiOperation(value = "修改资金密码")
//    //@DisableToken
//    @PostMapping(value = "/find/updateMoneyPassword")
//    public ServerResponse updateMoneyPassword(@CurrentUser CurrentUserVO currentUser,@RequestParam String moneyPassword){
//        return userService.updateMoneyPassword(currentUser.getUserId(),moneyPassword);
//    }
//
//    /******用户信息相关********/
//
//    @DisableToken
//    @PostMapping(value = "/find/queryBuyUserPage")
//    public ServerResponse queryBuyUserPage(@Valid UserQuery qry){
//        return userService.queryBuyUserPage(qry);
//    }
//
//    @ApiOperation(value = "根据关键词查询用户信息（用户名+手机号）")
//    @PostMapping(value = "/find/queryUserPageByKeyword")
//    public ServerResponse queryUserPageByKeyword(@CurrentUser CurrentUserVO currentUser, @Valid UserKeywordQuery qry){
//        return userService.queryUserPageByKeyword(qry);
//    }
//
//    @PostMapping(value = "/question/qryQuestion")
//    public ServerResponse qryQuestion(@CurrentUser CurrentUserVO currentUser,String pageNum,String pageSize,String stadu,String type) {
//        return userService.qryQuestion(currentUser.getUserId(), pageNum, pageSize, stadu,type);
//    }
//
//    @PostMapping(value = "/question/qryAllQuestion")
//    public ServerResponse qryAllQuestion(@CurrentUser CurrentUserVO currentUser,String pageNum,String pageSize,String stadu,String type) {
//        return userService.qryQuestion("", pageNum, pageSize, stadu,type);
//    }
//
//
//    @PostMapping(value = "/question/newQuestion")
//    public ServerResponse newQuestion(@CurrentUser CurrentUserVO currentUser,String question,String type,String title){
//        return userService.newQuestion(currentUser.getUserId(),question,type,title);
//    }
//
//    @PostMapping(value = "/question/replyQuestion")
//    public ServerResponse replyQuestion(@CurrentUser CurrentUserVO currentUser,String questionId,String answer){
//        return userService.replyQuestion(currentUser.getUserId(),questionId,answer);
//    }
//
//
//    /******钱包相关********/
//
//    @ApiOperation(value = "查询钱包余额")
//    @PostMapping(value = "/moneybag/findRemainNum")
//    public ServerResponse findRemainNum(@CurrentUser CurrentUserVO currentUser){
//        return userService.findRemainNum(currentUser.getUserId());
//    }
//
//    @ApiOperation(value = "usdt/BET充值，只返回一个address")
//    @PostMapping(value = "/moneybag/findUserWalletAddress")
//    public ServerResponse findUserWalletAddress(@CurrentUser CurrentUserVO currentUser){
//        return userService.findUserWalletAddress(currentUser.getUserId());
//    }
//
//
//    @ApiOperation(value = "usdt转账--从平台用户钱包--用户其他钱包")
//    @PostMapping(value = "/moneybag/transMoneyUsdt")
//    public ServerResponse transMoneyUsdt(@CurrentUser CurrentUserVO currentUser, @Valid TransMoneyCmd qry){
//        return userService.transMoneyUsdt(currentUser.getUserId(),qry);
//    }
//
//    @ApiOperation(value = "bet提现--平台账户到平台用户钱包")
//    @PostMapping(value = "/moneybag/WithdrawalMoneyBet")
//    public ServerResponse WithdrawalMoneyBet(@CurrentUser CurrentUserVO currentUser, @Valid WithdrawalMoneyCmd qry){
//        return userService.WithdrawalMoneyBet(currentUser.getUserId(),qry);
//    }
//
//    @ApiOperation(value = "bet转账--平台用户钱包--到可交易钱包")
//    @PostMapping(value = "/moneybag/transMoneyBet")
//    public ServerResponse transMoneyBet(@CurrentUser CurrentUserVO currentUser, @Valid TransMoneyCmd qry){
//        return userService.transMoneyBet(currentUser.getUserId(),qry);
//    }
//
//    /********************用户等级***********************/
//
//    @ApiOperation(value = "查看用户等级详情")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(name = "type",value = "等级类型1.默认，2.影视",dataType = "int",required = true)
//    })
//    @PostMapping(value = "/rank/detailRank")
//    public ServerResponse detailRank(@CurrentUser CurrentUserVO currentUser, @RequestParam(value = "type",required = true) int type){
//        return userService.detailRank(currentUser.getUserId(),type);
//    }
//
//
//    /********************用户等级***********************/
//
//    @ApiOperation(value = "查询用户流水")
//    @PostMapping(value = "/findMybill")
//    public ServerResponse findMybill(@CurrentUser CurrentUserVO currentUser ,@Valid FlowRecordAllQuery qry){
//        return userService.findMybill(currentUser.getUserId(),qry);
//    }
//
//
//    @ApiOperation(value = "收益中心")
//    @PostMapping(value = "/findRevenueCenter")
//    public ServerResponse findRevenueCenter(@CurrentUser CurrentUserVO currentUser){
//        return userService.findRevenueCenter(currentUser.getUserId());
//    }
//
//    @ApiOperation(value = "分页查询我的收益 typ 0 昨日，1本周，2本月，3 上月 ，4 本年")
//    @PostMapping(value = "/findMyRevenueCenter")
//    public ServerResponse findMyRevenueCenter(@CurrentUser CurrentUserVO currentUser,@Valid MyRevenueCenterQuery qry){
//        return userService.findMyRevenueCenter(currentUser.getUserId(),qry);
//    }
//
//    /********************资金密码***********************/
//    @ApiOperation(value = "检查资金密码")
//    @PostMapping(value = "/find/checkMoneyPwd")
//    public ServerResponse checkMoneyPwd(@CurrentUser CurrentUserVO currentUser){
//        return userService.checkMoneyPwd(currentUser.getUserId());
//    }

}