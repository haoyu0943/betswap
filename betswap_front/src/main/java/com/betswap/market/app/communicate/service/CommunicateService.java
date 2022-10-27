package com.betswap.market.app.communicate.service;

import com.betswap.market.client.communicate.qry.MyMsgQry;
import com.betswap.market.infrastruture.common.response.ServerResponse;

public interface CommunicateService {
    ServerResponse SendMsg(String Sender, long CommunicationId, String msg, int type);
    ServerResponse SendCmd(String Sender, long CommunicationId, short cmd);

//    ServerResponse OpenAfterSale(String Visitor, String OrderId);
    ServerResponse OpenUserSupport(String Visitor);

    ServerResponse getCommunicateRecord(String UserId,long CommunicateId);
    ServerResponse queryAllCreate(String UserId,int pageNum);
    ServerResponse queryAllReceive(String UserId,int pageNum);
    ServerResponse AdminQryAllToDo(String UserId,int pageNum);
    ServerResponse AdminQryAllDone(String UserId,int pageNum);

    ServerResponse queryAllSysMsg(String UserId,int pageNum);
    ServerResponse querySysMsgById(String UserId,int Id);

    ServerResponse queryMyMsgByType(String userId, MyMsgQry qry);

    ServerResponse queryMyMsgById(String userId, Long id);
}
