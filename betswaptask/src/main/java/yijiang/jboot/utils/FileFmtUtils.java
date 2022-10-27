package yijiang.jboot.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;


public class FileFmtUtils {
    private static final Logger LOGGER = Logger.getLogger(FileFmtUtils.class);

    private static final FileFmtUtils FILE_FMT_UTILS = new FileFmtUtils() ;

    private String charset = "utf-8" ;

    private int bufferSize = 16 * 1024;

    private FileFmtUtils(){

    }

    private FileFmtUtils(String charset){
        this.charset = charset ;
    }

    private FileFmtUtils(int bufferSize){
        this.bufferSize = bufferSize ;
    }

    private FileFmtUtils(String charset, int bufferSize){
        this.charset = charset ;
        this.bufferSize = bufferSize ;
    }

    public static FileFmtUtils getInstance(){
        return FILE_FMT_UTILS ;
    }

    public static FileFmtUtils getInstance(String charset){
        return new FileFmtUtils(charset) ;
    }

    public static FileFmtUtils getInstance(int bufferSize){
        return new FileFmtUtils(bufferSize) ;
    }

    public static FileFmtUtils getInstance(String charset, int bufferSize){
        return new FileFmtUtils(charset, bufferSize) ;
    }

    /**
     * 随机读取文件内容
     */
    public byte[] readFileByRandomAccess(File f)throws IOException {

        RandomAccessFile randomFile = null;
        ByteArrayOutputStream baos = null ;

        try {

            LOGGER.debug("随机读取一段文件内容：");
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(f, "r");
            baos = new ByteArrayOutputStream();
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 0 : 0;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[bufferSize];
            int byteread = 0;
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            // 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                baos.write(bytes, 0, byteread);
            }
            return baos.toByteArray();

        }finally {
            if (randomFile != null) {
                randomFile.close();
            }
            if (baos != null) {
                baos.flush();
                baos.close();
            }
        }
    }

    /**
     * 随机读取文件内容
     */
    public String[] readFileByRandomAccessByLine(File f)throws IOException {

        RandomAccessFile randomFile = null;
        ByteArrayOutputStream baos = null ;

        try {

            LOGGER.debug("随机读取一段文件内容：");
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(f, "r");
            baos = new ByteArrayOutputStream();
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 0 : 0;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            // 将一次读取的字节数赋给byteread
            String tempString = "" ;
            int line = 0;

            List<String> list = new ArrayList<String>();
            while ((tempString = randomFile.readLine()) != null) {
                // 显示行号
                //tempString = new String(tempString.getBytes("ISO-8859-1"), charset) ;
                tempString = new String(tempString.getBytes(), charset) ;
                LOGGER.debug("line " + line + ": " + tempString);
                list.add(tempString);
                line++;
            }
            String[] str = (String[])list.toArray(new String[list.size()]);
            return str;
        }finally {
            if (randomFile != null){
                randomFile.close();
            }
            if (baos != null){
                baos.flush();
                baos.close();
            }
        }
    }

    public String readByBufferedLinesToStr(InputStream is)throws IOException {
        String[] lines = readByBufferedLines(is) ;
        String str = "" ;
        for(int i=0; i<lines.length; i++){
            if(i>0 && i<(lines.length-1)){
                str += "\r\n" ;
            }

            str+=lines[i] ;
        }

        return str ;
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public String[] readByBufferedLines(InputStream is)throws IOException {

        BufferedReader reader = null;
        try {
            LOGGER.debug("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new InputStreamReader(is, charset));
            String tempString = null;
            int line = 0;
            // 一次读入一行，直到读入null为文件结束
            List<String> list = new ArrayList<String>();
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                tempString = new String(tempString.getBytes(charset), charset) ;
                LOGGER.debug("line " + line + ": " + tempString);
                list.add(tempString);
                line++;
            }
            String[] str = (String[])list.toArray(new String[list.size()]);
            return str;
        }finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    public String readByCharsByR(InputStream is)throws IOException {

        Reader reader = null;
        try {
            LOGGER.debug("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(is, charset);
            int tempchar;
            String str = "" ;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                char c = (char) tempchar;
                if (c != '\r') {
                    str += c;
                }
            }
            return str;
        }finally{
            if(reader!=null){
                reader.close();
            }
        }
    }

    public String readByCharsByNLine(InputStream is)throws IOException {

        Reader reader = null;
        try {
            LOGGER.debug("\n以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[bufferSize];
            int charread = 0;
            String str = "" ;
            reader = new InputStreamReader(is, charset);
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    str +=tempchars;
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            str +=tempchars[i];
                        }
                    }
                }
            }
            return str ;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * 以每个字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     */
    public byte[] readByBytes(InputStream in)throws IOException {

        ByteArrayOutputStream baos = null ;
        try {
            LOGGER.debug("以字节为单位读取文件内容，一次只读1个字节：");
            baos = new ByteArrayOutputStream();
            // 一次读多个字节
            int byteread = 0;
            LOGGER.debug("当前字节输入流中的字节数为:" + in.available());
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read()) != -1) {
                baos.write(byteread);
            }
            return baos.toByteArray();
        }finally{
            if(baos!=null){
                baos.flush();
                baos.close();
            }
        }
    }

    /**
     * 一次性读取所有内容，常用于读二进制文件，如图片、声音、影像等文件。
     */
    public byte[] readByBytesAll(InputStream in)throws IOException {

        ByteArrayOutputStream baos = null ;
        try {

            LOGGER.debug("以字节为单位读取文件内容，一次读多个字节：");
            baos = new ByteArrayOutputStream();
            LOGGER.debug("当前字节输入流中的字节数为:" + in.available());
            // 一次读多个字节
            byte[] tempbytes = new byte[in.available()];
            int byteread = 0;

            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
                baos.write(tempbytes, 0, byteread);
            }
            return baos.toByteArray();

        }finally{
            if(baos!=null){
                baos.flush();
                baos.close();
            }
        }
    }

    /**
     * 以字节的bufferSize为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     */
    public byte[] readByByteBuffer(InputStream in)throws IOException {
        ByteArrayOutputStream baos = null ;
        try {
            LOGGER.debug("以字节为单位读取文件内容，一次读多个字节：");
            baos = new ByteArrayOutputStream();
            // 一次读多个字节
            byte[] tempbytes = new byte[bufferSize];
            int byteread = 0;
            LOGGER.debug("当前字节输入流中的字节数为:" + in.available());
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
                baos.write(tempbytes, 0, byteread);
            }
            return baos.toByteArray();
        }finally{
            if(baos!=null){
                baos.flush();
                baos.close();
            }
        }
    }

    public static void copyFile(File s, File t)throws IOException {

        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t, true);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        }finally {
            fi.close();
            in.close();
            fo.close();
            out.close();
        }
    }

    public void copyFile(FileInputStream fi, File t)throws IOException {
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fo = new FileOutputStream(t, true);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        }finally {
            fi.close();
            in.close();
            fo.close();
            out.close();
        }
    }

    public void writeFile(InputStream fi, File dst)throws IOException {

        DataInputStream in = null;
        DataOutputStream out = null;

        try{

            in = new DataInputStream(fi);
            out = new DataOutputStream(new FileOutputStream(dst));
            int offset = 0;
            while ((offset = in.read()) != -1) {
                out.write(offset);
            }

        }finally {
            if(null != fi){
                fi.close();
            }
            if(null != in){
                in.close();
            }
            if (null != out){
                out.close();
            }
        }
    }

    public void writeFileByByteBuffer(InputStream fi, File dst)throws IOException {

        DataInputStream in = null;
        DataOutputStream out = null;

        try{

            in = new DataInputStream(fi);
            out = new DataOutputStream(new FileOutputStream(dst));
            byte[] buffer = new byte[bufferSize];
            int offset = 0;
            while ((offset = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, offset);
            }

        } finally {
            if(null != fi){
                fi.close();
            }
            if(null != in){
                in.close();
            }
            if (null != out){
                out.close();
            }
        }

    }

    public InputStream stringToInputStream(String str) throws IOException {
        byte[] bt = str.getBytes(charset) ;
        return new ByteArrayInputStream(bt);
    }

    public String getSuffix(String fullName) throws Exception {
        return fullName.substring(fullName.lastIndexOf(File.separator)+1) ;
    }

    public String getName(String fullName) throws Exception {
        return fullName.substring(0, fullName.lastIndexOf(File.separator)) ;
    }

    public static void main(String[] args) throws Exception {

        FileFmtUtils fm = FileFmtUtils.getInstance() ;

        //获取resource根目录下/test.txt的文件
        //String path = FileFmtUtils.class.getResource("/test.txt").getPath() ;
        File f = new File("D:\\test.txt") ;
        InputStream is = new FileInputStream(f) ;
        try{
            // TODO Auto-generated method stub
            //String fileName =  "C:/Users/Administrator/Desktop/Noname1.txt";
            //readFileByBytes(path);
            //readFileByChars(path);
            //readFileByLines(path);
            //byte[] bts = readFileByRandomAccess(f);  readFileByBufferedLines
            //String str = StringUtils.join(readFileByRandomAccessByLine(f, "GBK"), "\n") ;
            //String str = StringUtils.join(readByBufferedLines(is, "GBK"), "\n") ;
            //String str = readByCharsByR(is, "GBK") ;
            //String str = readByCharsByNLine(is, "GBK") ;
            String str = new String(fm.readByBytesAll(is), "GBK");
            System.out.println(str);
        }finally{
            if(is!=null){
                is.close();
            }
        }


/*		File f = new File("D:\\嫌疑人照片.jpg") ;
		InputStream is = new FileInputStream(f) ;
		String str = null ;
		try{
			str = FileFmtUtils.inputStreamToBase64String(is, true) ;
		}finally{
			if(is!=null){
				is.close();
			}
		}

		//str = "_9j_4AAQSkZJRgABAQEAAQABAAD_4QAIRXhpZgAA_9sAQwAKBwcIBwYKCAgICwoKCw4YEA4NDQ4dFRYRGCMfJSQiHyIhJis3LyYpNCkhIjBBMTQ5Oz4-PiUuRElDPEg3PT47_9sAQwEKCwsODQ4cEBAcOygiKDs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7_8AAEQgCkgI0AwEiAAIRAQMRAf_EABsAAAIDAQEBAAAAAAAAAAAAAAIDAAEEBQYH_8QAOxAAAgIBAwMCBAMHAwMEAwAAAAECEQMEITESQVEFIhMyYXFCgZEGFCNSYqGxwdHwBzNyFUNT8YKS4f_EABkBAQEBAQEBAAAAAAAAAAAAAAABAgMEBf_EACIRAQEAAgICAwEBAQEAAAAAAAABAhEDIRIxBBMyQRRRIv_aAAwDAQACEQMRAD8A8uhkQY8hxPI-kOKGxQEUMijINDEDFDIoguKGJFJBpBlcUMQKQaQRaCKCNaERZErLoCFkQVAUWXRdAVQVESLoCIshCsoQsgEouiFkEolFkoCqJRdBJAVRdF0WkGFUQNIlF0KSLLJRRKJRZKAEsKi6ACgqLougBolBUSgKolBUSgBolBUSgiqKoKiKNhkJKGKFcoGWw0AougqJQagKKoOiUFLolBUSgBBYZQAUBQ2gWiBbRTQbQLRQtoCSHNANEqwloFoc0A0FKaBoYwWjICiBUQg8yhsRUR0ext2MjwMiBHgZEgZEZEBBrggNDIi0MiEGhi4FoZEIIsoIqVEECgkEWi0Ui0FEXRQQZQhCwqELIbEIWQlEossoyiFkosCUFRVFoIiLotIs1plRKLohkRFkLKIkESiyiiFkAhCEAoqgi0gyoshYVQUITySqMW_sN0-lnnarZPuas2TBooKMUnL9WzcibKhoXVzdDVhxwildmF6nUZJXJ1-ZfXJPm2U8a0ZsMn7o7mSUWnuaceaXD4C1GNSh1x_MUYyFkMNBIWUQSiqLIALQNDGgaACiqCaKoAGimg2gWgFtAtDGgGgsLYDGsBoKW0C0G0C0ZA0QuyEHlojYCojYFrsfEZEXENEDUHECIyIBRGxFxGR4CDQaAQaAMsosrFQIEIAkEikWgIECuRsMblxyWIEiHx07lG09_DDj6fmcb8mtJcpGcg96POnTgxUoSg6kqGjYQkCERVFkLIiBdyi-4VZaKIGRFlFlRCELIIWiFgWQhCiEIQMoWQsKlF0VRdBFUaNJpv3jK09oxVti8WKWWajFHSl0aXS9PLXJ0xiUGp1EdPjWPGvskc5xrJ8SXuyvv4DXvl1S3bLa3NLANN7lDe24LiuxGthba4ZpwPri4vujO0M0zcZNApM4OE3F9gTTql_E6u0hFGL0gaIWURAkLIAJC2iqChZTQTRTQAlFlEULAYbBYC2CxjAYUtgsYwGRQUQuiEHlYjIi4jIh2OgNiKgNiQMiMiLiMRkHEahSDiUMQa4FoYuCoINABlZWiykWggkWikWgCo0aSLnlpGc6-ixR0-mlmmvc1sbxjOX_ABojDDpkuveXiipa3slsjE8jy5JSLR0jHjtrWrk-CRy4c8ZKUV9duRMaqkTpLpL0HP6dGUXPC_yMDi4tpqmjpxnPHwy8-DHqo9UajMzliSuUWHkxSxT6ZLclHOxvYQiiyKtcFlLgsIhZRaKi6LogVAUWVRYFkLIECXRdEAlEoslGhC6b4RVG70_B1N5JfLEsiWtGlwrS6f4s_ma_Qw5pSzZHv7Ua9Vmllm4p1FdjMoqJ0kZiRiq4I4Pv0xDUnLiVlNXzINFvpXLAbTew2SXmP5guCXdMBUlZWOdZlEOW3Yy5H_HhJc2RqRt1XEBA7M7xxEnPJEKoshAJQRQRRCEChYIwEAGUwmUwoGA0MYLIFsBjWAwFMFjJAsNBIXRCDyUe4yIuIyJHY6AyIuAyJAyLGxExGxIGIOICDiAxBoBBoqCQSYKCRWRIiIiIAkECggGY49eSMbSt1bOprcvw8ePCuUtzm6ZdWpxr-o0eoyX73GD2bX6HSemNbySC_ql-o2DMsH1VHhd2ao7cG4uXR0dhi3FxGLZGnGqaB90X7dhjKvYC3COoj0z2l2Zz8uOWGfTJG7eLtBTUNTHfaRjLFpzCDM2KWKVMWcrF2hCFpBVpBJFJBIosshAiFlFhELolBJF0ztVF0XwX0jS7DRKDUJPsV0y8F0m148byzUI8tnTz5I6XSqHHZfVgaaEdPh62vc1dmfNJ5Lt8nTGJ7LvJKTk2o35DhiUlbnYpTksnQsc5fVIdBQe82or6vc0LbjGNRyRS8RQvqvsq8sktXGEW4wlJ-Omv9DHl9Q1E5bJwj4UWv8ojTVLL0S4V-W6FT1D6ebb7mb97yQ-bq6u170gJZp5K9sHXhUZta00rNJoz5XWSL8sZB2n2oDMvlf1I1GyTvGgC-IIojNQhCERRCEAohCEEBYQLAFgsJgsAWCwmC0FCwGGwGRQsBhsFoKohCEHkYjIi48jIkdjYjIi4jIkDYjIi4hxAZEZHsLiMQBoJAoJBBoJALgNFQSCBQQEQSBQSAfpHWrx_cv1SUsep27oXhdZ4PwzT6un8WE6Thdu0dIzj-isGRwXk2Y8uOXzPpZixQjNpQ5YyPVCSbhJ_WtjUXOOilta3Ra2Rlx52pU01ZoU2zbhYYnZVES_qX6lhAvcFXGVoZRHGwo5QhqcdPaRzs2CWGVM3L2jY9OWLU1dOjFxRyEHGNj9RpXgyu17exeKN0Z8Wtk1REjY9P1zfejTg0G11ya8ds3LTmUSjr5dF7OLMb0jjLYeNh5xl6RiwTcbNOPFF3as2wxQUUqLMWbm5cdPMbDTyZ0ZYV2HafDHpd9y-LPk5X7tLig4YEnujqZMEdtjBnXw5UXSeSdEa4Lhhi5KTXAF7FZ8yroi-OfqXTZWozfFk43UUCpqXBnvpyNPuR6uGCTxKPVPn7WFbMU1ij7m6-u4nJqJ7fBwZMr8RdF44zl0zzRp9kOU4ubhfuXKAyN6jl4IQXfqz7_4KxyyNdXQqfF5Hv_Y3OMIq2-kRPNibbebJPyoNf7hYW3LvGIKxwf4Ir7RCeTA-7X_lKgbTVp2u1SRlR_DhXyoRPEnt2Lk78iOmUslrJYrc9NL4KLZRhmoQhAKKLKIIymWymQUU-CynwALBYTBYAlUWSzKgYDQxgMKBgtBMplUJCEA8ehsRSGxMuxkRkeRcRkQGxGRFRGRIGx4DQuIaMhiGIVEYuCoJBIFBIoJBJgIJFBIJAoJBFm71bFGWnwylu3RhOnq0smixScumXSvd2N4s71WPD0JVJPbzsalK_F9k3QrHp24buOSPav8Alj441G93XjwbiZUSvvGvzItvD-9BrTXvBSl4eOS_wyPDL-ecfujpHNa-z_IYmLjjyL8TDjCXdhmjIilH6hUETpCjcQUxi3AelHNjqXPBklpJYpe12nx9DRBGjH8tE0zaThhsklyb4rpgkBiwqFMZI1IxbsrI9jC_dlcTdJWqBWLcEZceNuTNMYuIxQrsUQWlsMxvpsqCtBqNIu0DOd9jLlxqcrZpkrEZHSsBMsdGXULpg5eE2a-u-wE4KaphuOVGD6nNq5P-xTUYZuuW31o6HwbaitjNLD8bM_8A40915De1KCmnOUnGPdIVDUyXxJ4IRirq20mzXKNzaulH6GDVZJuPwcEuibpSm1tBePqwmydTqcUZSc4vPlW3So7OXjfn70BjnrM0r-DCMV-J4k1fjd2aY6T91x_E1Wasa2jCMOmvo3y2-4xZJtQksOS6qGJK3-nZfczW4zZcOScN4yb-iVfpZSw6hb_DSXdpP_Qe5ZWmv4cW-VHZR_Pv_Yzz1E4UoSbb7KOxGopZc91FNfc2YotpSmlf0MWDLq8mXqk5qPmUaX5I6SFW0Pcot8lWYZQhCAR8AhPgElEZTLZTIKKLKAFlMtlMAWU0WymRQMFhMFhQsBhsBhVEIQDxyGxFIZEy7mxGIXENApsWMixSYyLIhsWGmLT2DTIGINC0MQBJhIFBIqCQSBQSKCQSBQSDNEjpQaz-mKMXU8ZzopPmSj97Oj6bGHxXBZ4tSW6pm8WKz9MobtfoB--Z8TpSNWWCwZZdeR1J2k41_qFGGGbuUHP7HXSbIj6i5byxNvzFuzRDPKcb-Lmh_wCStBRhii7WGafm0hq6J86br--9FS3Rf7zJfNqL-yQyOdvi39kMjhfT_CccafZRQcoZLqSnXlLZ_mVz2WpyfYK2X8OuGF8O0GdguhkFaTFOLumOwbuimz8cR8Ig44j4RKzRR4SCa2LSI1uGQdFl9FDUqRUgFNC5KkNlsLe5FFiVtDmtmLxLYbWwC2jNlXKNjWxjzRu77gYckmp7MOGTq5FZl0zBxTqRnbTW17epeKE48XRBIansX2f0ZUJkn2dMXDEoLd39R7EW5Lj2_wCSqVNQyy6pQTjzG-31-jAz9eSKhFvpfzb8_dhZHs412v8AIuMYwxqVU5du_wCf1I2yfu8Jbz-VcRltFLxXcZL5mvH6hZJNrpjsvAnFkqXwWrS5S3f5vyRpogklyAsnVJx6Wq8hop_XnujNqqJZRRzEIQhRCiygKIQhBRT4LKfAAlFlACwWEwWFCymWymADBYTKYUJCECvGIZEVEbEw7GxGRFxDQU2IyImIyIDo8BoXHgNEBoNMWgk9yIag0LQaKlGi0CgkAaCQKCjfgrIjRpJuGaLXkVBT_kT-50NLp1KcXKDg12ZvGOWeU0drYZMkoyhXG6asThlOK6cso7cLz-R03h9iV21wzJkxyx5Iyi471alf-Ed4xjekx6ZyknFycX4it_13H_AeONznFR7JyuX6v_YTeo4Wboj_ACxAemlKXUsl3tJPdP7_AF-oL2Zl1-HTxv4cq7vpb_0G49Q8serG01_UqM_7r0QSk24x4S-ZffyvoLWlydayYszuLtuLpNfbj9Cs3ToJxycSVrleC0qM6pyj8SHTkcVWSPP5myEmlvuVkKinyi44lCSaGqEXuFBdPtfIZMxRtWOSoDGOoCRDStgoNIC-nYCa2GUDJAZ5i7HTjsZ3syB-N0h0XaMkJGiD2AuTM-ZU-DQLyq0Bys6uQvHjblZqyQ97Xkvp6YX2RmtFOVUi1OxGbMnPjuXjfVYDJPqW3D7gNdMa7c_oNUaI4-2K8KijNHG1G5v3Pd_7ATtvdGloXJRbqm_sGmWUbrevsK-X5EoJ8JLn7_c19C6m5c-OyFTx9UruvH3JWpSMSljnJzn1u9oxe0f92NTFS6IvdOX5b0NOdaUQlohkQhCFEKLKAohCEAsjIyMASiygBZTLZTCgZTLZTAEFhAkULRCyEV4mPI2ImPIyJHY6IxCkMiFNiMiKTGRYDUGhaCRA1BoUuA0RDEEmAmEgGJlpghxRUHDc0YU3LZ8A4cDm6bo349B7F05EvujUcsqbppTjJL4af3Ohid39DHh0M7d5Yy_I149P0_j_ALHfGPPndmzgpwaZmirfRljJNcOjWo9PexcsaU-vu-TZKW8Ea2bQjJ1Y6kpUvPk1uaXOwD3IuwQy43BPI1FfzJVRI4FGn1dXdNFR0_w5SUePH_NmaMaioUlTRqM2gyYvfGXauUNxqUfbaa89w4RTW5Olp7FQyCV8hNO00DBtqq3X-Bi4IhmMaJToK7Aag4sSmF1gPTI1YuMhl2gFTiZMiqTNzRmzpcpECY8j8cr2EwQUE-sDQgMu0bDi6VC8z9oGSuqVkzRrGH01Jotxv8kFceabm5PZdX9ka8Edvp3-oGoh_E5_-_AzFvBPwRdmpAuO1rd-Ri4-gDX6AKStltRgrCcklsthE52_cwqpNSb-isTk-T2oKTbftVhqDrcKy48fRBdW8u7JIdJNvZCpY2l8xzrcpdF0SqJRhVkIQCEIQoplMtlMASPghHwFCUWUECypFsqQAsBhsBmFCymWwWVVEIQivEIZEWhkSOxsWMixUWMiwpqYyLEp7DIsmw5MJPcWmMiwGJhpikw0wGJhpikw0wGxYanQlMJOyM1qhqckeHyasGbMn1Xsc1MbHLLpq9jcrnli72DLCm3KrNkMuP8AnR5tarLHhm7TvLnSvDNrynR1xycMsdO31ka6415FYcChBc_qaInRzYpxyQjU18vD8kwY1ixNObyqO66-Yrx9kb5Y-tcXQpYk3tsF2qSVJpP8iQT3bVPt_wA8BQxSg5VJuLdpP8P2GqLKzUig0iqotGhdFplkAsuwbJYDEWgFIJSIGRY6PAhDoPagIxWSNocwHGyDM49Loi-ex08YiXtml5JQ5PYqUbBug3urAQ1cmyL5W_HH3Bk6283_AJLUtiqzZsbh7m-ewuHl8vkLV5Ivbundf87AQnGKpmWtGfbjsG4KPNr7uylOcpJQjUezoYowe82r8toDPNxvm_pEW4p8RbNqjh7NN_QGcIpbJoox9NL5aBcn3HSw_wBIHwfN_qFItgTvuaJQ2oW8S7tszVZ6Ko0OCXAEkc7GiiFtAkVZRZQFEIQCinwWUyqEosoIFlMtlMAWCwmCzChYMgmDIqhIQgHh0HFi0MRl3NiMQlDEFNTGRYpMYmRdGpjExSYaZkMTCTATCiyg0w0AEig0w48ikMhNJ2iMmwxSnKkbMPpuXJVzjG_zMazT7M0Ys2SdR6qN4ueVv8dfTaLSabec1OfezoYnif8A21wYdFp7qo_m-TqRw0klsdsY8mWS077FxW4ShQagdGNpFbF_DiWohdJQKjRKDojiFLaJ0hUSiiUQsBk2LexOoVKddwHmiu5Nro_qCUjFLWQXE4r7tID_ANU0ikk8-O34kmB1YuzRBbbnPw6nHkV45xl9nZuxyrYqGlELCAaMuWNZEbGhOWNrgik1cWi5ZElT7lxiKzrZEWEzlVf2-wEs3TG7oTkyVKhE8t9ibbkFkydTtnN9R9a0fpWFZdbkWNS-WN-6X2Rzf2i9ex-j6V5cnuyzfThx3XU-7f0Xc-cajU6z1PWS1GryPJlyS3d_KvEV2SLJbUzymM7e2z_9T8cMq_dvT5ZYLdyyT6b_ALDMH_U_3P8AePTpQh3lGd1-p4laWON7SX_5bIKONOVf27Hf6K8_3x9U0H7V6H1KClpsrf8A5JKvvR0Froyd9cWfIsSzYMnxdPNxyR3tbJ_l4PY-i-rz10YpwUZvaSfEWjnnx3H264ZzOdPXrVwr3TRP3hMzYse9J9rNEcN9_wCxzdFSzXwynIP93-pUsLXci7LbsGrDcGuXZVGapbgLaodIXIw0WQj5ITYohCFFEZCAAUyymUCCECBTBlwEwZGFCCECVQkLIRXhEMQmLGxZHYyIyIuIyIUxBoBDFwGhxYaYtBrYiGJhpi4sNMBiYSYtSDsA7IgLGY4tq2qDNNxQlklSO_oPToY1HJk3f8tGDRSWOFs6WDNqMsqhijXk64Rw5MnWwqNVFVQ-KFYIOMfdz3NCO7y5VVF0WglGysqUQ1EJRCUQpfSTpHdJOkKS4guI_pAcQESQrJPp2NEkYs7fUJBzvUvV8Hp-GWfNkjFRV7934-54n1T1r1j1O1hhk0une0VHaTf1fZv6fqdqeFeseoz1OduWl0-R49Pjqk65l92_7E1Oiyp9Gm07ko9oxcnFeUq3PZxcEs3Xg5_lXGaxeIz-mttPNlnNvhuTYr9yy4HGdyUFdTxN2t_CNnrHqP7nlhBwXul7lNb1_wAZvw9OTTNxdtKr-ng9c-Px2PJfkcskyvpXpv7Qep-mSjkk5aiD3cZpdX_7Lv8AR_qfTPQfW9P6to8efFO726e6-h87joIywThjjLZ79Q_9kdXk0HrOTT3Uci6oo4cnDjrp6eH5Ny6r6z-GwkZ9JmWfD1LtsaEfPs09yNCpq0OBlG1RGpSOmkIy30ttdzVJcfQzamSUdw1HGnJqTETnSbH5lU2c31XI8Hpepyx5jjbMV1j5l-0Gvn6t67lnKXVhxeyEfFdzoej-gan1JfF0rwKSyrH0TlXU3Hq8cUmcRqK10lJ_N7jrYs2XBp1iwdaccqzKcOzUa_1PZwSPB8nK_wAcz1qOTDjz4skemeOXTJcq_p9AdBqXlxRcl7kq-5qzxerv4i6pZLcpeWDp9FHS4YJ39W-EeyYXe3m85MdV09FHJKUHKKcXd2b_AEJ_B9Xy6eXttdW3KZm9Nh8WcY576vmj5rzQfoKlrP2hy5YbpR3f5nDnx6dPi5f-n03Radz06qTdeRqx9Jp9Mwyhp9-6HTieDT37YekCUTVOAmUSLGeSrshUq7GiURUo12JY2Q0BKI90BKJiqzyVAhzXuAboxVUUWVYVRVk6geootlMpyKciiMBluQDkBGUwXMFzIomCwXMFzIorIL-IiBXhosbAzx5NEOCSbrrbo-EbGxx2Vhi5GzHhvub8WPs0THE-R0cE_Bpx4N-TVDCq33HgzeZgjp5vsOhpX3VnUhpYjo6ePgv1sfc5K0n9IcdI-6OxHTR8Do6ePgfUfc4i0X9IS0X9J3Fp4-Algh4L9TP31w1ov6RkdG-6O4sMK4IsEfA-s-6ubDTTo7WiwyhFNsXjwpPZGvBB9aNyac8srk2QGFRiMUTVYCkNhEigNjECKISiWkWkUD0k6Q6JQUtoGhrQEkAqcbOZrNk_szpy3ObrcTlG12LOqPJeiTm_TJQrqlDLki19ep2adDr56TVZMk5fDShXSpW-U6fZLb7mHDmj6T-0OXT5ZRji1r-Ljcv5tk1_qatbolmzSlGXXFcwqqf-p9Liss7fC-TjZlXl_wBsc-f1X0_Bgx6fbBnlNSV9UlKxvpeGa0vTKLbq5Uufp9DqvRQyQ6uiKcn8tU4rsTDjjD4eFwXU48rbjZnqlxnp5MuW5YzCiwYY_BcoP2TVX3T8Vycb0ucYftBjhjb3U7t_Q7OszY9HpWltt7focT9m4y1fqufWtfwsUXG_5mzjyent-JjuvpvoOpc4fRrdeGd5I8l6PH-FDI3u31f7HrMU1kgpLho-Xn7fXFRT5oJgM5tAmYNXJOOzs3ZXUGcnJNdX07BYw5nczB6pj-P6ZqcVX1Y2v7HWmoz3oyZ8HxMOTH_NFpMzY64vjesxuPTlv3QSUka9JqoyjT3b_F5Ojq_RMy1eTGqjPqdRlwzk6n0nV6N9fwZJP8PK_U78WUntw5uO3uOpjw0-hS3e6VbGyWnxLHGDn-Jb3b_sebxa7Ph6k8eS2qprg1_H9Q12oX7vpskm1SR7ZzSR83PgytbvUNTi0eKSxZGnwmuyPVfsH6Blw4HqtTDpnmal-XYw_s5-wubJljrvVn1u-pYkrr7n03S6fFgxR6IpbHl5c_KvbxYTCHwiscEqAnKD7hSknwxTRwruGSTWwiSXdpDZ_diZf83RlqFyqxU1Y2ewtslbZ5qnQDHZEumzOzDQJi5LcaxckYrUA0D0sbVhxiixKzdFgvFI2_DRbxx8GtM7YHikC8cjoOEfAEsa8DRtz5RkuwuUX4N04bipRM1tjaYMkzW4C5QIMrsB2aZQ-gDgZbjPuQd8MgV4FcmjHwZ0aMbLj7bybtOdDEjn4HR0cUlSOzzZNWOJqxxM2I1Y2jUc604-B8UJgOiaYNgkNXAuIaKDQSBQSLpkSDSAQaIo4Lc1aePvQiCNeCO1mVaooZFAwQ1IoiQxLYFIYiCFkLKJQIRQULBlwGymAloy5Y3a8m1oVLHfYDyn7QehQ9V0bx_LOFOElzFrho8zp_VNT6Nklp_VML65tL462xz8b1t-bPpWTBvaRztX6Jg1iccsVKEuYtWmdcOS4uXLxY8nt5d-s6KWmz54NR6bUfrXc50vXMK076l5kmux3cn7A-mSyP4eF414g6QeL_p_6dGXU4Sl_wCTPVj8iPnX4PbxTyar9oNQoae1hSqU-0fP3PXekegfA0kMMF0Y1XU380vNnpNH-z2j0qioYoRUeEkdKOmhClCCRy5Oby9PbxcXhHPw6ekoQWx1NH1Ri4yLhjS7UHD2yPPa6msAJgK-5lQ5lcGcnU425tpbPdHWyfIzBnjbDTnTk8aba2XLFuafBoz0ovq4oyRjU27bT7VwZrpi5-r9Nx5svxEk5Vwy9Pixwfw88E0_Jtnjk6qvzdf83L6PiRUm9-4jVu4WvRfTsjcpabHJPn2nR0fpGkw-3FihBfSIOmW8ToYmos3tyrRCEYKoxSLb2AeSgOslrMH1UBKYDmU2RpJ2xL2Dl92LkjNWBkxUr7DJASI2VNuhLHSruxT3ZmtBa2AaGASZiqpBx5FhplxKYQEs6sowWEymEhUkJktx0kA0c60U0LkhziLlEmlJaAcRrTAaJY1KCiEZDLT5xF7jY5KEotiXTtZttxalLk34NUl3OHGzdp03RZn3pjLjmtu5j1UdjXj1MUcjFBukjbCDXJ3leex1MWrTu9zTj1SfO5yscJdkascWXbHi6UdREasqXJgxwJmlKLpEuWpsmO7p0Vnj5CWePk4vxZ3yMjlnXJwy-Tq6dJw2uws8BkM8W6OHc29tzZonJNdac5yd0uxcfkeV0mXFcY7mNWjfiW5g00ZONt8nQxKkehwPihiFxGxQBpBIpBoCEIQCEa2IQAQQy6QC6K6RlEoBLj9AXBD6QLiFK6F2Iosb0k6QAUQ-mi0gqDNDQNbh0BRUESiropzArJ8piyq2acsvaZpysNRjzxTxuzmrKo8-Tqz4b8HL1eNRm6VdLM11xNcv468DehKdr9DPFqcVK7ZshuZhUgmpWlZqizPdDISNMn2C5ET2B37oIvqXctuwGA5NcDamMXJstTb5IyBbYFjJIBxFaJyCuB80Ja3OdaQCSGASRFLZEywGZ3prRqexPiw8mXJklF0mZZ55pmbzyXTU49ur8WHkp5YeTjy1k49jJl184pybJ_oanBXoXkh5Fyyw8nlp-sTX4mZ5esT_AJmPu21PjvXPNDyLlmh5PJP1ab_GxUvVslfOx9rX-Z66WWHkTPPA8hP1fJfzMCXquR_jYvLtZ8d656iHkh47_wBTyfzv9CGPsi_Q5iLYCD7m6QcFbSN-nSMEDoafsZn6ay_Lr4jTBKzNiNUD1R4qfjVGjHERjRpxorna0YooTqUrH49hOpMZ-lw9kYl_ERpUV4EYl_ERpR8vP9PZBQj9DbpsbU9lZlxc0jVDLc_hw4XMjrw-3PkvTp4ZqNLH8y7m3CnTbMmkxqMeDfBVsfUjw0yKHJARWwaKiwkVRaCrIQgEIQgEIUQCyFEAspolloKqiUEQJtVEIQAaJRdEoqBaM859Mbo0T2OTn1-llOoZ4tqVUGsZunTyTa5MeTJOUbTprgZ8Vz8X2i3uZpyDpML_AET1CjFuS_Kzy_q37X6LQ65YMrn1N02o7L8zu5pexnzz9pfTMGT1WXxdVeZ_gStr7-DOVdseL_j3ei1GPNii8bTg1caOhilsea_Z6Osjo8WPLSx44KKpU3R6THFqO5mOec1dG3YUZAplN0aZOjkCTUuGZ0yfEa4JaaaJJina5RXxJeURyb5IaSL3DFPqT4YUep-fzC6FsU6rYvpfdpE6V3kihM1uLcTRKOPyLlHGu7M0JKaDcoriN_crrb7IypMovshc4y8DpN92_wAhcmzNjUZssL7pfcy5YV-KP6m2Tsw5keLlnb0cd7Zcip8p_ZmDOv4bNk2Zc7_hs4R7MY5OdXIzNbmrN8xmfJ1xrSktheRbDWxc-DWwhRLcUEgWXah6V4IFZCbViTGdhMRh6a8UOidDTdjnwOlo-UZn6ay_Lq4uxpiZ8XY0xPVHhyaMZpxGeC2NGLgrm0wWwrUIdHgVqDGfpcP0RjXvNCEQ-Y0RVnzMv09kHjl035Z0tHplFJtfkZdNiafWzr6bHKKt9z18HHvuvNy566jRii1uzVBCoRrkdFnveYaGRBQaAtBIFBIKhCEILIQgAvkotlAQhRALQQJHIKIgIS4CLIQgEIQhQvLjjOurdd0cL1H03HljKUsMX1pp7HfkrEyxqUm2Fxtl2-dQ_ZPDPMs2WeVZsbuE1N9Ufzs7-NSwaTH1zeRqPunLl_c7Oo0sZNtPpe_Y-d_tN6_rNBqJYHh9vece6JXpwy8q6Gr_AGg08NNk-Fni8_S-hLs-xxfQPRcmvzfvOWcpW7nJveT-rPNei-ja3131SOnhajN9WSbfyq7r7vsfZ9F6Zg0GkjjxRSUIpbGdbbvJ49E6bR48GJRjFbDOk0Ne1oBqiyaefey6BcXJjbopulZVB8N0LlCfZhPM77opTf3OdWF9Uo8oOMm-QnJvsn9yIirUG_xMZFUVHgK6NIjBtFOW5RNi2xbDopxAU0DQxoBkC5C5rYZIXJ7GcmoRLkyZkbJcmXMeXkm47YXV252RbmTP8jN2V0zJlcZbPY8utPbjXHzP3Mzt7mzUYt24SUmuyMU1TOkjaWLky7FyZRRGUUyqqyFECsaGLgUuRiPTXih8OTpaPlHNh2Olo-UZn6ay9Ovh7GmPJmw9jTHk9UeLJqx9jRjM-PhGjGVyrTETqB0ROo4MZ-lw9kw-Y1QXuoyYl7joYIL5mfP1vN6rdRu0sPlOrjVRRh0iT3rg2xZ9Lix1i8ed3T4sdARDdmiB1YMiEuAYhIItBAkQFrkLsCi72IqNlWR8kAoojBCiIREAhZCwKCKIAYLICwi-oGyWU2kUFYPILyJIRLNJPYNQOrnGCaex5D9oPTsOu02XHPHGTS58_VP6Ho80pSyNyd-Dieq6lKHSnXUjNdePqh_Z7BHTaTCscVGlT8t99z0qmnFbe19_B5H0jUdOSWNbJu0elx5FONobOTumSSQt7qi3uUibc1qEQpY4UD3SKlGdOnsVpTUV2T-4PTGXZIpxaKSa5MqjxVw0ymqD6lHmwJ5YJbtfkRRIqU1EV-8w4jJX4ZTn17Tg19UTa6F1XK92FFi-i94S_LuXHq4ZEOQLLSLo3ImymgWhriC0E2RKIqSNMkIkYrcrLkdGfJwas6Mj-6_JnnzuunXFiz92c_OvadPOtzn517WeWx7ML05GZ1JiXna53GZ4-9mOZuOw5Sg91t9BUlTKJ1dmBRQTa7ASCqshVkCsa5GIWuRiPTXhh8Ox0tHyjmw7HS0fKMz9N5fl2MPY0x5M2HsaY8nqjxZNWPg0QM-Pg0QK51piJ1DobFmXUyMZ-jD2rH850tN7mo-Dl6eLyZKR2sMVCopHl48LcnbO9NuKo7I1Y-LM2LG-Wa4bH0J1Hkp0B0RERseSsnIJAxCQVZZRYEIQgVGymyNlWRVN0U5EbFybb2X-xlTOoJMyZNRjg9n1SfLXf6FfvnnZdle4lG6yWZced5IptUPjI0DLugbLCJdlMtyiu4DyRAjAbJKe2wmc-koKT7ct8CpzhGVWZ82pcJ-122ufBkyPJk6afDsm2pCvUNd7uiKr_wC6OJqMMtVlSfC4Ox-7TYSwRiSuk6c7T6D4fT58nUxOUI0g8eO7-gccdELdihk29wapqxTj9C4ypUGTinJgdewPxEuSWg-SSj7bEZMzhdLj-4K1FQ6lNOH4o94-P17E21o6Ul0tK2u8q2i_9vqjn6iU47ZFOP8AJlx9_wDRr-43LleKSlFuMHupxVp_88MtNZG-hQjKW8sdeyX1S7MzaMk-qWNZMkVKC2-Nh5X0a8_en9SdOXD_ABcU458XeUeV90P-A4T-Jgk8eaPMZS5-3n7MDoxufVT02ZfignX5pbr_AB9DDWzsWVTj1RY-LM_QoSUssVGT_wDcj8s_vW36foPUZRrqXPDTtNfQ3GTkXQKexfUdWUkgGW3f38ASf0IipLYzZNmaLFTXUmYrcZcm5z9QumTfg35E0Yc8Ou0nv4PLydV2w9kZXspeTHlipWP3it9zNn3dnn3t6senJ1WPpkznZNjq58iU23wc_Oot3Hg1HeMrKLfJRVSym9gWVYVZCrIFZUGgEEj0V4Y0w7HS0fY5sOx0tJ2Mz9N5fl2MPY0w5M2Hsaocnqjw5NMOw-AiHYfArmdFiM2OU3UVY9LYGTaTSfJnKbhKZosPwt2k2dfBiSqb3vsYdHD5bOrjhf2RMMdUzyHBGiKoCKXgNHdy2ZEbBbpi4jYhTEECuAgLQSBQSYEoukVZVkEYLQQEppBQy23fBhz5ur2Q2iM1Ge_ZH8zN09Ukt_yRhQNfW2x2LTOSUpOvtyNw4FGV9Sk1xXY1JALhiqqHJUTYlmhCpTaCoCaAFzTf07gua7bgvknH6ADLI3wJm5PkZWxWyDTN8JSdsYsSXGw1JNjYY4vkDM4UgZR8o2uNcGXI_dQC1BUqRHFUWmRhSZbMF7l5Jrf6GecpU3EzVg790vpFi3L8D4mv79gYTblTTuWyvyws0Kn0rmD580Z9gE24fDl80eI-Bfug-qPPDT7x7ofOD6o5ErbXu-nj_n0Ba9_STS7SoY4NwTnp5up45PeL8fR-GRY3BKUJdWJvZ1un4f8Av3Dx3Bu94v5o-RsIfDk5RfVGXn8X0GgC9-2Td-QJQfV8PKlKNUpePs_9GPyYqVx3XjwVG0qe6-o0bJ6cmJNwlcH2rb9OwyD6U4wdJ8wnuk_p_wAsPpjzH8_IHSna48rtflFgYlsSSJjlS6Z2_wCoKSrh2joyU7QLbfIxqwaJoAxb2G0BJGVZ8sb3OZn2yNrZnVmZM0djhyY7jrhdVysr24MWd7HTzYriczWYpSTSPJ4vbjZXJ1HU26V0ZZSptDM_Vjm-1maUrLHaAyLfYU3QTlbBkzahKIUUWQAhFJQxC0MR2rxQ_HydLSdjm4-TpaXsZn6XP07OHhGqHJlw8I1Q5PVHhrTDsPgIh2HwKy0RChh9_uJjW1m7T4m31PYaZ2fpcCSTo3RikthMNlVDobmpNM2iSCREgkjTI4jYi0MiFMXISAQQURCiAXZLJYDZkXKVCMk5NUkMcXLgvo2CscMEm92OjC37Ft_M-RvTbCUCKigoqkDJ1sMoS93bAJOw0hPWkMhK4lBt0KySvZBPcTluqQAXbLkXhwy6bbomZdMAM05t3GHNgKcu4cYdCt8sGS5ZlRwyJypD1NRW5j06c8sv6XQ6UX1V4KlHPK62M8pbWG05dT8OhXTbryVQ_EovrU43Fipxcbvt45JjS2muXzXBNqXl2ewhxd2zbXuriwZab-UlXYNHjU88b7bkUXOdSXJp0mNwnNv-WgErmvoNFopR6ccfsZpY6d2b9VH-FCP4ox_1ZmStbkCox2obDbZ8dvoClvwH07BVu4b_AK_VAyhW63i1Y2FSg4S57MGOz6XwELjtuXKF-5Byh0slWgA-_AXHPDJV_choVKNbrdANDItMGUQFONgSGi5oikT5Zmyq7NUzPPe13OeXpvFklEyZ8XVbN047GfIrTXk89xdsa89rdGp20tzj6jTzgro9Nmi1LdHP1MItPYxp6cMrrVecbafBGzTnxQc2k6M8oVw7K67LbKbJIFugqWQqyA2BDELQxHWvJifDsdLR8o5sOx0tHyjM_S5-nZw8I1Q5MuHhGqHJ6o8NasZphGjPi3NOONmmG3Ak5JHQxY3zdGXTY6ptG6DNRimQQ6AuPA2JUGlYyKoGKCAtBxWwMUNigLRZCwqUSi6IBTBSDRRBEiWSyUFUlYROCm9gJJmbJJt9KGydi4qmRVKFj4QpEihiWwSgaFtbjWxbYBJ7GbPLqnXZD7M83s13YVnbcpUhmVVjiu1Exw9wWo4j-hFDocaWKc-8pDJQt2Fp4dOBLn3Bte57djSVnjFNuPdi-mm_oNj_AN9ElCsj-oGTNHqgsr7upoWoKLUo8D3PolKLVqropQSnKUfllwZUagpchRwtcOxnR0rYuMuh7gVHH0xoqOKCd0NlNUUuLATnlWaUVwuDNLZmnLG5WInEihSsOtisauKGUULoNx6oqXjkjRcdiAX7o144KQco1ugGERopqw69v2Basq7LqnsW90XQK4aChkBJWHLYW9gpOSJmmqkzZk4MmVVFmMouJM1aZkyrlGptRW4nMk1a7nCuuNc7URXQc3Jjk3aaf0OvlxtXGSOXqYzjK4PjlHOx6Ma5GpwyTbcLOflcUuGn4OvkyxyKpN2c_Uxq15I7SsM5LkS5WMyR2EyY01sVkAIE2JBoBBo61540QOho-TnwOhpOTM_S5-nawPZGrG9zHgkasbtnqjx5N-nVpm7E0lwYtOmkjqaaCW7Vm45ZNuGNRVj4ciIsfjNsHwHQQGNbDURBrgspBAFFbWGgYrYIAyFIsKhCEAshCAQhCBVNgyYTYEmBVEityJFkUSC7C-oLqCIxT3YyT9ovgCSdIS0OluqAlEKGKpWDkXVX0GVtROmyKPEqxL7lvZv7F4_-2iNFZrNKP8ZUOnDbq7gSX8ZDOrZ_QDnareDklTJpX1Xjf5BZ41FxYOhqWpbXYit8VsKzqoD8btS-7_wZ9U6gvq6ADqY3GnKItK0jTjilAKVOFCMkTXkQiaCbZ4qmMrYtR3DSIFtFDGgaC7QBqmMcfbYPKAFFVQXDIwpbQNDJLewJIAJraxTY1q1QmW0qKqp7ozZVao0SlaEzRjJYyZYe0zNtwcP0-5syq6MuSPc4V1lY_jRbruZ9TpviLrh8yD1kXHJ8RLaXP3FY89rpvk513xcjV4XL38PucrM5QbU3dHpNVg6-pxaTfZ9zg6mHX7ZxpojvjXMy07pmeRqyRirSRlyKpUVoNkKINBqDQ5af-kJaf-k15OMx0qJv0rqjGsEkbNNFxqyS9mU6dbC6o2YnUrMGF8G3C7lfg9Urx5Otpd0dXByjl6f2yUO_c6uKlE3HDJqh8yRphGzPhXdmqGxtzp8fag1uLjuOigQSQSRSQSQUa4LKQSAshCEEIQgFkIQKohCgqMAMBcgEinwEiBS1yEimtwkiIqfyi32GT-UU-UgDRGi6J3oKFoiiE0RIC4qqRbVFFTlTj9WAuS_iX4KulL6lydNsGrAzajf3eFwT06NLJP6h5o9UH-hWL2abEuep2Qasb5Xkx6uTcsca7mpeTHnfVNfQDRijbRpfG3YTgX8OP0GZJNQ2Ap7img47luICVHcLp2CrcJRCldJXSM_E0C-UwAfFC3sOYtgC91YKZfYG6ZAT3FSQxO0BPYEBwJyqtxt2Bk3gytEdwJ8BgTMVqETRnmjTNCJxOWTTJnxqWGSfZWcea6JNHdlttV2c7V6ZTTcO3Y52O2GX8c-WZRdye3ky-oYo5sfxYNNrwKz58mFyU1S7IDTauEpOPF8GXeORqMbttGPI237jqauLhm67uMvoc_UpWmiOhBCiAdpBopRCUTO0FFJ8jIpFRiMjAbLNmQk0dDDPpVvcx4cSctzRKS6qXY3M7HK4x09JljGSd7Hb0n8Rve0cLQYG4rqVuT2R3sbWnwxxraUj08eVvdeLmkl6dHG6pGnGusy407r8jfhj0Q-56HmMjGkGikHFAWg0Cg0RRJFlIsghCEAhZCyiiEIFSimWDJ2FUTpotFgUQsgFFoosAWthTXvQ4XXuIDBr32HHgpgR8Ar5kvoE_lYFboKIDL80PuG92DNbr6BKVlfb6khugMtuYeDlgBJdTryVqKxvFFcJDXH3CsyucG-yZFNb9teTLKHuofKXC-gUMabUpIA8UenGkypyTdLsFKVKgIxsAoqkXREWAC-egpOiltKwW7Cg_E2Wym6BcrQF-QJcMl7lvgBQDYU-V-f-BV2FEpUyNp8AyBUqIKft_NlS3TRc3cPzF3RVLf8AgVJ2OlV32FzVK-3YxWoTMVJWh7FSVHOtM847mScH1P7m-VPZmbLHdmdNyuB6rpVKLdHn5Rlin4aPZ6rH14mqPN67TO9kYsejHLpzZ5eqPS90Ys64H5IuEqYjO7bRHVndEKogV3updhsIdSszRQ_HlUFXJz0h0YNBR2YCzKXYaqW7ImxxyOPA3HP3KVCI1LZG7BCONRqNz8eF5Kza62hisc-rJs328HW0MXll1y36TkaKM8mocn-i4R6PSQSTSXB7OKdPBzXtr0-Pe2ak7deBUajBBQdnd5z4hpiojYlQaCRUQyVURZSLSMiELIUQhCFEIQgVGwUW2RIKhZCgLKZbKApllFoCmimgmimgLRGDYVgVWwLW4wFoAP8A3aLmipxrIpfQmTeBkJyK2XiVNkaLvpi2BUnTByK6fgknbJlVJUFDFOWRGh7CdN1Nvq_IdP5gEufVk6RkeDM5KOZxS3b3ZrjG4oCUSXyhcAzl7WAtyAk7IU5BVP68AsjZTYAylQSdxsXMuEqVAVPlfn_gQmOybNvtQhSJtRPgU3Qx7_oJb2sgZzBC2XftRQULdpoDmHT3QbQtuiVqAFyiHLZlORhoiSoRl3ZrkjNmW7ZmxYyZKcWvBxdfiUJ9bdJnXyPpfUu5i1Kx5ocWu5zdsHldZjdyb5XBzsu7s7WsxPrdb0cbPvNkemEOVEBb3IFddYc3839h0cE6NSQaRx86aJxYpJ3LsP6G3uEkNiibNBxw6XZswO5JiEjRprnPd2ax7rGXUd_07GopKEfud7Tw6IW-WYPT8LUOqXBvUrPo4TUfK5LunJ2xsRONGiJ0czIjYi4jUAcQwEGRVkIQgshKJQEIQhRRCEIqmiFkKqEIQCMhGQyBZEWyUUWyEIigJIVgyN5JwlzyjQ0ZlBrU9XajI0cMjI-xfYoGS6l9QGrVDQXuQKceCsm0RlAZF7AFLdryHONpWUlugpu8bChwv3uXZKkFkdbi4PpivqHPfYBGLHeS3uborYTBUMT2AknuKyS3oKT7i5u3YAMBhsBhVEaLSLaAVLkoJ7sHkCZN8b-xjWxskvYzLJUyKLsKl4GLmgGiLFSe9eECmWUgLe6FZF7RhTVijNJ1GwFKx84mCc_hza8HO9NztquxWRdwYZVKNoqeS-xLdtaY82O20ueyOPkjKM2ltJco7uWPU0zi-pdWOfxcaqceV2MO2Fc3VxjP4jjyjzWeac3R6fJnxzwvem0419Wec1WDpk3FccmXfFib34IXRA29TEZETFjFI8ymxoNClbDiBrxQhJPqZ2fTNDBVly7Lsjj6KDyaiMIrdnejk96x442lzv3O3HP68nLlfUdaMtx-PcxYI07s1w4Pfj3Hgy6a8XA-CEYY1DcfDk2wbEYhcRiAJBoBBoiiIQgBEIWBRQQIFEIWRVFkLApkLZOwAkIQirIQhRZRZQEFzXMhrW1gS-VkFRVwQSRIqopF0UQANgsAaKkrjQaVlNAJqinvjYU0C9sQUrwGmLju2GuADT3GNpREt0HF9UPsALe5TLfNgsAWwA2iktwLSJXuSDigWvdYCpRpsBLcdNC2qVkVGrRlyQfVwa4uxeWPcKy1TI6qy5qnYPaiKBlPYJgyJVirIUWQoJbnO9Sxe1yivclZ0pIVmxLJCu5Mp0Y3VebXqHwItq1FtdR0Y5I5IqUHcXwzkepaSeDPLa4039GL9L9SjBRw5Nk3UXfH_wDDj6enW5t2ZZUq637XspfU5-vxObq6UuGPyxUpyl-GSqX0ku5hlqpY08c4OUFK20-3n7gxcPW9emd8Jumc7LKM7t1Z3tXCOohKMZU35Xc81nhPH1QnGmiPRizShKMqqyF_FkQjb1UcaoOMYrk4T9Qzf8ZT9Q1K5dHDwrNyegUVY6Eca5R5Veq6hZkrOpjz5skbTHhWfLb02g6MbnOC91Un4Z0Y54Yfbhdtcy7HlY6nLh0zipe97o6foeDNn_iambWOPC8nfCfx5-Xq7eqwfIjZjW1mKD6qjFG7EqjR7cZqaeHO7a4fKhkRMOKGx5NsnIYhURqIDiGBHkMirIQtAWiyiwIVRZAKIQgVZCEIIR8EI-ABIQgVZCFgQqiyAQFrYIpgUgkD3CQFMFoJlBFIkiIkgpbQvLtAaxWZ3EKzrZjEAhi4AqS9rZWCfucX3Cl8jAxbSsBrBDe7sEASgnsCnYBwRJLuSPIclcaARICXy0NaFsBcfbJWFLl-GVJVuSTvHYCMkeRMjRJ2txDW5mtAYL4DZTMqUQtghUe4EmEDIDDr9KtRB1s0eM1mLJo87Un0t7xf1Pd5vlZ571zSLLhbS9y3Rxzx_r0ceU3qs2l18suJZVvB_wDdj_qL1sOtRyY3cJK9jk6LUvHllhnGk-VZtzZfg4owirVvv2Mx2s7ZNRNv3p9LRztQ45oRfTTa33NrcZufS7i3svBypL4GeWKXZ7MN4scoyjJqiDcnzshGnRx4ouVLYf8AusXwZ_gz8GvRrI5dL3-pLWWZ-m3O1X6HV0mNYo3NJfQ1R0kvP9isuCUI3yZ8rWOv4dp9PjytTa4O5o8atR7I42gh1xq691noNLp5Yd57-Drx-3n5q6GnhSvubI7KjLB1UY7GmGyo9keKtOPdDYisa9o6KNIbHkbEVFbjoogMvgovkKstFItEFllFgUWiqLAhRZRlVkIQCEIQoohZAqFlFgUWQgRRQRQFFlWQKhCECKKfBbKfAUEhM-KHSEzCl17kgkqJW6YQA5PkBhyFk-QGIDG9ikyWUmBUuQfxUGxDlWRpkqtEHvQyxEZDFLYAcntlfkWx0vfB-UJ7ABLcGL7PguQDbW7IBmqewpjm7FyiFKaBYySAaIAaAkhouRGiwZBMGQAS9ypnM9UxS-Fa3OlPyKyxWbFTX_2Zs21Lq7fPvUMbxaht7qReTVKeninzHk63qmgmouLjuuTzeRuPt7nKzT2YXfa8WqUZptNUJ9Qgo5uvHvCW6-gh5EpNPsHllKcKjs-fuZdGTI31sgGW3NkCPWY4YsmOlyWsEYdhWHFPp9r3Rrhiytb7mKzs_T6mSXRJ_mx_WsnDMeNJyo14cHVKo7PyRm6a9Dpms_W6lXCT7nawzVtP5u5h0-KWmxdXzN8I06SEsbcpq-yXds78byZ3bo4nckzXiVy-xkxb1RtxKmeuPNWiA6AqHA6PBahkeRiFx5GJkFhLgEJcBVotFItAEQEgBFgoIyIQhCKhCEKIQhCiEIQKgS4BCXAEIQgRRT4CKfAALkLsCuQuwVT5IR8kCIwGGwGFUJl81DhTW5KoaCZCMAMqqAuDDyP2CoPcoaQlkAjZnyL3WOb3Fz3IqRkNi7Qi6GRlsAy6Yut2i3Kym1yAE1sJbtUPlujO9p0RE4K5Le4AaSQEg2CwpbFtDWC0RSJqgGx00Z57Eovkzz2tDVMCa6lsZVz9fBSwW-UeK9TxPDOMmva5cnvsuPrg4tcnlvW9A8iaivqc8nfiy108lqY9MprwL6n1434o06_TuEowk96tuuTC7hyjL1KlO2yAN2yBHr9NqpYavdHaw5MWTEmcXpn_AChRnkxra0jjbtzdaWKMndDMC-FK-Tn6XV5MjjFdzqY8WTqTbv8AIsYybcedyl0yW_8Ag2Y7eWm7o5vwpRpqW5u0S9ql7t_J6ON5s3T06tm6C3M2BJQTNMOD1R560QXAxC4cjEVBxDsUHEBiCQCDRFEWUWBCEIASLKRZBRZCiKshCWBCEIUQhCBULTKLAhC6JQEIQgQqPf7hoGHf7hAUQhAKYLCYMgqnwLQcvlYMSKoEKWwLYC83yiY8jMm4tcgMsLsCiMoGT3Kb9rBm9yk7VACw4SBaKlsYU9boF2gYzqCCTckVEuxWSO9jeAJsBLBClyCg0qyrJPiwVIKqtyMkpO7QPxH33AFqzLmXuZtU4AZdPHKrjJJmarnP2siyB5tPkhyjHKTi6ZFatpo5fqGl676d2jX8WS4JmSzYm1z3RmxrG6rxHqmlfS3W6OBmhyj2mux1Fqcbo8z6hhUXcUYenHLbjkCa3ZCNbe9wOGoaQ3JpIqOysy45dVSon7zmxSqW6ODG40YsXw5dUaR0tLqkpqM9vDOO_UK_CaceRz3_ADVBL27SyQztKPc2Y95nI9Px1kUoLZ8o7OhhUfdvLuevjn9ebkv8dHHskjRAzw5Hxex6Y81PTDTFJhJgNQ2PAmLGpgGHECIRFGgkCgkBRCEAshCAWVZYNkURSLKQBEIQCEIQKhEQiAIhCBlLBlwSypMCkWUiwoWREZEBAWEAwqmwUy2wL_yFXNi2wpirAqW4IbdAkFotsAjZQE-QU6ClyD3JaogXwwiiIGy4zZTBj81hTrAnxZd2U-AFt2gA5CwqS3Quxgmb3CpJ7C2w2xUmTYrroL4q7ipOhblaaJtT8upUUlu_sZJ5cOVU2190W3FxqRjyrodcktWRc_Y6tP7ASyOPAmWTcX8QztqQGsw_GxtnmNdhUk6PTSyWcj1HTXFzhw-V4MOuDy89MupkNGSDU2Qjo9bDTRhH4mGnX4e7H4_hZlco1LwwXOC-QVkSb6lszi5yHvDCXZEjhUXcYmf96-FC5K39zVptZDK-Q1qx1NG7xK9m-DrYI1BWcvQw-Jkf9LOrHake3jnTx8ns-A6LEQGxOzjTUxkRURsWA2IyIqIyIDFwGhcRqIo0WCggKIQgELKLC6WCQhBaZEREQSiIQgEIQgVZCEAohW5QQTAYTAYFosojCqZEUyIC5AMNgBQyF9w5gBQ5JUAvlRWSVsi4AsplopgA-SnItsDuSgnvGwUFXsZIogt8AvgNopr2tgK7kI3REGoOJQSWxGtgEyYtjZIVJhVC5BAyACrFTTGPYCW5GipiZMbMzz4MEDOapruJyS6lUv1JkdJ1yLn7op_QlrTJktTYpyGZZW6ESfY5txJy3vyZ8z9rQxsVN3FoNRyM-lvK2uGQ2TXuIRvbXHHPC-htyS4bCmpZFUH9w8kuqTYhyccnUmcrWYOOhlk-Z8fQ0YNI8Uem_wAysWsjF1ktLyjdh-HOcakpPwaxTLK6dbQwWHBS7mzEr3MOCbkmnwjfB-w9vH6eTI6IcWIxyvcdFnRzOTGRYmLGxAfBjIiYDEA2IxCUMiyBqIUidwLIQgFkKIGlsopstMAkRERERmiISyARkRGUgoiEIBVFFlACUWUBCMjIwIyIhAKKZZTClTEzlSodNmTJO5tErWgt2xsflFJDLJARRLKs0gJAoNlLkirXBaIWBGV2aI-KKAXMkST5BjyQOjwUy48FMKCXAiRokIkFLZUt4lvhorsgFSFsZPkWwpczPlT6djTLgz5OGjFaYsrtMSsn4WOyqrMM5e9HOtQOSVuxEpb2Hll7hE2Y23FSkBJ3FlSYDY20B7shGyADjjnitk5V4DcpxrrjRtxZVONqvyYMn1HLSbLx43lhaHYNPmxzc4SdpAwl8J3zfY2aXUQk3v01280ax9pl6dfRxePBCL5SNvWpJRTMWLNGSTRpx_MmezB5MmrG6jQ6LM8WOi9joyfF7jIsTDdjolQ6IxCojIsBiYyLFIOLAcmRsBMlkBtlpim9w4sAyFECoyR3BbokXRlTURFJlorNQshQQRTLKkFWiEXBAqiiygICECBCEIBCEKsCAzewQqctqClZJbWZO9sfN2KrczWkDRSQRBO5T5sso0Lrayki7LQFEfARUgBBYQLAGQuL91By4FR2lZFjTFlTKiyT4DIWImO7ip8BSmwWy5MU2SNLk7EzdBSkBJ7CqpuxGUNypi8ruBFZMsk9jnZXUjfm23MWanuc8nSMuWXv_ITJhZPnYqTOTelSYtyLkxUmXQty3IKctyBRxzKHvjLjt5I_U0uUkLxdySwYp9rMI1YdR8dWh8PbsuOyMmBLDFJbnU0c8WSKUvm8iFjoaPI5xSR14bHLxNQ4VGrBkc3stv8AJ6uO9PLnO3Qix0ZcGaMqHQdnVzrTBjYiIDYsqHwYxCYPcYmUNTDTFRYaYDUyWAmRMAk9w0xSYSZA1MtMBFoKuRSKZRDZsWGkJixiYQRCEsAkQrqIgLISymwqpMhT3ZYFEIQCiEIBTKbLYLkFipSpCMkg5sW1ZKpbBSDkiJGVRItoJKirKBBsuTAbKDRFuApDIAWUwipbhAMGT2oje9AthQTFRe4zJxYpMlaPhIJvYRGYxy2DOlN0Kmw5MTOQWFyYqUtwpyEzZnbaZJVGxfxLKnKxEslEtU2UhUsqi6l3All-gGRqcLJsBqqivuc_NKkkbJZVKHRL8n4OfqDOTcIyfMzPJjJzESkc3QMpC5SLnIVKRRHLcgtyIBU0Mx54QjUthenjCcN5cD3oeuCcZc_Q5mzMeSM-GasU1jd9VGHBh-HkWPKva_7nUWmxS-SSYhafp9a3G5bb1ydjTSS9tVRyMeOMGnXHB0MMvYj0YPPm6UJOUjVAx4XUUase56I4VqgxsdzPEdFlYOixkWKiMSCmJhrsLQSCmEKIZBJlp7goiAdF7BC4sO9iqkgU9yS3AupIIcg0xadhJgMTKZSZYFotAlgWUXZQVXchCAWUX2KAhCEApgSCbAmwpcgQmCyKFkgty6CSpWRUewDezI2-p2wJvYAJTBAduRd0ZBJ7jYsRB3KjRXSkii2C-CwZ_KyoB8lA2WZUvM9kJb2HZvlMrYrUGmHHJ1Kr4YlMHq6cv0YVok_a2Jk7QTl7WhLZaheSVMVkltsScl1bi5MxWicsqdCMmQLK9zPOVnO1qL-LTB611PxLkTOVMF5B5NaW5coy5Zqaa8B5Z3FmTI97JbtYVke9CJSDyS3bESkZa2qchUpEnIVKZrSbW5bkFuW5AqJ07To26XXOL6Mu68mTDp8mWLaQyWkyR5OekjdLURn8u4Ucs4u4OjlLHkxZdjoaeE8lXJbkWuhi10k11JP6nT0-ZSSfZnHjp_sdDTx6Uo3Z2wcM3ZwNzq2b8bpnP0zpI3QkerFwrTFjocmaDs0wZpg-PAaFwDAMJC0GiNDCAQaoghZRYFpjE9hQSCjsXIJlMC4PsxqM6dMdFgNRZS4LAq6L5BkRAFZCiAWUQpcgEU2XZUmBCETIALBYTAZlVMWxjFsKhU5UiWLk7YVLFTd9w5vahMnvQFgzLBmQXi2lZquzPjXtGp0ggmwHLYqwHL2sCSe-xE6BW5J8EUOZ3BmSx85bbiGStRVlN7ksFskUy7QqciddC5SstoTkluA5A5ZVKhbmc7WoVllTdmScx-eSbZhnKjFrpjBSlbE5JtS2KlIXOVka0uc9tzNOVhzlaM83QNAyMzTkNyStGaciwDOQpsubFOZtBORBTkQaDNNqJaWVR-Vdjp4Ndh1C6Ze1_U4MM6y20OjCf8pysSV2J9LezstbcOjkLJmxcdxun1OTN82w0tdzT6h7qW_g6mBqVNHI0sNkdXS7JHbCOGddfDwjZB7nOwyfUbsb3PRHC1rxuzTB-4y42acb3NMtEORguHIwItIIqK2CsNCQaAQaIq6JRaRbRAL2LTKLQVCEIBQyEtgUi47AOiy2xd0w0BGRckIuQIQhAIQhALsqRCT-UCLcLsBjDCgYLZciiAZMU2HN0JbthVuQJTe9EQUM2Z0-rMMzT6VdWLwL2t-XZkMBkFwBLdkDoL2oObpFQXsRWR-xlAdQtyZTnRAChySbBTI-QE5nSFBah1JAJ7ErcDJgt7EkxbezRzUM50wJTtFZd4ClIKmTczTlTHTkZcr3sxWoy5s_RJ78ick1LdAayTW6Zjjm8P7mXWRpkxc3sD8VPkGUl5IoZSEzdl5JCXM1EoMjM82OnLYzTZqRihlLsIlLdoKcjPKZrSbE5bkEuW5CpsGg-f8ANHeRCHLJYHIl08FYEuvhEIIV1dN8qOjp-EQh2wcM3RwfOdDFyQh6I4tWMfDkhCs1qjyMRCEBrguHJCEUXYJckIFHEt8EIRQlkIFQohACQSIQJV-A1wQgEIiECrIQgEZGQgERUuCEAmMNkIFAwCEAXl5EkIZUL-YkuCECs2o4Lw_IQhAcuAO5CAPj8iByfIyEAyz_AA_cYQgFF_iRCFGXU_OhcexCGa3AS-Zi2QhzUqfAhcv7EIGoCZlzcEIYq4-3K1nzHOj_ANyRCGXeGot8EIQKmIkQhuMUqfBnyEIajJMzLMhDbIHyQhAP_9k";
		System.out.println(str);

		FileOutputStream write = new FileOutputStream("D:\\"+new File(UUID.randomUUID().toString() + ".jpg"));

		byte[] decoderBytes = Base64CryptFmtUtil.decodeToByte(str.getBytes()) ;
		write.write(decoderBytes);
	    write.close();*/
    }
}
