package com.betswap.market.adapter.flow;

import com.betswap.market.app.flow.service.FlowService;
import com.betswap.market.client.common.vo.qry.CommonPageQry;
import com.betswap.market.client.flow.vo.query.TransferRecordAllQuery;
import com.betswap.market.client.quotation.TransactionTypeEnum;
import com.betswap.market.client.quotation.qry.BetRecordQuery;
import com.betswap.market.client.quotation.qry.ProfitQuery;
import com.betswap.market.client.user.vo.CurrentUserVO;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.config.annotation.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


//流水controller
@RestController
@RequestMapping("/flow")
@Api(value = "FlowController", tags = { "/flow 流水controller" })
public class FlowController {
    @Autowired
    private FlowService flowService;

//    @ApiOperation(value = "USDT 充值记录")
//    @PostMapping(value = "/queryUSDTRechargeRecord")
//    public ServerResponse queryUSDTRechargeRecord( @CurrentUser CurrentUserVO currentUser, @Valid CommonPageQry qry){
//        return flowService.queryUSDTRechargeRecord(currentUser.getUserId(),qry);
//    }
//
//    @ApiOperation(value = "BET 充值记录")
//    @PostMapping(value = "/queryBETRechargeRecord")
//    public ServerResponse queryBETRechargeRecord( @CurrentUser CurrentUserVO currentUser, @Valid CommonPageQry qry){
//        return flowService.queryBETRechargeRecord(currentUser.getUserId(),qry);
//    }
//
//    @ApiOperation(value = "USDT 转账记录")
//    @PostMapping(value = "/queryUSDTWithdrawalRecord")
//    public ServerResponse queryUSDTWithdrawalRecord( @CurrentUser CurrentUserVO currentUser, @Valid CommonPageQry qry){
//        return flowService.queryUSDTWithdrawalRecord(currentUser.getUserId(),qry);
//    }
//    @ApiOperation(value = "BET 转账记录")
//    @PostMapping(value = "/queryBETWithdrawalRecord")
//    public ServerResponse queryBETWithdrawalRecord( @CurrentUser CurrentUserVO currentUser, @Valid CommonPageQry qry){
//        return flowService.queryBETWithdrawalRecord(currentUser.getUserId(),qry);
//    }



    @ApiOperation(value = "根据条件查询交易记录")
    @PostMapping(value = "/queryBetRecordByCondition")
    public ServerResponse queryBetRecordByCondition( @CurrentUser CurrentUserVO currentUser, @Valid BetRecordQuery qry){
        if(TransactionTypeEnum.TRANSACTION_BET.type==qry.getTransactionType()){
            return flowService.queryBetRecordByCondition(currentUser.getUserId(),qry);
        }
        return flowService.queryQuotationRecordByCondition(currentUser.getUserId(),qry);
    }

    @ApiOperation(value = "根据交易记录id查询交易记录详情")
    @PostMapping(value = "/queryBetRecordDetail")
    public ServerResponse queryBetRecordDetail( @CurrentUser CurrentUserVO currentUser,
                                                @RequestParam(value = "transactionType",required = true) int transactionType,
                                                @RequestParam(value = "id",required = true) String id){
        return flowService.queryBetRecordDetail(currentUser.getUserId(),id,transactionType);
    }


    // 只显示已完结的 订单
    @ApiOperation(value = "根据条件查询盈利情况")
    @PostMapping(value = "/queryProfitByCondition")
    public ServerResponse queryProfitByCondition( @CurrentUser CurrentUserVO currentUser, @Valid ProfitQuery qry){
        if(TransactionTypeEnum.TRANSACTION_BET.type==qry.getTransactionType()){
            return flowService.queryProfitByCondition(currentUser.getUserId(),qry);
        }
        return flowService.queryQuotationRProfitByCondition(currentUser.getUserId(),qry);
    }


    @ApiOperation(value = "查询充值转账记录trc20")
    @PostMapping(value = "/queryTransferRecordPage")
    public ServerResponse queryTransferRecordPage( @CurrentUser CurrentUserVO currentUser, @Valid TransferRecordAllQuery qry){
        return flowService.queryTransferRecordPage(currentUser.getUserId(),qry);
    }

    @ApiOperation(value = "查询充值转账记录trx")
    @PostMapping(value = "/queryTransferRecordTrxPage")
    public ServerResponse queryTransferRecordTrxPage( @CurrentUser CurrentUserVO currentUser, @Valid TransferRecordAllQuery qry){
        return flowService.queryTransferRecordTrxPage(currentUser.getUserId(),qry);
    }

}
