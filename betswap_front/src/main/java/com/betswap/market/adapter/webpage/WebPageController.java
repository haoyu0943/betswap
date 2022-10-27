package com.betswap.market.adapter.webpage;


import com.alibaba.fastjson.JSONArray;
import com.betswap.market.app.quotation.QuotationService;
import com.betswap.market.app.user.service.WalletService;
import com.betswap.market.client.quotation.cmd.BetPayCmd;
import com.betswap.market.client.quotation.cmd.BetPayWebCmd;
import com.betswap.market.client.quotation.cmd.QuotationCmd;
import com.betswap.market.client.user.vo.CurrentUserVO;
import com.betswap.market.client.wallet.vo.cmd.RegisterWalleAddresstCmd;
import com.betswap.market.client.wallet.vo.cmd.RegisterWalletCmd;
import com.betswap.market.client.wallet.vo.cmd.RegisterWalletKeyCmd;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.config.annotation.CurrentUser;
import com.betswap.market.infrastruture.config.annotation.DisableToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/webPage")
@Api(value = "WalletController", tags = { "/H5管理API" })
public class WebPageController {
    @Autowired
    private WalletService walletService;
    @Autowired
    private QuotationService quotationService;

    @ApiOperation(value = "根据钱包地址创建账户/导入账户")
    @DisableToken
    @PostMapping(value = "/importByAddress")
    public ServerResponse importByAddress(@Valid RegisterWalleAddresstCmd cmd){
        return walletService.importByAddress(cmd);
    }

    @ApiOperation(value = "根据比赛id获取平台地址")
    @PostMapping(value = "/findAddressById")
    public ServerResponse findAddressById(@CurrentUser CurrentUserVO currentUser,@RequestParam String matchId){
        return walletService.findAddressById(matchId);
    }

    //TODO  投注 直接调用 app 端的 betPay 接口

//    @ApiOperation(value = "投注支付前创建订单--返回逗号分隔的订单id")
//    @PostMapping(value = "/betPayBefore")
//    public ServerResponse betPayBefore( @CurrentUser CurrentUserVO currentUser,@Valid BetPayCmd cmd){
//        return quotationService.betPayBefore(currentUser.getUserId(),cmd);
//    }
//
//    @ApiOperation(value = "支付成功后调用接口")
//    @PostMapping(value = "/betPayFinish")
//    public ServerResponse betPayFinish( @CurrentUser CurrentUserVO currentUser,@Valid BetPayWebCmd cmd){
//        return quotationService.betPayFinish(currentUser.getUserId(),cmd);
//    }
//
//    @ApiOperation(value = "支付页面取消时调用接口--逗号分隔的订单集合")
//    @PostMapping(value = "/betPayCancel")
//    public ServerResponse betPayCancel( @CurrentUser CurrentUserVO currentUser,@RequestParam String orderId){
//        return quotationService.betPayCancel(currentUser.getUserId(),orderId);
//    }


    @ApiOperation(value = "创建盘口")
    @PostMapping(value = "/createQuotationsWeb")
    public ServerResponse createQuotationsWeb(@CurrentUser CurrentUserVO currentUser, @RequestParam("quotations")  String quotations,@RequestParam String txId){
        List<QuotationCmd> cmds= JSONArray.parseArray(quotations, QuotationCmd.class);
        return quotationService.createQuotationsWeb(currentUser.getUserId(),cmds,txId);
    }


    @DisableToken
    @ApiOperation(value = "创建盘口")
    @PostMapping(value = "/test")
    public ServerResponse test(@RequestParam String txId){
        return quotationService.test(txId);
    }

}