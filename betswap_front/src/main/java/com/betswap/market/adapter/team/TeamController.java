package com.betswap.market.adapter.team;

import com.betswap.market.app.team.TeamService;

import com.betswap.market.client.team.qry.TeamRankingQuery;
import com.betswap.market.client.team.qry.TeamRevenueQuery;
import com.betswap.market.client.user.vo.CurrentUserVO;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.config.annotation.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/team")
@Api(value = "TeamController", tags = { "/team 团队Controller" })
public class TeamController {
    @Autowired
    private TeamService teamService;

    @ApiOperation(value = "根据邀请码加入团队")
    @PostMapping(value = "/joinTeamByInviter")
    public ServerResponse joinTeamByInviter(@CurrentUser CurrentUserVO currentUser,  @RequestParam(value = "code") String code){
        return teamService.joinTeamByInviter(currentUser.getUserId(),code);
    }


    @ApiOperation(value = "获取团队数据--我的团队")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "ifLoss",value = "是否统计亏损 否0 是1 ",dataType = "int",required = true)
    })
    @PostMapping(value = "/teamMember")
    public ServerResponse teamMember(@CurrentUser CurrentUserVO currentUser, @RequestParam("ifLoss") int ifLoss){
        return teamService.teamMember(currentUser.getUserId(),ifLoss);
    }

    @ApiOperation(value = "分页查询团队排名")
    @PostMapping(value = "/findTeamRanking")
    public ServerResponse findTeamRanking(@CurrentUser CurrentUserVO currentUser,@Valid TeamRankingQuery qry){
        return teamService.findTeamRanking(currentUser.getUserId(),qry);
    }
    @ApiOperation(value = "分页查询团队收益")
    @PostMapping(value = "/findTeamRevenue")
    public ServerResponse findTeamRevenue(@CurrentUser CurrentUserVO currentUser,@Valid TeamRevenueQuery qry){
        return teamService.findTeamRevenue(currentUser.getUserId(),qry);
    }

    @ApiOperation(value = "当前等级数据--代理等级")
    @PostMapping(value = "/myTeamMember")
    public ServerResponse myTeamMember(@CurrentUser CurrentUserVO currentUser){
        return teamService.myTeamMember(currentUser.getUserId());
    }


}
