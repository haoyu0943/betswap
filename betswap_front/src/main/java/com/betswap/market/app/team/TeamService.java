package com.betswap.market.app.team;
import com.betswap.market.client.team.qry.TeamRankingQuery;
import com.betswap.market.client.team.qry.TeamRevenueQuery;
import com.betswap.market.infrastruture.common.response.ServerResponse;

public interface TeamService {

    ServerResponse teamMember(String userId, Integer ifLoss);

    ServerResponse joinTeamByInviter(String userId, String inviterId);

    ServerResponse findTeamRanking(String userId, TeamRankingQuery qry);

    ServerResponse findTeamRevenue(String userId, TeamRevenueQuery qry);

    ServerResponse myTeamMember(String userId);
}
