package com.betswap.market.adapter.file;

import com.betswap.market.client.user.vo.CurrentUserVO;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.config.annotation.CurrentUser;
import com.betswap.market.infrastruture.config.annotation.DisableToken;
import com.betswap.market.infrastruture.file.dao.IpfsFileDao;
import com.betswap.market.infrastruture.utils.helper.file.IpfsFileHelper;
import com.betswap.market.app.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
@CrossOrigin
@Slf4j
@Api(value = "FileController", tags = { "/file 文件API" })
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private IpfsFileHelper fileHelper;
    @Autowired
    private IpfsFileDao ipfsFileDao;
    @Value(value = "${file.key}")
    private String keyToken;

    @PostMapping(value = {"/url/upload"})
    public ServerResponse uploadUrlFile(@CurrentUser CurrentUserVO currentUser, @RequestParam("file") MultipartFile multipartFile){
        return fileService.uploadUrlFile(currentUser.getUserId(), multipartFile);
    }

    @ApiOperation(value = "其他系统调用文件保存")
    @DisableToken
    @PostMapping(value = {"/url/uploadOther"})
    public ServerResponse uploadUrlFile(@RequestParam("key") String  key,@RequestParam("type") String  type, @RequestParam("file") MultipartFile multipartFile){
        if(keyToken.equals(key)){
            return fileService.uploadUrlFileOther(type,multipartFile);
        }
        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.INVALID_PARAM);
    }

    @ApiOperation(value = "其他系统调用图片文件(base64)")
    @DisableToken
    @PostMapping(value = {"/url/uploadBase64"})
    public ServerResponse uploadBase64(@RequestParam("key") String  key,@RequestParam("type") String  type, @RequestParam("filename") String filename,@RequestParam("base64") String base64){
        if(keyToken.equals(key)){
            return fileService.uploadBase64(type,filename,base64);
        }
        return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.INVALID_PARAM);
    }

    /**
     * 直接上传到ipfs
     * @param currentUser
     * @param uploadType
     * @param multipartFile
     * @return
     */
    @PostMapping(value = {"/ipfs/upload"})
    public ServerResponse uploadIpfsFile(@CurrentUser CurrentUserVO currentUser, @RequestParam("uploadType") String uploadType, @RequestParam("file") MultipartFile multipartFile){
        return fileService.uploadIpfsFileWithCache(currentUser.getUserId(),uploadType,multipartFile);
    }



    /**
     * 直接从ipfs下载
     * @param response
     * @param currentUser
     * @param fileHash
     */
    @PostMapping(value = {"ipfs/download"})
    public void downloadIpfsFile(HttpServletResponse response ,HttpServletRequest request,@CurrentUser CurrentUserVO currentUser, @RequestParam("fileHash") String fileHash){
        fileService.downloadIpfsFileWithCache(response,request , fileHash);
    }

    /**
     * 播放视频
     * @param request
     * @param response
     * @param currentUser
     * @param fileHash 文件hash
     */
    @GetMapping({"/ipfs/playVideo"})
    public void videoPreview(HttpServletRequest request, HttpServletResponse response,@CurrentUser CurrentUserVO currentUser, @RequestParam("fileHash") String fileHash){
        fileService.videoPreview(request,response,fileHash);
    }

    /**
     * 播放音频 mp3
     * @param request
     * @param response
     * @param currentUser
     * @param fileHash 文件hash
     */
    @GetMapping({"/ipfs/playAudio"})
    public void audioPreview(HttpServletRequest request, HttpServletResponse response,@CurrentUser CurrentUserVO currentUser, @RequestParam("fileHash") String fileHash){
        fileService.videoPreview(request,response,fileHash);
    }


}
