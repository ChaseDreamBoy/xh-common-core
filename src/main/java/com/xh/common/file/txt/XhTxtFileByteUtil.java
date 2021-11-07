package com.xh.common.file.txt;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * TxtFileByteUtil
 *
 * @author xh
 * @date 2021/7/19
 */
public class XhTxtFileByteUtil {

    /**
     * 读取文件
     *
     * @param filePath 文件路径
     * @return 文件内容
     * @throws IOException 文件处理异常
     */
    public static String read(String filePath) throws IOException {
        return read(filePath, 1024, null);
    }

    /**
     * 读取文件
     *
     * @param filePath    文件路径
     * @param charsetName 编码
     * @return 文件内容
     * @throws IOException 文件处理异常
     */
    public static String read(String filePath, String charsetName) throws IOException {
        return read(filePath, 1024, charsetName);
    }

    /**
     * 读取文件
     *
     * @param filePath 文件路径
     * @param size     文件一次读取大小
     * @return 文件内容
     * @throws IOException 文件处理异常
     */
    public static String read(String filePath, int size) throws IOException {
        return read(filePath, size, null);
    }

    /**
     * 读取文件
     *
     * @param filePath    文件路径
     * @param size        文件一次读取大小
     * @param charsetName 编码
     * @return 文件内容
     * @throws IOException 文件处理异常
     */
    public static String read(String filePath, int size, String charsetName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] bys = new byte[size];
            while (fis.read(bys, 0, bys.length) != -1) {
                // 将字节数组转换为字符串
                String str;
                if (StringUtils.isBlank(charsetName)) {
                    str = new String(bys);
                } else {
                    str = new String(bys, Charset.forName(charsetName));
                }
                stringBuilder.append(str);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 写文件
     *
     * @param filePath 文件路径
     * @param msg      文件内容
     * @param append   是否追加
     * @throws IOException 文件处理异常
     */
    public static void write(String filePath, String msg, boolean append) throws IOException {
        write(filePath, msg, append, null);
    }

    /**
     * 写文件
     *
     * @param filePath    文件路径
     * @param msg         文件内容
     * @param append      是否追加
     * @param charsetName 编码
     * @throws IOException 文件处理异常
     */
    public static void write(String filePath, String msg, boolean append, String charsetName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath, append)) {
            final byte[] bytes;
            if (StringUtils.isBlank(charsetName)) {
                bytes = msg.getBytes();
            } else {
                bytes = msg.getBytes(Charset.forName(charsetName));
            }
            fos.write(bytes);
        }
    }

}
