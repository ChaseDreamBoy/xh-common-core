package com.xh.common.file.txt;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TxtFileUtil -- 使用 FileWriter 和 FileReader，对文件内容按字符读取
 *
 * @author xh
 * @date 2021/7/19
 */
public class XhTxtFileCharUtil {

    /**
     * 文件读取
     *
     * @param filePath 文件路径
     * @return 文件内容
     * @throws IOException 文件处理异常
     */
    public static String read(String filePath) throws IOException {
        return read(filePath, 1024);
    }

    /**
     * 文件读取
     *
     * @param filePath 文件路径
     * @param size     每次读取大小
     * @return 文件内容
     * @throws IOException 文件处理异常
     */
    public static String read(String filePath, int size) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(filePath)) {
            char[] buf = new char[size];
            int len;
            while ((len = reader.read(buf)) != -1) {
                String str = new String(buf, 0, len);
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
        try (FileWriter writer = new FileWriter(filePath, append)) {
            writer.write(msg);
        }
    }

    
    private static void testRead(String filePath) throws IOException {
        System.out.println("方式一:");
        try (FileReader reader = new FileReader(filePath)) {
            // 这样每次read读取一个字符，直到-1为止,这种方法不可取
            while (reader.read() != -1) {
                System.out.print(reader.read());
            }
        }

        System.out.println();
        System.out.println("方式二:");
        try (FileReader reader = new FileReader(filePath)) {
            int ch;
            // 这样写可以防止最后取值到-1的情况
            while ((ch = reader.read()) != -1) {
                // char就可以把原本是数字的文件翻译回来
                System.out.print((char) ch);
            }
        }

        System.out.println();
        System.out.println("方式三:");
        try (FileReader reader = new FileReader(filePath)) {
            // 数组自定长度一次性读取
            char[] buf = new char[1024];
            int len;
            while ((len = reader.read(buf)) != -1) {
                String str = new String(buf, 0, len);
                System.out.print(str);
            }
        }

    }
}
