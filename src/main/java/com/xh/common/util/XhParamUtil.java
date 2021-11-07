package com.xh.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * ParamUtil
 *
 * @author xh
 * @date 2021/8/2
 */
public class XhParamUtil {

    public static void addParam(Map<String, Object> map, String key, Object val) {
        if (val != null) {
            map.put(key, val);
        }
    }

    public static void addParam(Map<String, Object> map, String key, Object val, Object defaultVal) {
        Object value = val != null ? val : defaultVal;
        map.put(key, value);
    }

    public static void addParamExcludeBlank(Map<String, Object> map, String key, String val) {
        if (StringUtils.isNotBlank(val)) {
            map.put(key, val);
        }
    }

    // ==================================================================

    public static String getStringParam(Map<String, Object> map, String key) {
        return getStringParam(map, key, null);
    }

    public static String getStringParam(Map<String, Object> map, String key, String defaultVal) {
        final Object obj = map.get(key);
        return Objects.toString(obj, defaultVal);
    }

    public static Integer getIntParam(Map<String, Object> map, String key, Integer defaultVal) {
        final String val = getStringParam(map, key, "");
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public static Integer getIntParam(Map<String, Object> map, String key) {
        return getIntParam(map, key, null);
    }

    public static Long getLongParam(Map<String, Object> map, String key, Long defaultVal) {
        final String val = getStringParam(map, key, "");
        try {
            return Long.parseLong(val);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public static Long getLongParam(Map<String, Object> map, String key) {
        return getLongParam(map, key, null);
    }
}
