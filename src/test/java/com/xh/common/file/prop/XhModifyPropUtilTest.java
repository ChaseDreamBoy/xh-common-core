package com.xh.common.file.prop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * XhModifyPropUtilTest
 *
 * @author xh
 * @date 2021/7/2
 */
public class XhModifyPropUtilTest {

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\asus\\Desktop\\test.properties";
        Map<String, String> map = new HashMap<>();
        map.put("token", "1111");
        map.put("token.create.time", "2222");
        XhModifyPropUtil.modifyProp(path, map);
    }

}
