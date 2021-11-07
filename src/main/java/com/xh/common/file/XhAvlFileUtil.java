package com.xh.common.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AvlFileUtil
 *
 * @author xh
 * @date 2021/4/10
 */
public class XhAvlFileUtil {
    
    public static List<String[]> handleAvl(String path, boolean isNotReadFirst, String split, String charsetName) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(path));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, charsetName));
        if (isNotReadFirst) {
            //循环中不读取第一行
            reader.readLine();
        }
        String line;
        List<String[]> list = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            list.add(line.split(split));
        }
        return list;
    }
    
}
