package com.betswap.market.adapter.communicate;

import com.betswap.market.app.communicate.service.CommunicateService;
import com.betswap.market.client.communicate.qry.MyMsgQry;
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

@RestController
@RequestMapping("communicate")
@Api(value = "CommunicateController", tags = { "/communicate 交流" })
public class CommunicateController {
    @Autowired
    private CommunicateService communicateService;

    @ApiOperation(value = "商家/客户共用。回复售后聊天")
    @PostMapping(value = "/SendMsg")
    public ServerResponse SendMsg(@CurrentUser CurrentUserVO currentUser, long CommunicationId, String msg, int type){
        return communicateService.SendMsg(currentUser.getUserId(), CommunicationId, msg, type);
    }

    @PostMapping(value = "/SendCmd")
    public ServerResponse SendCmd(@CurrentUser CurrentUserVO currentUser, long CommunicationId, short cmd){
        return communicateService.SendCmd(currentUser.getUserId(), CommunicationId, cmd);
    }

//    @PostMapping(value = "/OpenAfterSale")
//    public ServerResponse OpenAfterSale(@CurrentUser CurrentUserVO currentUser, String OrderId){
//        return communicateService.OpenAfterSale(currentUser.getUserId(), OrderId);
//    }

    @PostMapping(value = "/OpenUserSupport")
    public ServerResponse OpenUserSupport(@CurrentUser CurrentUserVO currentUser){
        return communicateService.OpenUserSupport(currentUser.getUserId());
    }

    @ApiOperation(value = "商家/客户共用。获取售后聊天详情，包括图片")
    @PostMapping(value = "/getRecord")
    public ServerResponse getRecord(@CurrentUser CurrentUserVO currentUser, long RecordId){
        return communicateService.getCommunicateRecord(currentUser.getUserId(),RecordId);
    }

    @PostMapping(value = "/queryAllCreate")
    public ServerResponse queryAllCreate(@CurrentUser CurrentUserVO currentUser,int pageNum){
        return communicateService.queryAllCreate(currentUser.getUserId(),pageNum);
    }
    @ApiOperation(value = "商家获取向自己的所有售后，分页")
    @PostMapping(value = "/queryAllReceive")
    public ServerResponse queryAllReceive(@CurrentUser CurrentUserVO currentUser,int pageNum){
        return communicateService.queryAllReceive(currentUser.getUserId(),pageNum);
    }

    @PostMapping(value = "/AdminQryAllToDo")
    public ServerResponse AdminQryAllToDo(@CurrentUser CurrentUserVO currentUser,int pageNum){
        return communicateService.AdminQryAllToDo(currentUser.getUserId(),pageNum);
    }

    @PostMapping(value = "/AdminQryAllDone")
    public ServerResponse AdminQryAllDone(@CurrentUser CurrentUserVO currentUser,int pageNum){
        return communicateService.AdminQryAllDone(currentUser.getUserId(),pageNum);
    }

    @ApiOperation(value = "查看系统消息列表,返回第pageNum页以及总数.假如read不为0，表明已读")
    @PostMapping(value = "/queryAllSysMsg")
    public ServerResponse queryAllSysMsg(@CurrentUser CurrentUserVO currentUser,int pageNum){
        return communicateService.queryAllSysMsg(currentUser.getUserId(),pageNum);
    }

    @ApiOperation(value = "查看某一条系统消息")
    @PostMapping(value = "/querySysMsgById")
    public ServerResponse querySysMsgById(@CurrentUser CurrentUserVO currentUser,@RequestParam(value = "Id",required = true)int Id){
        return communicateService.querySysMsgById(currentUser.getUserId(),Id);
    }


    @ApiOperation(value = "根据类型查询个人消息列表 0.账户消息 ，1.资产消息")
    @PostMapping(value = "/queryMyMsgByType")
    public ServerResponse queryMyMsgByType(@CurrentUser CurrentUserVO currentUser,@Valid MyMsgQry qry){
        return communicateService.queryMyMsgByType(currentUser.getUserId(),qry);
    }

    @ApiOperation(value = "查看某一条个人消息")
    @PostMapping(value = "/queryMyMsgById")
    public ServerResponse queryMyMsgById(@CurrentUser CurrentUserVO currentUser, @RequestParam(value = "id",required = true) Long id){
        return communicateService.queryMyMsgById(currentUser.getUserId(),id);
    }

}
