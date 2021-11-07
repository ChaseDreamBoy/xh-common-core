package com.xh.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * JsonUtil
 *
 * @author xh
 * @date 2021/3/28
 */
public class XhJsonUtil {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private XhJsonUtil() {

    }

    static {
        XhJsonUtil.OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static <T> T readValue(String content, Class<T> valueType) throws IOException {
        return OBJECT_MAPPER.readValue(content, valueType);
    }

    public static String toJson(Object obj) throws Exception {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    // start ======> 使用 Jackson 实现添加或者删除属性

    public static String addVal(String json, String key, String val) throws JsonProcessingException {
        ObjectNode jsonNodes = OBJECT_MAPPER.readValue(json, ObjectNode.class);
        jsonNodes.put(key, val);
        return OBJECT_MAPPER.writeValueAsString(jsonNodes);
    }

    public static String removeVal(String json, String key) throws JsonProcessingException {
        ObjectNode jsonNodes = OBJECT_MAPPER.readValue(json, ObjectNode.class);
        jsonNodes.remove(key);
        return OBJECT_MAPPER.writeValueAsString(jsonNodes);
    }

    // end ======> 使用 Jackson 实现添加或者删除属性


    public static class JsonMap {

        public static Map<String, String> readStringString(String json) throws JsonProcessingException {
            return OBJECT_MAPPER.readValue(json, new TypeReference<Map<String, String>>() {
            });
        }

        public static Map<String, Object> readStringObject(String json) throws JsonProcessingException {
            return OBJECT_MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        }

        public static Map<String, Integer> readStringInteger(String json) throws JsonProcessingException {
            return OBJECT_MAPPER.readValue(json, new TypeReference<Map<String, Integer>>() {
            });
        }

        public static Map<String, Long> readStringLong(String json) throws JsonProcessingException {
            return OBJECT_MAPPER.readValue(json, new TypeReference<Map<String, Long>>() {
            });
        }

        public static Map<Integer, Integer> readIntegerInteger(String json) throws JsonProcessingException {
            return OBJECT_MAPPER.readValue(json, new TypeReference<Map<Integer, Integer>>() {
            });
        }

        public static Map<Long, Long> readLongLong(String json) throws JsonProcessingException {
            return OBJECT_MAPPER.readValue(json, new TypeReference<Map<Long, Long>>() {
            });
        }

    }

    public static class JsonList {

        public static List<String> strings(String json) throws JsonProcessingException {
            return OBJECT_MAPPER.readValue(json, new TypeReference<List<String>>() {
            });
        }

        public static List<Integer> integers(String json) throws JsonProcessingException {
            return OBJECT_MAPPER.readValue(json, new TypeReference<List<Integer>>() {
            });
        }

        public static List<Long> longs(String json) throws JsonProcessingException {
            return OBJECT_MAPPER.readValue(json, new TypeReference<List<Long>>() {
            });
        }

    }


}
