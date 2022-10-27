package com.betswap.market.app.file.impl;

import cn.hutool.json.JSONObject;
import com.betswap.market.app.file.service.FileService;
import com.betswap.market.client.common.UploadFileTypeEnum;
import com.betswap.market.infrastruture.common.response.ServerResponse;
import com.betswap.market.infrastruture.file.dao.IpfsFileDao;
import com.betswap.market.infrastruture.file.entity.IpfsFileEntity;
import com.betswap.market.infrastruture.utils.helper.file.IpfsFileHelper;
import com.betswap.market.infrastruture.utils.helper.file.NonStaticResourceHttpRequestHandler;
import com.betswap.market.infrastruture.utils.helper.file.PictureHelper;
import com.betswap.market.infrastruture.common.response.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private IpfsFileHelper fileHelper;
    @Autowired
    private PictureHelper pictureHelper;
    @Autowired
    private IpfsFileDao IPFSFileDao;
    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    private String storage_path = System.getProperty("user.dir")+"/static";

    @Override
    public ServerResponse uploadUrlFile(String userId, MultipartFile file) {
        //TODO 将file转化为url
        String coverURL = pictureHelper.savePicture(file,"cover",userId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("fileUrl",coverURL);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse uploadIpfsFile(String userId, String uploadType, MultipartFile multipartFile) {
        return uploadIpfsFileBase(userId,uploadType,multipartFile,false);
    }

    //上传文件到ipfs，并缓存一份到暂存目录中
    @Override
    public ServerResponse uploadIpfsFileWithCache(String userId, String uploadType, MultipartFile multipartFile){
        return uploadIpfsFileBase(userId,uploadType,multipartFile,true);
    }

    @Override
    public void downloadIpfsFile(HttpServletResponse response,HttpServletRequest request, String fileHash) {
        downloadIpfsFileBase(response, request, fileHash, false);
    }

    @Override
    public void downloadIpfsFileWithCache(HttpServletResponse response, HttpServletRequest request, String fileHash) {
        downloadIpfsFileBase(response, request, fileHash, true);
    }

    @Override
    public void videoPreview(HttpServletRequest request, HttpServletResponse response,String fileHash) {
        try{
            IpfsFileEntity fileEntity = IPFSFileDao.findByFileHash(fileHash);
            String fileSuffix = fileEntity.getFileSuffix();
            //todo 检查文件类型是否是规定的类型，否则报错
//            String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1);
            //文件的路径
            String realPath = fileHelper.getDirPath() + fileHash + "." + fileSuffix;

            Path filePath = Paths.get(realPath);
            //先检查有没有，没有的话下载
            if(!Files.exists(filePath)){
                fileHelper.download(fileHash,fileSuffix,fileHash);
            }
            if (Files.exists(filePath)) {//从暂存文件夹中读取该文件
                String mimeType = Files.probeContentType(filePath);
                if (!StringUtils.isEmpty(mimeType)) {
                    response.setContentType(mimeType);
                }
                request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
                nonStaticResourceHttpRequestHandler.handleRequest(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            }
        }catch (IOException | ServletException e){
            log.error(e.getMessage());
        }
    }

    @Override
    public ServerResponse uploadUrlFileOther(String type,MultipartFile file) {
        String coverURL = pictureHelper.savePicture(file,"otherService",type);
        JSONObject resultJson = new JSONObject();
        resultJson.put("fileUrl",coverURL);
        /*
        //如果是视频，增加了对视频的截取，并将封面文件放到framefileurl下
        if(type.equals("movievedio")){
            //System.out.println("coverURL="+coverURL);
            //String videofile=storage_path + "/" +coverURL;
            int p=coverURL.indexOf("otherService");
            String videofile=storage_path + "/" +coverURL.substring(p);
            File tempFile =new File(videofile);
            String fileName = tempFile.getName();
            String framefileurl="/framefile/" +fileName+".jpg";
            String framefile=storage_path + framefileurl;
            File targetFile = new File(framefile);
            if(!targetFile.getParentFile().exists()){
               targetFile.getParentFile().mkdirs();
            }
            long start = System.currentTimeMillis();
            try {
                FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
                ff.start();
                int lenght = ff.getLengthInFrames();
                int i = 0;
                Frame f = null;
                while (i < lenght) {
                    // 过滤前5帧，避免出现全黑的图片，依自己情况而定
                    f = ff.grabFrame();
                    if ((i > 48) && (f.image != null)) {
                        break;
                    }
                    i++;
                }
                //        IplImage img = f.image;
                int owidth = f.imageWidth;
                int oheight = f.imageHeight;
                // 对截取的帧进行等比例缩放
                int width = 600;
                int height = (int) (((double) width / owidth) * oheight);
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage fecthedImage = converter.getBufferedImage(f);
                BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
                bi.getGraphics().drawImage(fecthedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
                ImageIO.write(bi, "jpg", targetFile);
                ff.stop();
                resultJson.put("framefileurl", framefileurl);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
         */
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    @Override
    public ServerResponse uploadBase64(String type,String filename,String base64) {
        String suffix = filename.substring(filename.lastIndexOf(".")).toLowerCase();
        String savedName = UUID.randomUUID() + suffix;
        String storage_path = System.getProperty("user.dir")+"/static/"+type;
        File targetFile = new File(storage_path, savedName);
        if(!targetFile.getParentFile().exists()){
            targetFile.getParentFile().mkdirs();
        }
//        BASE64Decoder decoder = new BASE64Decoder();
        try{
//            byte[] b = decoder.decodeBuffer(base64);
            byte[] b =  Base64.decodeBase64(base64);
            for(int i=0;i<b.length;++i){
                if(b[i]<0){//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String imgFilePath = storage_path+"/"+savedName;
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        JSONObject resultJson = new JSONObject();
        String coverURL= "/" + type + "/" + savedName;
        resultJson.put("fileUrl",coverURL);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultJson);
    }

    /**
     *
     * @param userId
     * @param uploadType
     * @param multipartFile
     * @param isWithCache 是否使用缓存文件
     * @return
     */
    public ServerResponse uploadIpfsFileBase(String userId, String uploadType, MultipartFile multipartFile,boolean isWithCache) {

        //判断是否是规定的文件类型
        switch (uploadType){
            case UploadFileTypeEnum.FILE:
            case UploadFileTypeEnum.IMAGE:
            case UploadFileTypeEnum.AUDIO:
            case UploadFileTypeEnum.VIDEO:
            case UploadFileTypeEnum.TEXT:
            case UploadFileTypeEnum.OTHER:
                break;
            default:
                return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.FILE_TYPE_INVALID);
        }

        //文件上传到IPFS
        String fileHash = null;
        try {
            if(!isWithCache){
                fileHash = fileHelper.upload(multipartFile);
            }else{
                fileHash = fileHelper.uploadWithCache(multipartFile);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //文件地址返回为空
        if(fileHash == null || fileHash.equals("")){
            return ServerResponse.getInstance().fail().responseEnum(ResponseEnum.SAVE_FILE_FAILED);
        }


        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String suffix = "";
        try {
            suffix = fileName.substring(fileName.lastIndexOf("."));
        }catch (Exception e){
            e.printStackTrace();
            suffix = "";
        }

        IpfsFileEntity contentEntity = IpfsFileEntity.builder()
                .uploadUserId(userId)
                .fileType(uploadType)
                .fileName(fileName)
                .fileSuffix(suffix)
                .fileHash(fileHash)
                .uploadTime(new Timestamp(System.currentTimeMillis()))
                .build();

        IPFSFileDao.save(contentEntity);

        JSONObject resultObject = new JSONObject();
        resultObject.put("fileHash",fileHash);
        return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SUCCESS).data(resultObject);
    }


    /**
     *  @param response
     * @param request
     * @param fileHash
     * @param isWithCache 是否从缓存下载
     */
    public void downloadIpfsFileBase(HttpServletResponse response,HttpServletRequest request, String fileHash,boolean isWithCache) {
        IpfsFileEntity fileEntity = IPFSFileDao.findByFileHash(fileHash);
        String fileSuffix = fileEntity.getFileSuffix();
        String fileName = "";
        if(isWithCache){
            fileName = fileHelper.downloadWithCache(fileHash,fileSuffix);
        }else{
            fileName = fileHelper.download(fileHash,fileSuffix,null);//从ipfs下载文件到本地
        }

        if(fileName == null){
            log.info(fileHash+"下载文件不存在");
            return;
        }
        File file = new File(fileName);
        if(!file.exists()){
            log.info(fileHash+"下载文件不存在");
            return;
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + UUID.randomUUID() + "." + fileSuffix );
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization");

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.info(fileHash+"下载失败");
            return ;
        }
        log.info(fileHash+"下载成功");
        if(!isWithCache) {//如果不是从缓存中读文件，那么删除临时文件
            fileHelper.deleteFile(file);//删除临时文件
        }
        log.info(fileName+"删除临时文件");
    }


}
