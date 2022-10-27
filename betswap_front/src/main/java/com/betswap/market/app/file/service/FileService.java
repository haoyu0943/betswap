package com.betswap.market.app.file.service;

import com.betswap.market.infrastruture.common.response.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileService {
    public ServerResponse uploadUrlFile(String userId, MultipartFile multipartFile);
    public ServerResponse uploadIpfsFile(String userId, String uploadType, MultipartFile multipartFile);
    public ServerResponse uploadIpfsFileWithCache(String userId, String uploadType, MultipartFile multipartFile);
    public void downloadIpfsFile(HttpServletResponse response,HttpServletRequest request,String fileHash);
    public void downloadIpfsFileWithCache(HttpServletResponse response,HttpServletRequest request,String fileHash);
    public void videoPreview(HttpServletRequest request, HttpServletResponse response,String fileHash);

    public ServerResponse uploadUrlFileOther(String type,MultipartFile multipartFile);
    public ServerResponse uploadBase64(String type,String filename,String base64);
}
