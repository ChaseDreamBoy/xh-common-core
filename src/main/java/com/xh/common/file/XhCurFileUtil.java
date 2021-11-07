package com.xh.common.file;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * CurFileUtil
 *
 * @author xh
 * @date 2021/7/19
 */
public class XhCurFileUtil {

    public static boolean isFile(String filePath) {
        File file = new File(filePath);
        return file.isFile();
    }

    public static boolean isDirectory(String filePath) {
        File file = new File(filePath);
        return file.isDirectory();
    }

    public static boolean exists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static boolean createNewFile(String filePath) throws IOException {
        File file = new File(filePath);
        return file.createNewFile();
    }

    public static boolean createDirectory(String filePath) {
        File file = new File(filePath);
        return file.mkdirs();
    }

    public static void copyCommonIo(File srcFile, File destFile) throws IOException {
        FileUtils.copyFile(srcFile, destFile);
    }

    public static void copyChannel(File srcFile, File destFile) throws IOException {
        try (FileChannel inputChannel = new FileInputStream(srcFile).getChannel();
             FileChannel outputChannel = new FileOutputStream(destFile).getChannel()) {
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        }
    }

    public static void copyStream(File srcFile, File destFile) throws IOException {
        try (InputStream input = new FileInputStream(srcFile);
             OutputStream output = new FileOutputStream(destFile)) {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        }
    }

    public static boolean delete(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }

}
