package yijiang.jboot.Core;

import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LogUtil {
    /**
     * 重定向log4j日志地址
     * @param ext
     */
    public static void redirectLogext(String ext) {
        Properties props = new Properties();
        try {
            InputStream istream = LogUtil.class.getResourceAsStream("/log4j.properties");
            props.load(istream);
            istream.close();
            props.setProperty("log4j.appender.R.File", props.getProperty("log4j.appender.R.File") + ext);
            PropertyConfigurator.configure(props);// 装入log4j配置信息
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }
    }
}
