package com.betswap.market.adapter.quotation;

import com.alibaba.fastjson.JSONArray;
import com.betswap.market.app.quotation.QuotationService;
import com.betswap.market.client.quotation.cmd.BetPayCmd;
import com.betswap.market.client.quotation.cmd.QuotationCmd;
import com.betswap.market.client.quotation.dto.QuotationDTO;
import com.betswap.market.client.quotation.qry.BetQuery;
import com.betswap.market.client.quotation.qry.MatchAllQuery;
import com.betswap.market.client.user.vo.CurrentUserVO;
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
import java.math.BigDecimal;
import java.util.List;

//博彩controller
@RestController
@RequestMapping("/quotation")
@Api(value = "QuotationController", tags = { "/quotation 盘口controller" })
public class QuotationController {
    @Autowired
    private QuotationService quotationService;


    @ApiOperation(value = "创建盘口")
    @PostMapping(value = "/createQuotations")
    public ServerResponse createQuotations(@CurrentUser CurrentUserVO currentUser, @RequestParam("quotations")  String quotations){
        List<QuotationCmd> cmds= JSONArray.parseArray(quotations, QuotationCmd.class);
        return quotationService.createQuotations(currentUser.getUserId(),cmds);
    }


    @ApiOperation(value = "取消开盘 ,比赛状态未开赛和无人投注 时可以取消")
    @PostMapping(value = "/cancelQuotations")
    public ServerResponse cancelQuotations(@CurrentUser CurrentUserVO currentUser, @RequestParam("quotationId")  String quotationId){

        return quotationService.cancelQuotations(currentUser.getUserId(),quotationId);
    }


    @ApiOperation(value = "查询比赛信息-首页")
    @PostMapping(value = "/queryMathByCondition")
    public ServerResponse queryMathByCondition( @CurrentUser CurrentUserVO currentUser,@Valid MatchAllQuery qry){
        return quotationService.queryMathByCondition(qry);
    }

    @ApiOperation(value = "查询冠军-首页")
    @PostMapping(value = "/queryWinMath")
    public ServerResponse queryWinMath( @CurrentUser CurrentUserVO currentUser,@Valid MatchAllQuery qry){
        return quotationService.queryWinMath(qry);
    }


    @ApiOperation(value = "查询赛果-首页")
    @PostMapping(value = "/queryMathFruit")
    public ServerResponse queryMathFruit( @CurrentUser CurrentUserVO currentUser,@Valid MatchAllQuery qry){
        return quotationService.queryMathFruit(qry);
    }


    @ApiOperation(value = "查询比赛数量-筛选框")
    @PostMapping(value = "/queryMathCount")
    public ServerResponse queryMathCount( @CurrentUser CurrentUserVO currentUser, @RequestParam("startTime")  Long startTime){
        return quotationService.queryMathCount(startTime);
    }



    @ApiOperation(value = "根据比赛id和玩法类型查询开盘信息-投注盘口界面")
    @PostMapping(value = "/queryQuotationByMatchId")
    public ServerResponse queryQuotationByMatchId( @CurrentUser CurrentUserVO currentUser, @RequestParam("matchId")  String matchId,
                                                   @RequestParam("quotationType")  Integer quotationType,
                                                   @RequestParam("oddsType")  Integer oddsType){//oddsType 0 是 任何赔率，1 是 最佳赔率
        return quotationService.queryQuotationByMatchId(matchId,quotationType,oddsType);
    }


    @ApiOperation(value = "根据比赛id和玩法类型查询开盘信息-具体投注界面")
    @PostMapping(value = "/queryBetPage")
    public ServerResponse queryBetPage( @CurrentUser CurrentUserVO currentUser,@Valid BetQuery qry){
        return quotationService.queryBetPage(qry);
    }



    @ApiOperation(value = "根据投注金额获取转账信息")
    @PostMapping(value = "/queryBetMessage")
    public ServerResponse queryBetMessage( @CurrentUser CurrentUserVO currentUser,
                                           @RequestParam("betAmount") BigDecimal betAmount,
                                           @RequestParam("matchId") String matchId){
        return quotationService.queryBetMessage(currentUser.getUserId(),betAmount,matchId);
    }


    @ApiOperation(value = "投注支付--app 和H5 通過 txId 判断")
    @PostMapping(value = "/betPay")
    public ServerResponse betPay( @CurrentUser CurrentUserVO currentUser,@Valid BetPayCmd cmd){
        return quotationService.betPay(currentUser.getUserId(),cmd);
    }

    @DisableToken
    @ApiOperation(value = "testIds")
    @PostMapping(value = "/testIds")
    public ServerResponse testIds( @RequestParam("ids") String ids,@RequestParam("count") int count){
        return quotationService.testIds(ids,count);
    }


}
