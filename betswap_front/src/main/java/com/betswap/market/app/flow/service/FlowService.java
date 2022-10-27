package com.betswap.market.app.flow.service;

import com.betswap.market.client.common.vo.qry.CommonPageQry;
import com.betswap.market.client.flow.vo.query.TransferRecordAllQuery;
import com.betswap.market.client.quotation.qry.BetRecordQuery;
import com.betswap.market.client.quotation.qry.ProfitQuery;
import com.betswap.market.infrastruture.common.response.ServerResponse;

public interface FlowService {


    //usdt 充值记录
    ServerResponse queryUSDTRechargeRecord(String userId, CommonPageQry qry);

    //Bet 充值记录
    ServerResponse queryBETRechargeRecord(String userId, CommonPageQry qry);

    ServerResponse queryUSDTWithdrawalRecord(String userId, CommonPageQry qry);

    ServerResponse queryBETWithdrawalRecord(String userId, CommonPageQry qry);

    ServerResponse queryBetRecordByCondition(String userId, BetRecordQuery qry);

    ServerResponse queryBetRecordDetail(String userId, String id, int transactionType);

    ServerResponse queryProfitByCondition(String userId, ProfitQuery qry);

    ServerResponse queryTransferRecordPage(String userId, TransferRecordAllQuery qry);

    ServerResponse queryQuotationRecordByCondition(String userId, BetRecordQuery qry);

    ServerResponse queryQuotationRProfitByCondition(String userId, ProfitQuery qry);

    ServerResponse queryTransferRecordTrxPage(String userId, TransferRecordAllQuery qry);
}
