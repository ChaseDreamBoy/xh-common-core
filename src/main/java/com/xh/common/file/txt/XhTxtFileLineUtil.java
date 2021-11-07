package com.xh.common.file.txt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TxtFileUtil - 使用包装类 BuffedReader和BufferedWriter，对文件内容进行整行读取
 *
 * @author xh
 * @date 2021/7/19
 */
public class XhTxtFileLineUtil {

    /**
     * 读取文件每一行列表
     *
     * @param txtFilePath 文件路径
     * @return 文件每一行列表
     * @throws IOException 文件处理异常
     */
    public static List<String> lines(String txtFilePath) throws IOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(txtFilePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }

    /**
     * 按照行写文件
     *
     * @param filePath     文件路径
     * @param lines        每一行的内容
     * @param append       是否追加
     * @param startNewLine 第一行之前是否换行
     * @throws IOException 文件处理异常
     */
    public static void writeLine(String filePath, List<String> lines, boolean append, boolean startNewLine) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, append))) {
            if (startNewLine) {
                bw.newLine();
            }
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

}
