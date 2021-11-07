package com.xh.common.file.prop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ModifyPropUtil
 *
 * @author xh
 * @date 2021/6/30
 */
public class XhModifyPropUtil {

    public static void modifyProp(String path, String key, String value) throws IOException {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(key, value);
        modifyProp(path, map);
    }

    public static void modifyProp(String path, Map<String, String> data) throws IOException {
        final XhPropInfo propInfo = XhPropInfo.createXhPropInfo(path);
        data.forEach(propInfo::modifyProp);
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path)))) {
            for (XhPropLineInfo lineInfo : propInfo.getList()) {
                out.println(lineInfo.getLine());
            }
        }
    }

}
