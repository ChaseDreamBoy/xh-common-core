package com.xh.common.util;

/**
 * UnicodeToChinese
 *
 * @author xh
 * @date 2021/4/10
 */
public class UnicodeToChinese {

    public static String convert(String unicode) {
        int start = 0;
        int end;
        StringBuilder buffer = new StringBuilder();
        while (start > -1) {
            end = unicode.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = unicode.substring(start + 2, unicode.length());
            } else {
                charStr = unicode.substring(start + 2, end);
            }
            // 16进制parse整形字符串。   
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(letter);
            start = end;
        }
        return buffer.toString();
    }

}
