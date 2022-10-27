package com.betswap.market.app.communicate.impl;

import com.alibaba.fastjson.JSONObject;
import com.betswap.market.app.communicate.service.CommunicateService;
import com.betswap.market.client.communicate.converter.MyMsgConverter;
import com.betswap.market.client.communicate.dto.MyMsgDTO;
import com.betswap.market.client.communicate.dto.SysMsgDTO;
import com.betswap.market.client.communicate.qry.MyMsgQry;
import com.betswap.market.client.user.enums.UserPrivilegeEnum;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.communicate.dao.MyMsgDao;
import com.betswap.market.infrastruture.communicate.dao.SysMsgDao;
import com.betswap.market.infrastruture.communicate.dao.SysMsgHaveReadDao;
import com.betswap.market.infrastruture.communicate.entity.MyMsgEntity;
import com.betswap.market.infrastruture.communicate.entity.SysMsgEntity;
import com.betswap.market.infrastruture.communicate.dao.CommunicationDao;
import com.betswap.market.infrastruture.communicate.entity.CommunicateEntity;
import com.betswap.market.infrastruture.user.dao.UserDao;
import com.betswap.market.infrastruture.user.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.betswap.market.infrastruture.utils.constant.TmDateUtil.getCurrentTime;

@Service
@Slf4j
public class CommunicateServiceImpl implements CommunicateService {
    @Autowired
    private CommunicationDao communicationDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SysMsgDao sysMsgDao;
    @Autowired
    private SysMsgHaveReadDao sysMsgHaveReadDao;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private MyMsgDao myMsgDao;


    @Override
    public ServerResponse SendMsg(String Sender, long CommunicationId, String msg, int type) {
        if(IsAdmin(Sender)) communicationDao.AdminDone(CommunicationId);
        try{
            List<List<Object>> record = (List<List<Object>>) getRecordFromFile(CommunicationId);
            List<Object> Msg = new ArrayList<>();
            Msg.add(getCurrentTime());
            Msg.add(userDao.findUserNameByUserId(Sender));
            Msg.add(msg);
            Msg.add(type);
            record.add(Msg);
            writeObjectToFile(record,CommunicationId);
            return ServerResponse.getInstance().ok();
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail();
        }
    }

    @Override
    public ServerResponse SendCmd(String Sender, long CommunicationId, short cmd) { //允许通过cmd实现申请平台介入、删除通话等功能
        if(cmd==1) communicationDao.AskAdminForAfterSale(CommunicationId);
        return ServerResponse.getInstance().ok();
    }

//    @Override
//    public ServerResponse OpenAfterSale(String Visitor, String OrderId) {
//        Long id = communicationDao.findIdByOrderId(OrderId);
//        if(id==null){
//            return CreateAfterSale(Visitor,OrderId);
//        }else{
//            return ServerResponse.getInstance().ok().data(id);
//        }
//    }
//
//    public ServerResponse CreateAfterSale(String creator, String OrderId) {
//        Object[] o = orderDao.findSellerIdAndGoodNameByOrderId(OrderId).toArray();
//        CommunicateEntity e = communicationDao.save(CommunicateEntity.builder().Creator(creator).Receiver((String)o[0]).OrderId(OrderId)
//                .type((short)3).status((short)2).createTime(System.currentTimeMillis()).build());
//        List<List<Object>> record = new ArrayList<>();
//        List<Object> InitialMsg = new ArrayList<>();
//        InitialMsg.add(getCurrentTime());
//        InitialMsg.add("");
//        InitialMsg.add(userDao.findUserNameByUserId(creator)+"发起售后处理，订单号:"+OrderId+"。商品名:"+(String)o[1]);
//        InitialMsg.add(0);
//        record.add(InitialMsg);
//        long Id = e.getId();
//        writeObjectToFile(record,Id);
//        return ServerResponse.getInstance().ok().data(Id);
//    }

    @Override
    public ServerResponse OpenUserSupport(String Visitor) {
        Long id = communicationDao.findUserSupportByCreator(Visitor);
        if(id==null){
            return CreateUserSupport(Visitor);
        }else{
            return ServerResponse.getInstance().ok().data(id);
        }
    }

    public ServerResponse CreateUserSupport(String creator) {
        CommunicateEntity e = communicationDao.save(CommunicateEntity.builder().Creator(creator).type((short)1)
                .status((short)0).createTime(System.currentTimeMillis()).build());
        List<List<Object>> record = new ArrayList<>();
        List<Object> InitialMsg = new ArrayList<>();
        InitialMsg.add(getCurrentTime());
        InitialMsg.add("");
        InitialMsg.add("请在下方输入您遇到的问题或者对平台的建议");
        record.add(InitialMsg);
        long Id = e.getId();
        writeObjectToFile(record,Id);
        return ServerResponse.getInstance().ok().data(Id);
    }

    public ServerResponse getCommunicateRecord(String UserId,long CommunicateId) {
        String[] strs=communicationDao.findRelatedUserIdById(CommunicateId).get(0);
        if(UserId.equals(strs[0])){
            communicationDao.CreatorRead(CommunicateId);
        }else if(UserId.equals(strs[1])){
            communicationDao.ReceiverRead(CommunicateId);
        }else{
            return null;
        }
        return ServerResponse.getInstance().ok().data(getRecordFromFile(CommunicateId));
    }

    @Override
    public ServerResponse queryAllCreate(String UserId,int pageNum) {
        JSONObject json = new JSONObject();
        json.put("total",communicationDao.countByCreator(UserId));
        json.put("list",communicationDao.findByCreator(UserId,(pageNum-1)*10));
        return ServerResponse.getInstance().ok().data(json);
    }
    @Override
    public ServerResponse queryAllReceive(String UserId,int pageNum) {
        JSONObject json = new JSONObject();
        json.put("total",communicationDao.countByReceive(UserId));
        json.put("list",communicationDao.findByReceive(UserId,(pageNum-1)*10));
        return ServerResponse.getInstance().ok().data(json);
    }

    public boolean IsAdmin(String userId) {//管理员账号
        return UserPrivilegeEnum.ADMIN.status.equals(userDao.findPrivilegeByUserId(userId));
    }
    @Override
    public ServerResponse AdminQryAllToDo(String UserId,int pageNum) {
        if(!IsAdmin(UserId)) return ServerResponse.getInstance().fail();
        JSONObject json = new JSONObject();
        json.put("total",communicationDao.countByToDo());
        json.put("list",communicationDao.findByToDo((pageNum-1)*10));
        return ServerResponse.getInstance().ok().data(json);
    }
    @Override
    public ServerResponse AdminQryAllDone(String UserId,int pageNum) {
        if(!IsAdmin(UserId)) return ServerResponse.getInstance().fail();
        JSONObject json = new JSONObject();
        json.put("total",communicationDao.countByDone());
        json.put("list",communicationDao.findByDone((pageNum-1)*10));
        return ServerResponse.getInstance().ok().data(json);
    }



    public void writeObjectToFile(List<List<Object>> obj, long Id){
        try {
            ObjectOutputStream objOut=new ObjectOutputStream(
                    new FileOutputStream(new File("record"+Id+".db"))
            );
            objOut.writeObject(obj);
            objOut.close();
        } catch (IOException e) {
            System.out.println("CommunicateServiceImpl.java : writeObjectToFile failed");
            e.printStackTrace();
        }
    }
    public Object getRecordFromFile(long Id){
        try {
            ObjectInputStream objIn=new ObjectInputStream(
                    new FileInputStream(new File("record"+Id+".db"))
            );
            Object data=objIn.readObject();
            objIn.close();
            return data;
        } catch (Exception e) {
            System.out.println("getRecordFromFile failed");
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public ServerResponse queryAllSysMsg(String UserId,int pageNum){
        Byte[] getReadState = sysMsgHaveReadDao.getReadState(UserId);
        if(getReadState==null){
            getReadState=new Byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            sysMsgHaveReadDao.create(UserId,getReadState);
        }
        List<SysMsgDTO> MsgsDTO = new ArrayList<>();
        for(SysMsgEntity msg:sysMsgDao.queryAllSysMsg((pageNum-1)*10)){
            SysMsgDTO tmp = MyMsgConverter.INSTANCE.entityS2dto(msg);
            int id=msg.getId()&127;
            int ByteIndex = (id>>3)&15;
            int BitIndex  = id&7;
            int havaRead  = (getReadState[ByteIndex] & (1 << BitIndex));
            tmp.setRead(havaRead);
            MsgsDTO.add(tmp);
        }
        JSONObject json = new JSONObject();
        json.put("total",sysMsgDao.countSysMsg());
        json.put("list",MsgsDTO);
        return ServerResponse.getInstance().ok().data(json);
    }

    @Override
    public ServerResponse querySysMsgById(String UserId,int Id){
        Byte[] getReadState = sysMsgHaveReadDao.getReadState(UserId);
        Id&=127;
        int ByteIndex = (Id>>3)&15;
        int BitIndex  = Id&7;
        getReadState[ByteIndex] = (byte)(getReadState[ByteIndex]|(1<<BitIndex));
        getReadState[(ByteIndex+10)&15] = 0; //一种循环,只记录最近80条消息的已读状态。
        sysMsgHaveReadDao.updateReadState(UserId,getReadState);
        return ServerResponse.getInstance().ok().data(sysMsgDao.findSysMsgById(Id));
    }

    @Override
    public ServerResponse queryMyMsgByType(String userId, MyMsgQry qry) {
        JSONObject resultJson = new JSONObject();
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            StringBuilder sqljoint=new StringBuilder();
            StringBuilder sqltotal=new StringBuilder();
            sqljoint.append(" select* from my_msg as t where  1=1 ");
            sqltotal.append(" select count(1) from my_msg as t where   1=1 ");
            if(StringUtils.isNotBlank(userId)){
                sqljoint.append(" and  t.user_id=  '"+userId+"'");
                sqltotal.append(" and  t.user_id=  '"+userId+"'");
            }
            if(StringUtils.isNotBlank(qry.getType())){
                sqljoint.append(" and  t.type=  '"+qry.getType()+"'");
                sqltotal.append(" and  t.type=  '"+qry.getType()+"'");
            }
            sqljoint.append(" order by t.create_time desc");
            sqljoint.append(" limit "+qry.getPageNum()* qry.getPageSize()+","+qry.getPageSize());
            String sql=sqljoint.toString();
            String sql1=sqltotal.toString();
            //创建原生查询的时候，将info.class类即第二个参数，写成要传回的bean，这样就可以直接用List<Bean>接收
            Query query = em.createNativeQuery(sql, MyMsgEntity.class);
            List<MyMsgEntity> myMsgs=query.getResultList();
            List<MyMsgDTO> lst=new ArrayList<>();
            for(MyMsgEntity myMsg:myMsgs){
                MyMsgDTO dto= MyMsgConverter.INSTANCE.entity2dto(myMsg);
                if(StringUtils.isNotBlank(myMsg.getInitiator())){
                    UserEntity user= userDao.findByUserId(myMsg.getInitiator());
                    if(user!=null){
                        dto.setInitiatorName(user.getUserName());
                        dto.setInitiatorCover(user.getUserAvatar()==null?null:user.getUserAvatar());
                    }
                }

                lst.add(dto);
            }

            Query query1 = em.createNativeQuery(sql1);
            Integer total= Integer.valueOf(query1.getSingleResult().toString());
            resultJson.put("myMsgs",lst);
            resultJson.put("pageSize",qry.getPageSize());
            resultJson.put("pageNum",qry.getPageNum());
            resultJson.put("total",total);
        }finally {
            em.close();
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Transactional
    @Override
    public ServerResponse queryMyMsgById(String userId, Long id) {
        MyMsgEntity my=myMsgDao.findFirstByUserIdAndId(userId,id);
        if(my==null)
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.NO_SUCH_DATA);
        MyMsgDTO dto= MyMsgConverter.INSTANCE.entity2dto(my);
        if(StringUtils.isNotBlank(my.getInitiator())){
            UserEntity user= userDao.findByUserId(my.getInitiator());
            if(user!=null){
                dto.setInitiatorName(user.getUserName());
                dto.setInitiatorCover(user.getUserAvatar()==null?null:user.getUserAvatar());
            }
        }

        my.setReadStatus(1);//已读
        myMsgDao.save(my);

        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
    }


}
