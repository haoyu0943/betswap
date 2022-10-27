package com.betswap.market.app.quotation;
import com.betswap.market.client.quotation.cmd.BetPayCmd;
import com.betswap.market.client.quotation.cmd.BetPayWebCmd;
import com.betswap.market.client.quotation.cmd.QuotationCmd;
import com.betswap.market.client.quotation.qry.BetQuery;
import com.betswap.market.client.quotation.qry.MatchAllQuery;
import com.betswap.market.infrastruture.common.response.ServerResponse;

import java.math.BigDecimal;
import java.util.List;

public interface QuotationService {

    ServerResponse createQuotations(String userId, List<QuotationCmd> cmds);

    ServerResponse queryMathByCondition(MatchAllQuery qry);

    ServerResponse queryQuotationByMatchId(String matchId, Integer quotationType, Integer oddsType);

    ServerResponse queryBetPage(BetQuery qry);

    ServerResponse betPay(String userId,BetPayCmd cmd);

    ServerResponse cancelQuotations(String userId, String quotationId);

    ServerResponse queryMathFruit(MatchAllQuery qry);

    ServerResponse queryMathCount(Long startTime);

    ServerResponse queryWinMath(MatchAllQuery qry);

    ServerResponse betPayBefore(String userId, BetPayCmd cmd);

    ServerResponse betPayFinish(String userId, BetPayWebCmd cmd);

    ServerResponse betPayCancel(String userId, String orderId);

    ServerResponse createQuotationsWeb(String userId, List<QuotationCmd> cmds, String txId);

    ServerResponse test(String txId);

    ServerResponse testIds(String ids, int count);

    ServerResponse queryBetMessage(String userId, BigDecimal betAmount, String matchId);
}
