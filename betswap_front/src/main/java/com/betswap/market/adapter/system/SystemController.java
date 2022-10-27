package com.betswap.market.adapter.system;


import com.betswap.market.app.system.service.SystemService;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.config.annotation.DisableToken;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/system")
@Api(value = "SystemController", tags = { "/system 系统管理API" })
public class SystemController {
    @Autowired
    private SystemService systemService;

    @Value(value = "${file.key}")
    private String keyToken;

    /**
     * 根据申请记录id 查询申请记录  (当重新申请时 使用)
     */
    @DisableToken
    @ApiOperation(value = "根据字典类型查询字典项")
    @PostMapping(value = "/dic/queryDic")
    public ServerResponse queryDic( @RequestParam(value = "dicType",required = false) String dicType){
        return systemService.queryDic(null, dicType);
    }
    @DisableToken
    @ApiOperation(value = "根据字典类型和字典code查询字典项")
    @PostMapping(value = "/dic/queryDicNameByCodeAndType")
    public ServerResponse queryDicNameByCodeAndType( @RequestParam(value = "code",required = false) String code,
                                                     @RequestParam(value = "type",required = false) String type){
        return systemService.queryDicNameByCodeAndType(code, type);
    }

    @DisableToken
    @ApiOperation(value = "根据id查询配置表(汇率，手续费等)")
    @PostMapping(value = "/papa/findSysParaEntityById")
    public ServerResponse findSysParaEntityById( @RequestParam(value = "id",required = true) int id){
        return systemService.findSysParaEntityById(id);
    }

    @ApiOperation(value = "根据类型查询平台客服")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "typeFlg",value = "1商城客服，2电影客服",dataType = "String",required = true)
    })
    @PostMapping(value = "/findCustomerService")
    public ServerResponse findCustomerService(@RequestParam(value = "userToken",required = false)String token,@RequestParam(value = "typeFlg",required = true) String typeFlg){
        return systemService.findCustomerService(typeFlg);
    }

    @ApiOperation(value = "保存文章信息")
    @DisableToken
    @PostMapping(value = "/saveArticle")
    public ServerResponse saveArticle(@RequestParam("key") String  key,
                                      @RequestParam("typeFlag") String  typeFlag,
                                      @ApiParam(value = "file") @RequestParam(value = "file",required = false) MultipartFile file,
                                    @RequestParam("id") String  id ,
                                    @RequestParam("content") String  content ,
                                    @RequestParam("webUrl") String  webUrl ,
                                    @RequestParam("title") String  title ,
                                    @RequestParam("subtitle") String  subtitle ,
                                    @RequestParam("userId") String  userId){
        if(keyToken.equals(key)){
            return systemService.saveArticle(typeFlag,file,id,title,subtitle,webUrl,content,userId);
        }
        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.INVALID_PARAM);
    }

    @DisableToken
    @ApiOperation(value = "根据类型查询是否需要签收协议")
    @PostMapping(value = "/papa/findParaValueByTypeFlag")
    public ServerResponse findParaValueByTypeFlag( @RequestParam(value = "typeFlag",required = true) int typeFlag){
        return systemService.findParaValueByTypeFlag(typeFlag);
    }


    @DisableToken
    @ApiOperation(value = "获取比赛状态")
    @PostMapping(value = "/findMatchStatus")
    public ServerResponse findMatchStatus(){
        return systemService.findMatchStatus();
    }

    @DisableToken
    @ApiOperation(value = "获取联赛列表")
    @PostMapping(value = "/findLeagueMatchS")
    public ServerResponse findLeagueMatchS(){
        return systemService.findLeagueMatchS();
    }

    @DisableToken
    @ApiOperation(value = "查询最新的版本信息-判断是否更新")
    @PostMapping(value = "/papa/findLatestVersion")
    public ServerResponse findLatestVersion(){
        return systemService.findLatestVersion();
    }

}
