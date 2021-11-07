package com.xh.common.file.prop;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * XhPropInfo
 *
 * @author xh
 * @date 2021/7/2
 */
@Getter
public class XhPropLineInfo {

    public static final String EMPTY_STRING = "";

    public static final String SPLIT_STRING = "=";

    /**
     * 注释字符串
     */
    public static final String NOTE_STRING = "#";

    private final String key;

    private final String value;

    private String line;

    private final boolean isEmptyLine;

    /**
     * 是否是注释
     */
    private final boolean isNote;

    public void setLine(String line) {
        this.line = line;
    }

    XhPropLineInfo(String key, String value, String line, boolean isEmptyLine, boolean isNote) {
        this.key = key;
        this.value = value;
        this.line = line;
        this.isEmptyLine = isEmptyLine;
        this.isNote = isNote;
    }

    public static XhPropLineInfo createPropInfo(String line) {
        if (StringUtils.isBlank(line)) {
            return new XhPropLineInfo(EMPTY_STRING, EMPTY_STRING, line, true, false);
        }
        if (line.trim().startsWith(NOTE_STRING)) {
            return new XhPropLineInfo(EMPTY_STRING, EMPTY_STRING, line, false, true);
        }
        String key = line.split(SPLIT_STRING)[0];
        final int index = line.indexOf(SPLIT_STRING);
        String value = line.substring(index + 1);
        return new XhPropLineInfo(key, value, line, false, false);
    }

}
