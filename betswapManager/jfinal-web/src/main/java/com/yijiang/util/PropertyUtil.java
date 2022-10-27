package com.yijiang.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class PropertyUtil {

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(PropertyUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 根据Name获取Property
     * @param name
     * @return
     */
    public static String getProperty(String name) {
        return prop.getProperty(name);
    }

    /**
     * 获取所有的Property
     * @return
     */
    public static List<String> getBeanFactoryClass() {
        List<String> list = new ArrayList<>();
        Set<String> keys = prop.stringPropertyNames();
        for (String key : keys) {
            list.add(prop.getProperty(key));
        }
        return list;
    }
}
