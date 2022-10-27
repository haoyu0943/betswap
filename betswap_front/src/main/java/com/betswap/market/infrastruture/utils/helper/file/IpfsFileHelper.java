package com.betswap.market.infrastruture.utils.helper.file;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * IpfsFileHelper
 * 上传文件
 * 需要更改dirPath 和 ipfsPath
 */
@Component
@Slf4j
public class IpfsFileHelper {
    @Value(value = "${com.tempPath}")
    private String dirPath;
    @Value(value = "${com.ipfsPath}")
    private String ipfsPath;

    public IpfsFileHelper(String dirPath, String ipfsPath){
        this.dirPath = dirPath;
        this.ipfsPath = ipfsPath;
    }

    public IpfsFileHelper(){
        super();
    }

    public String upload(MultipartFile multipartFile) throws Exception{
        return uploadBase(multipartFile,false);
    }

    public String getDirPath(){
        return dirPath;
    }

    /**
     *  根据fileHash和type下载文件
     * @param fileHash ipfs文件的hash
     * @param fileType 要保存的文件类型
     * @return 临时存储的文件路径
     */
    public String download(String fileHash,String fileType,String fileName) {
        try{
            // 实例化ipfs对象
            IPFS ipfs = new IPFS(ipfsPath);
            //下载文件
            Multihash filePointer = Multihash.fromBase58(fileHash);//参数为文件 hash
            byte[] fileContents = ipfs.cat(filePointer);
            //保存文件
            if (fileName==null){
                fileName = UUID.randomUUID().toString();
            }
            String tempFileName = dirPath + fileName + "." + fileType;
            File downloadFile = new File(tempFileName);
            if(!downloadFile.exists()) {
                downloadFile.createNewFile();
            }
            FileOutputStream fop = new FileOutputStream(downloadFile);
            fop.write(fileContents);
            fop.flush();
            fop.close();
            log.info("download ipfsFile: "+fileHash+" filePath: " + tempFileName);
            return tempFileName;
        }catch (IOException e){
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 先判断是否在缓存路径中保留了一份
     * @param fileHash
     * @param fileType
     * @return 文件的路径
     */
    public String downloadWithCache(String fileHash,String fileType){
        String filePath = dirPath + fileHash + "." + fileType;
        log.info("downloadWithCache: "+filePath);
        File file  = new File(filePath);
        if(file.exists()){//文件已存在，直接返回
            return filePath;
        }
        return download(fileHash,fileType,fileHash);
    }


    /**
     * 删除
     *
     * @param files 文件
     */
    public void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public String uploadWithCache(MultipartFile multipartFile) throws Exception{
        return uploadBase(multipartFile,true);
    }

    public String uploadBase(MultipartFile multipartFile,Boolean isWithCache) throws Exception {
        if(multipartFile.isEmpty()){
            return "";
        }
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String suffix = "";
        try {
            suffix = fileName.substring(fileName.lastIndexOf("."));
        }catch (NullPointerException e){
            e.printStackTrace();
            suffix = "";
        }

        // 用uuid作为文件名，防止生成的临时文件重复
        final File tempFile = File.createTempFile(UUID.randomUUID().toString(), suffix,new File(dirPath));
        // MultipartFile to File
        multipartFile.transferTo(tempFile);
        IPFS ipfs = new IPFS(ipfsPath);
        ipfs.refs.local();
        //保存上传文件
        NamedStreamable.FileWrapper savefile = new NamedStreamable.FileWrapper(tempFile);
        //Multihash addResult = ipfs.add(savefile);
        MerkleNode result = ipfs.add(savefile).get(0);
        Multihash multihash = result.hash;
        //System.out.println(multihash);
        //删除临时文件
        deleteFile(tempFile);

        if(isWithCache){
            //再存储一份到ipfs文件本地备份路径中,文件命名规则类似 hash+".mp4"
//            String filePath = dirPath + multihash.toString() + suffix;
//            File desFile = new File(filePath);
//            if(!desFile.getParentFile().exists()){
//                desFile.mkdirs();
//            }
//            try {
//                multipartFile.transferTo(desFile);
//            } catch (IllegalStateException | IOException e) {
//                e.printStackTrace();
//            }
            log.info("suffix:"+suffix.substring(1));
            downloadWithCache(multihash.toString(),suffix.substring(1));// .pdf -> pdf 忽略点
        }
        //返回结果里面获取保存文件的唯一hash，基于文件内容的 hash
        return multihash.toString();

    }
}