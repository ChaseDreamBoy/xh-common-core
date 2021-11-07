package com.xh.common;

import com.xh.common.file.XhCurFileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ReIncludePackage
 *
 * @author xh
 * @date 2021/7/19
 */
public class ReIncludePackage {

    public static void main(String[] args) throws IOException {
        String basePath = "F:\\code\\gitee\\xh-java-base\\java-demo\\";
        String targetBasePath = "F:\\code\\gitee\\xh-company\\company-note\\lib";
        List<String> projectNameList = new ArrayList<>();
        projectNameList.add("common-core");
        projectNameList.add("http");
        projectNameList.add("media-core");
        projectNameList.add("java-core");
        projectNameList.add("spring-core");
        for (String projectName : projectNameList) {
            String jarName = projectName + "-1.0.0.jar";
            String packageFilePath = basePath + projectName + File.separator + "target" + File.separator + jarName;
            File packageFile = new File(packageFilePath);
            if (!packageFile.exists()) {
                System.out.println("file [" + packageFilePath + "] not exists");
                continue;
            }
            String targetFileName = targetBasePath + File.separator + jarName;
            File targetFile = new File(targetFileName);
            if (!targetFile.exists()) {
                System.out.println("file [" + targetFileName + "] not exists");
                continue;
            }
            targetFile.delete();
            XhCurFileUtil.copyCommonIo(packageFile, targetFile);
            System.out.println("copy [" + packageFilePath + "] finally.");
        }

    }

}
