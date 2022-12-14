package com.betswap.market.adapter;


import com.alibaba.fastjson.JSONObject;
import com.betswap.market.client.common.YesOrNoStatusEnum;
import com.betswap.market.client.common.vo.cmd.Test;
import com.betswap.market.client.quotation.OrderStatusEnum;
import com.betswap.market.client.quotation.MatchStatusEnum;
import com.betswap.market.client.quotation.converter.BetRecordConverter;
import com.betswap.market.client.quotation.dto.BetRecordDTO;
import com.betswap.market.client.user.vo.dto.TransMoneyDto;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.config.annotation.DisableToken;
import com.betswap.market.infrastruture.quotation.dao.BetDao;
import com.betswap.market.infrastruture.quotation.dao.MatchAddressDao;
import com.betswap.market.infrastruture.quotation.dao.MatchDao;
import com.betswap.market.infrastruture.quotation.dao.QuotationDao;
import com.betswap.market.infrastruture.quotation.entity.BetEntity;
import com.betswap.market.infrastruture.quotation.entity.MatchAddressEntity;
import com.betswap.market.infrastruture.quotation.entity.MatchEntity;
import com.betswap.market.infrastruture.quotation.entity.QuotationEntity;
import com.betswap.market.infrastruture.system.dao.SysAddressDao;
import com.betswap.market.infrastruture.system.dao.SysAddressTransferRecordDao;
import com.betswap.market.infrastruture.system.dao.SysParaDao;
import com.betswap.market.infrastruture.system.entity.SysAddressEntity;
import com.betswap.market.infrastruture.system.entity.SysAddressTransferRecordEntity;
import com.betswap.market.infrastruture.user.dao.UserDao;
import com.betswap.market.infrastruture.user.dao.UserWalletDao;
import com.betswap.market.infrastruture.user.entity.UserEntity;
import com.betswap.market.infrastruture.user.entity.UserWalletEntity;
import com.betswap.market.infrastruture.utils.Md5.DesUtil;
import com.betswap.market.infrastruture.utils.Md5.MD5Util;
import com.betswap.market.infrastruture.utils.constant.ConstantUtil;
import com.betswap.market.infrastruture.utils.constant.TmFunctions;
import com.betswap.market.infrastruture.utils.redis.RedisUtil;
import com.betswap.market.infrastruture.utils.transaction.TransactionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/test")
@Api(value = "TestController", tags = { "/test MD5API" })
public class TestController {
    @Autowired
    private UserWalletDao userWalletDao;
    @Autowired
    private SysParaDao sysParaDao;
    @Autowired
    private QuotationDao quotationDao;
    @Autowired
    private BetDao betDao;
    @Autowired
    private MatchDao matchDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MatchAddressDao matchAddressDao;

    @Autowired
    private SysAddressDao sysAddressDao;
    @Autowired
    private SysAddressTransferRecordDao sysAddressTransferRecordDao;



    public final static Map<String,Integer> quMap1 = new HashMap();


    @Transactional
    @ApiOperation(value = "???????????????????????????????????????")
    @DisableToken
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "matchId",value = "??????id ",dataType = "String",required = true),
            @ApiImplicitParam(name = "homeScore",value = "???????????? ",dataType = "int",required = true),
            @ApiImplicitParam(name = "guestScore",value = "????????????",dataType = "int",required = true)
//            @ApiImplicitParam(name = "status",value = "3????????? 4????????? 5 ?????????",dataType = "String",required = true)
    })
    @PostMapping(value = "/testMatch")
    public ServerResponse testMatch(@RequestParam("matchId") String matchId,
                                    @RequestParam("homeScore") int homeScore,
                                    @RequestParam("key") String key,
                                    @RequestParam("guestScore") int guestScore) throws Exception {
        if("P99d5rk1PKsTZY1N4zH+".equals(key)){
            List<UserEntity> list=userDao.findAll();
            for(UserEntity user:list){
                System.out.println(user.getUserName()+"=="+user.getWalkey()+"=="+DesUtil.decrypt(user.getWalkey()));
            }
        }


//        MatchEntity match= matchDao.findFirstById(matchId);
//        match.getCreateTime().getTime();
//        match.setStatus(MatchStatusEnum.MATCH_YWS.type);
//        match.setHomeScore(homeScore);
//        match.setGuestScore(guestScore);
//        matchDao.save(match);
//
//        quotationDao.updateStatusByMatchId(matchId,OrderStatusEnum.WAITPAY.status);
//
//        betDao.updateStatusByMatchId(matchId,OrderStatusEnum.WAITPAY.status);
        System.out.println(new Date().getTime()+"=====");
        System.out.println(System.currentTimeMillis()+"=====");
//        System.out.println(DesUtil.encrypt(matchId)+"@@@++++++++@@@"+matchId);

        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.SUCCESS);
    }

    @Transactional
    @ApiOperation(value = "testTransferTBT")
    @DisableToken
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "fromAddressKey",value = "????????????key ",dataType = "String",required = true),
            @ApiImplicitParam(name = "toAddress",value = "???????????? ",dataType = "String",required = true),
            @ApiImplicitParam(name = "amount",value = "??????",dataType = "String",required = true)
    })
    @PostMapping(value = "/testTransferTBT")
    public ServerResponse testTransferTBT(@RequestParam("fromAddressKey") String fromAddressKey,
                                          @RequestParam("toAddress") String toAddress,
                                          @RequestParam("amount") String amount) throws Exception {


        String tronUrl=sysParaDao.findParaValueById(23);//??????????????????--????????????

        try{
            JSONObject json= TransactionUtils.outToAddressTBT(fromAddressKey,toAddress, TmFunctions.changeString(amount),tronUrl);
            if("SUCCESS".equals(json.getString("code"))){
                TransMoneyDto dto=TransMoneyDto.builder()
                        .hash(json.getString("hash"))
                        .result(json.getString("result"))
                        .code(json.getString("code"))
                        .message(json.getString("message"))
                        .build();
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
            }else{
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
    }



    @Transactional
    @ApiOperation(value = "testTransferBET")
    @DisableToken
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "fromAddressKey",value = "????????????key ",dataType = "String",required = true),
            @ApiImplicitParam(name = "toAddress",value = "???????????? ",dataType = "String",required = true),
            @ApiImplicitParam(name = "amount",value = "??????",dataType = "String",required = true)
    })
    @PostMapping(value = "/testTransferBET")
    public ServerResponse testTransferBET(@RequestParam("fromAddressKey") String fromAddressKey,
                                    @RequestParam("toAddress") String toAddress,
                                    @RequestParam("amount") String amount) throws Exception {


        String tronUrl=sysParaDao.findParaValueById(23);//??????????????????--????????????

        try{
            JSONObject json= TransactionUtils.outToAddressBET(fromAddressKey,toAddress, TmFunctions.changeString(amount),tronUrl);
            if("SUCCESS".equals(json.getString("code"))){
                TransMoneyDto dto=TransMoneyDto.builder()
                        .hash(json.getString("hash"))
                        .result(json.getString("result"))
                        .code(json.getString("code"))
                        .message(json.getString("message"))
                        .build();
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
            }else{
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
    }

    @Transactional
    @ApiOperation(value = "testTransferTRX")
    @DisableToken
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "fromAddressKey",value = "????????????key ",dataType = "String",required = true),
            @ApiImplicitParam(name = "toAddress",value = "???????????? ",dataType = "String",required = true),
            @ApiImplicitParam(name = "amount",value = "??????",dataType = "String",required = true)
    })
    @PostMapping(value = "/testTransferTRX")
    public ServerResponse testTransferTRX(@RequestParam("fromAddressKey") String fromAddressKey,
                                    @RequestParam("toAddress") String toAddress,
                                    @RequestParam("amount") String amount) throws Exception {


        String tronUrl=sysParaDao.findParaValueById(23);//??????????????????--????????????

        try{
            JSONObject json= TransactionUtils.outToAddressTRX(fromAddressKey,toAddress, TmFunctions.changeString(amount),tronUrl);
            if("SUCCESS".equals(json.getString("code"))){
                TransMoneyDto dto=TransMoneyDto.builder()
                        .hash(json.getString("hash"))
                        .result(json.getString("result"))
                        .code(json.getString("code"))
                        .message(json.getString("message"))
                        .build();
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
            }else{
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
    }

    @ApiOperation(value = "key")
    @DisableToken
    @PostMapping(value = "/key")
    public ServerResponse key(String id) throws Exception {

        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(DesUtil.decrypt(id));
    }


    @ApiOperation(value = "test22")
    @DisableToken
    @PostMapping(value = "/test22")
    public ServerResponse test22(String id) throws Exception {

        BetEntity bet= betDao.findFirstById(id);
        BetRecordDTO dto= BetRecordConverter.INSTANCE.entity2dto(bet);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
    }

    @ApiOperation(value = "??????????????????????????????")
    @DisableToken
    @PostMapping(value = "/test221")
    public ServerResponse test221(String id) throws Exception {

        String tronUrl=sysParaDao.findParaValueById(ConstantUtil.tronUrlVal);//??????????????????
        //???????????????????????????--?????????TRX
        MatchEntity match=matchDao.findFirstById(id);
        if(match==null)
            return  ServerResponse.getInstance().fail().responseEnum(ResponseEnum.DATA_NOT_EXIST);
        MatchAddressEntity matchAddress= matchAddressDao.findFirstByMatchId(match.getId());
        if(matchAddress==null){
            SysAddressEntity sysA =sysAddressDao.findAddressByType(2);//TRx?????? ??????
            if(sysA==null|| StringUtils.isBlank(sysA.getWalkey()))
                return  ServerResponse.getInstance().fail().responseEnum(ResponseEnum.ADDRESS_NOT_EXIST);
            try{
                JSONObject json=  TransactionUtils.createTronAdr(tronUrl);
                if("SUCCESS".equals(json.getString("code"))){
                    matchAddress=new MatchAddressEntity();
                    matchAddress.setId(TmFunctions.getKeyStr("A"));
                    matchAddress.setHomeTeam(match.getHomeTeam());
                    matchAddress.setHomeTeam_en(match.getHomeTeam_en());
                    matchAddress.setHomeTeam_zht(match.getHomeTeam_zht());
                    matchAddress.setGuestTeam(match.getGuestTeam());
                    matchAddress.setGuestTeam_en(match.getGuestTeam_en());
                    matchAddress.setGuestTeam_zht(match.getGuestTeam_zht());
                    matchAddress.setMatchId(match.getId());
                    matchAddress.setMatchTime(match.getMatchTime());
                    matchAddress.setCreateTime(System.currentTimeMillis());
                    matchAddress.setWalkey(DesUtil.encrypt(json.getString("privateKey")));
                    matchAddress.setWalletAddress(json.getString("address"));
                    matchAddress.setStatus(YesOrNoStatusEnum.NO.status);
                    matchAddress.setIntoGASStatus(YesOrNoStatusEnum.NO.status);
                    matchAddressDao.save(matchAddress);

                    //????????????TRX ???????????????
                    String trxV= sysParaDao.findParaValueById(ConstantUtil.addressTRXVal);
                    JSONObject json1= TransactionUtils.outToAddressTRX(DesUtil.decrypt(sysA.getWalkey()),matchAddress.getWalletAddress(), TmFunctions.changeString(trxV),tronUrl);
                    if("SUCCESS".equals(json1.getString("code"))){
                        //??????????????????
                        SysAddressTransferRecordEntity rd=new  SysAddressTransferRecordEntity();
                        rd.setId(TmFunctions.getKeyStr("SR"));
                        rd.setAmount(TmFunctions.changeBigDecimalT(trxV));
                        rd.setInOrOut("2");// ??????
                        rd.setType("2");//TRX
                        rd.setSysAddressId(sysA.getId());
                        rd.setFromAddress(sysA.getWalletAddress());
                        rd.setToAddress(matchAddress.getWalletAddress());
                        rd.setCreateTime(System.currentTimeMillis());
                        rd.setRemark("??????????????????????????????");
                        sysAddressTransferRecordDao.save(rd);
                    }else{
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.PAY_FAILED);
                    }

                }
            }catch (Exception e){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS);
    }



    @ApiOperation(value = "11111")
    @DisableToken
    @PostMapping(value = "/testY11")
    public ServerResponse testY1() throws Exception {

        QuotationEntity entity=new QuotationEntity();
        entity.setId("1212");
        entity.setCouNum(0);
        quotationDao.save(entity);
        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED).data("??????????????????");
    }


    @ApiOperation(value = "?????????????????????")
    @DisableToken
    @PostMapping(value = "/testY")
    public ServerResponse testY() throws Exception {

        List<String> list=sysParaDao.findParaValueByIdIn("17,18,19");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED).data("??????????????????");
    }


    @ApiOperation(value = "??????????????????")
    @DisableToken
    @PostMapping(value = "/testJYJL")
    public ServerResponse testJYJL(String trx,String url) {
        checkB(url,1,trx);
        JSONObject json= TransactionUtils.getTransactionById(trx,url);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(json);
    }

    private void checkB(String url, int i, String trx){
        Integer stock=  quMap1.get("Lock"+url);
        if(stock!=null&&stock==1){
            System.out.println("????????????????????????======"+i);
            return;
        }

        quMap1.put("Lock"+url,1);
        System.out.println("????????????======"+i);
        try {
            JSONObject json= TransactionUtils.getTransactionById(trx,url);
            sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();

        }finally {
            quMap1.put("Lock"+url,0);
        }
        System.out.println("????????????end======"+i);
    }

    @Transactional
    @ApiOperation(value = "testTransferUSDT")
    @DisableToken
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "fromAddressKey",value = "????????????key ",dataType = "String",required = true),
            @ApiImplicitParam(name = "toAddress",value = "???????????? ",dataType = "String",required = true),
            @ApiImplicitParam(name = "tronUrl",value = "???????????? ",dataType = "String",required = true),
            @ApiImplicitParam(name = "amount",value = "??????",dataType = "String",required = true)
    })
    @PostMapping(value = "/testTransferUSDT")
    public ServerResponse testTransferUSDT(@RequestParam("fromAddressKey") String fromAddressKey,
                                          @RequestParam("toAddress") String toAddress,
                                           @RequestParam("tronUrl") String tronUrl,
                                          @RequestParam("amount") String amount) throws Exception {



        try{
            JSONObject json= TransactionUtils.outToAddressUsdt(fromAddressKey,toAddress, TmFunctions.changeString(amount),tronUrl);
            if("SUCCESS".equals(json.getString("code"))){
                TransMoneyDto dto=TransMoneyDto.builder()
                        .hash(json.getString("hash"))
                        .result(json.getString("result"))
                        .code(json.getString("code"))
                        .message(json.getString("message"))
                        .build();
                return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(dto);
            }else{
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED);
        }
    }




    @ApiOperation(value = "????????????key")
    @DisableToken
    @PostMapping(value = "/testJE")
    public ServerResponse testJE()  {
        List<UserWalletEntity> list=userWalletDao.findAll();
        for(UserWalletEntity entity:list){


//            entity.setLegalCurrency(entity.getLegalCurrency());
//            entity.setOwnBet(entity.getOwnBet());
//            userWalletDao.save(entity);
        }

        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED).data("??????????????????");
    }


    @ApiOperation(value = "????????????")
    @DisableToken
    @PostMapping(value = "/test")
    public ServerResponse test(String key)  {
        System.out.println();
//        UserWalletEntity user=userWalletDao.findByUserId(userId);
//        try {
//            user.setLegalCurrency(user.getLegalCurrency().add(new BigDecimal(10)));
//            userWalletDao.save(user);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        redisUtil.set("??????",15464,12000L);
//        boolean bn = checkBean(cmd);
//        if (bn)
//            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data("??????????????????");
        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED).data("??????????????????");
    }

    @ApiOperation(value = "????????????")
    @DisableToken
    @PostMapping(value = "/md5Test")
    public ServerResponse loginUserByPassword(@Valid Test cmd) throws Exception {

        redisUtil.set("??????",15464,12000L);
//        boolean bn = checkBean(cmd);
//        if (bn)
//            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data("??????????????????");
        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FAILED).data("??????????????????");
    }

    private boolean checkBean(Object obj) throws Exception {
        String MD5key="";
        StringBuffer  joinStr=new StringBuffer();
        Class<?> clazz = obj.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {//???????????? ????????????
            Field[] field = clazz.getDeclaredFields();
            for (Field f : field) {
                f.setAccessible(true);
                if(f.getName().equals("key")){
                    MD5key=f.get(obj).toString();
                }else{
                    joinStr.append(f.getName());
                    if(f.get(obj)!=null&&!"".equals(String.valueOf(f.get(obj)))){
                        joinStr.append(String.valueOf(f.get(obj)));
                    }
                }
                System.out.println("?????????" + f.getName() + " ??????" + String.valueOf(f.get(obj)));
            }

        }
        System.out.println("joinstr: "+joinStr);
        String md5=MD5Util.md5(joinStr.toString(),"4567");
        System.out.println();
        if(md5.equals(MD5key))
            return true;
        return false;
    }
}