package com.xh.common.file.prop;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;

/**
 * XhPropInfo
 *
 * @author xh
 * @date 2021/7/2
 */
@Getter
public class XhPropInfo {

    private final List<XhPropLineInfo> list;

    private final Map<String, String> propMap;

    private XhPropInfo(List<XhPropLineInfo> list, Map<String, String> propMap) {
        this.list = list;
        this.propMap = propMap;
    }

    public static XhPropInfo createXhPropInfo(String path) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            Map<String, String> propMap = new LinkedHashMap<>();
            List<XhPropLineInfo> list = new ArrayList<>();
            bufferedReader.lines().forEach(line -> {
                final XhPropLineInfo lineInfo = XhPropLineInfo.createPropInfo(line);
                list.add(lineInfo);
                if (lineInfo.isEmptyLine() || lineInfo.isNote()) {
                    return;
                }
                propMap.put(lineInfo.getKey(), lineInfo.getValue());
            });
            return new XhPropInfo(list, propMap);
        }
    }

    public void modifyProp(String key, String value) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        String useValue = Objects.toString(value, XhPropLineInfo.EMPTY_STRING);
        String line = key + XhPropLineInfo.SPLIT_STRING + useValue;
        if (propMap.containsKey(key)) {
            for (XhPropLineInfo lineInfo : list) {
                if (key.equals(lineInfo.getKey())) {
                    lineInfo.setLine(line);
                    break;
                }
            }
        } else {
            this.list.add(new XhPropLineInfo(key, useValue, line, false, false));
        }
        propMap.put(key, useValue);
    }

}
