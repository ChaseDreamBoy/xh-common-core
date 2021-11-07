package com.xh.common.file.prop;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * PropUtil
 *
 * @author xh
 * @date 2021/6/30
 */
public class XhPropUtil {

    public static Properties getProperties(InputStream inputStream) throws IOException {
        // need close inputStream
        InputStream in = new BufferedInputStream(inputStream);
        Properties p = new Properties();
        p.load(in);
        return p;
    }

    public static Map<String, String> readProperties(String path) throws IOException {
        return XhPropInfo.createXhPropInfo(path).getPropMap();
    }

}
