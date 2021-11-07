package com.xh.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ParamUtil
 *
 * @author xh
 * @date 2021/3/10
 */
public class XhParamBodyUtil {
    
    JsonNode jsonNode = null;

    private XhParamBodyUtil(String body) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonNode = mapper.readTree(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static XhParamBodyUtil createParamUtil(String body) {
        return new XhParamBodyUtil(body);
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        try {
            return this.jsonNode.get(key).asText(defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public Integer getInteger(String key) {
        return getInteger(key, null);
    }

    public Integer getInteger(String key, Integer defaultValue) {
        try {
            return Integer.parseInt(getString(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public Long getLong(String key) {
        return getLong(key, null);
    }

    public Long getLong(String key, Long defaultValue) {
        try {
            return Long.parseLong(getString(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
