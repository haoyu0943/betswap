package yijiang.jboot.utils.ftp;



import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import yijiang.jboot.utils.ftp.pojo.Ftp;


public class FtpUtil {

    private static Logger logger=Logger.getLogger(FtpUtil.class);


    private static FTPClient ftp;


    private static ThreadLocal<FTPClient> ftpClientThreadLocal = new ThreadLocal<FTPClient>(); //线程局部变量

    private static class FTPUtilInstance {
        private static FtpUtil ftpUtil = new FtpUtil();
    }

    public static FtpUtil getInstance() {
        return FTPUtilInstance.ftpUtil;
    }



    /**
     * 获取ftp连接
     * @param f
     * @return
     * @throws Exception
     */
    public static boolean connectFtp(Ftp f) throws Exception{
        ftp=new FTPClient();
        boolean flag=false;
        int reply;
        if (f.getPort()==null) {
            ftp.connect(f.getIpAddr(),21);
        }else{
            ftp.connect(f.getIpAddr(),f.getPort());
        }
        ftp.login(f.getUserName(), f.getPwd());
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return flag;
        }
      /*  ftp.setDataTimeout(60000);       //设置传输超时时间为60秒
        ftp.setConnectTimeout(60000);*/
        ftp.changeWorkingDirectory("/");
        flag = true;
        return flag;
    }



    /**
     * 关闭ftp连接
     */
    public static void closeFtp(){
        if (ftp!=null && ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取ftp连接（多线程）
     * @return
     * @throws Exception
     */
    public static FTPClient getFTPClient() throws Exception{
        if (ftpClientThreadLocal.get() != null && ftpClientThreadLocal.get().isConnected()) {
            return ftpClientThreadLocal.get();
        }
        Ftp f = getFtp();
        FTPClient ftpClient=new FTPClient();
        boolean flag=false;
        int reply;
        if (f.getPort()==null) {
            ftpClient.connect(f.getIpAddr(),21);
        }else{
            ftpClient.connect(f.getIpAddr(),f.getPort());
        }
        ftpClient.login(f.getUserName(), f.getPwd());
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            return ftpClient;
        }
        ftpClient.changeWorkingDirectory("/");
        ftpClientThreadLocal.set(ftpClient);
        return ftpClient;
    }

    /**
     * 断开ftp连接（多线程）
     *
     * @throws Exception
     */
    public static void disconnect() throws Exception {
        try {
            FTPClient ftpClient = getFTPClient();
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
                ftpClient = null;
            }
        } catch (IOException e) {
            throw new Exception("Could not disconnect from server.", e);
        }
    }



    /**
     * 获取ftp链接常量
     * @return
     */
    public static Ftp getFtp(){
        Ftp f=new Ftp();
        PropKit.use("jboot.properties");
        f.setIpAddr(PropKit.get("ftp.ipAddr"));
        f.setUserName(PropKit.get("ftp.username"));
        f.setPwd(PropKit.get("ftp.pwd"));
        f.setPort(Integer.valueOf(PropKit.get("ftp.port")));
        f.setUploadFilePath(PropKit.get("ftp.uploadFilePath"));
        f.setDownLoadFilePath(PropKit.get("ftp.downLoadFilePath"));
        f.setErrFilePath(PropKit.get("ftp.errFilePath"));
        return f;
    }

    /**
     * InputStream-->byte[]
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws Exception{
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        inputStream.close();
        return outputStream.toByteArray();
    }

    /*
     * 下载获得文件的二进制流信息
     * @param key
     * @return
     * @throws java.io.IOException
     */
    public static byte[] download(String key) throws Exception {
        return download(key, null);
    }

    /**
     * 多线程下载
     * @param key
     * @param type
     * @return
     */
    public static byte[] download(String key, String type) throws Exception {
        byte[] objFile=null;
        Ftp f =  getFtp();
        String cacheFile = "/cache_"+key;
        if(null == type){
            //设置目录
            key=f.getDownLoadFilePath()+key;
        }else{
            //设置目录
            key=f.getErrFilePath()+key;
        }
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient();
            if (ftpClient!=null && ftpClient.isConnected()) {
                try {
                    ftpClient.changeWorkingDirectory("/");
                    File localFile = new File(cacheFile);
                    if(localFile.exists()){
                        localFile.createNewFile();//创建文件
                    }
                    OutputStream outputStream = new FileOutputStream(localFile);
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setUseEPSVwithIPv4(true);
//                    ftp.retrieveFile("1.jpg", outputStream);
                    ftpClient.retrieveFile(key, outputStream);
                    InputStream inputStream = new FileInputStream(localFile);
                    objFile = readInputStream(inputStream);
                    outputStream.close();
                    inputStream.close();
                    localFile.delete();

                } catch (Exception e) {
                    logger.error(e);
                    logger.error("下载过程中出现异常");
                }

            }else{
                logger.error("链接失败！");
            }
        }finally {
            try{
                disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return objFile;
    }

    /**
     * 上传文件
     * @param key 实为文件名，可以为:upload/20150306/text.txt
     * @param input 文件流
     * @return
     */
    public static String upload(String key, InputStream input) throws Exception {
        return upload(key, input, null);
    }

    /**
     * 多线程上传
     * @param key
     * @param input
     * @param type
     * @return
     */
    public static String upload(String key, InputStream input, String type) throws Exception {
        String etag="";
        Ftp f =  getFtp();
        if(null == type){
            //设置目录
            key=f.getUploadFilePath()+key;
        }else{
            //设置目录
            key=f.getErrFilePath()+key;
        }

        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient();
            if (ftpClient!=null && ftpClient.isConnected()) {
                ftpClient.enterLocalPassiveMode();
                ftpClient.setUseEPSVwithIPv4(true);
                ftpClient.storeFile(key,input);
                input.close();
                etag="succ";
            }
        }finally {
            try{
                disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return etag;
    }



    /**
     * 删除一个存储在FTP服务器上的文件
     * @param key
     */
    public static void delete(String key){
        Ftp f =  getFtp();
        key=f.getDownLoadFilePath()+key;
        try{
            if (connectFtp(f)) {
                ftp.deleteFile(key);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeFtp();
        }

    }


    /**
     * 多线程下载
     * @param fileName
     * @return
     */
    public static   Object[] getDownLoadStream(String fileName){

        Long dataLength = null;
        byte[] objFile=null;
        Ftp f =  getFtp();
        fileName=f.getDownLoadFilePath()+fileName;
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient();
            if (ftpClient!=null && ftpClient.isConnected()) {

                File localFile = new File("/cache_"+fileName);
                if(localFile.exists()){
                    localFile.createNewFile();//创建文件
                }
                OutputStream outputStream = new FileOutputStream(localFile);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setUseEPSVwithIPv4(true);
//                    ftp.retrieveFile("1.jpg", outputStream);
                ftpClient.retrieveFile(fileName, outputStream);
                InputStream inputStream = new FileInputStream(localFile);
                objFile = readInputStream(inputStream);
                dataLength = (long)inputStream.available();
                outputStream.close();
                inputStream.close();
                localFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Object[] res = new Object[]{objFile,dataLength};

        return res;
    }

    //序列化对象返回byte数组
    public static byte[] serialise(Object obj){
        ObjectOutputStream os=null;
        ByteArrayOutputStream bos=null;
        try {
            bos=new ByteArrayOutputStream();
            os=new ObjectOutputStream(bos);
            os.writeObject(obj);
            byte[] bytes=bos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //反序列化byte数组 为对象
    public static Object unserialize(byte[] bytes){
        ByteArrayInputStream bin=null;
        try {
            bin=new ByteArrayInputStream(bytes);
            ObjectInputStream in=new ObjectInputStream(bin);
            return in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Ftp下载路径
     * @param key
     * @return
     */
    public  String getObjectUrl(String key){
        Ftp f =  getFtp();
        key=f.getDownLoadFilePath()+key;
        String alpath = "ftp://"+f.getUserName()+":"+f.getPwd()+"@"+f.getIpAddr()+":"+f.getPort() + key;
        return alpath;
    }

    /**
     * 获取 ftp 目录下 包含对应名称的文件集合
     * @param pathName  ftp 文件夹路径
     * @param fileName  查询文件名称
     * @return
     * @throws Exception
     */
    public static List<String> listFileNames(String pathName,String fileName) throws Exception {
        List<String> arFiles=new ArrayList<>();
        if(connectFtp( getFtp())){
            String directory = pathName;
            //更换目录到当前目录
            ftp.changeWorkingDirectory(directory);
            FTPFile[] files = ftp.listFiles();
            for(FTPFile file:files){
                if(file.isFile()){
                    if(file.getName().contains(fileName)){
                        arFiles.add(file.getName());
                    }
                }
            }
        }
        return arFiles;
    }
    public static void main(String[] args) {

        try {
            List<String> lst=listFileNames("/KsrzSysFile/","test123");
            System.out.println(lst.size()+"---");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Record  rd=new Record();
            for(int i=0;i<3;i++){
                rd.set("sex"+i,"男"+i);
            }
            List<Record> lst=new ArrayList<>();
            lst.add(rd);
            lst.add(rd);
            byte[] bytes= serialise(lst);
            String str=FtpUtil.upload("tbl_xz_task-1628650620003.zip",new ByteArrayInputStream(bytes));
            System.out.println(str+"++++++++++++");
            byte[] by=FtpUtil.download("test1.zip");

            List<Record> rds= (List<Record>) unserialize(by);
            System.out.println(rds.size()+"======");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
