package yijiang.jboot.utils.ftp.pojo;

public class Ftp {
    private String ipAddr;//ip地址

    private Integer port;//端口号

    private String userName;//用户名

    private String pwd;//密码

    private String downLoadFilePath;//下载路径

    private String uploadFilePath;//上传路径

    private String errFilePath;//错误文件路径


    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDownLoadFilePath() {
        return downLoadFilePath;
    }

    public void setDownLoadFilePath(String downLoadFilePath) {
        this.downLoadFilePath = downLoadFilePath;
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }

    public String getErrFilePath() {
        return errFilePath;
    }

    public void setErrFilePath(String errFilePath) {
        this.errFilePath = errFilePath;
    }
}
