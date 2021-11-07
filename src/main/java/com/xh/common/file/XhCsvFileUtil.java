package com.xh.common.file;

import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * CsvFileUtil
 *
 * @author xh
 * @date 2021/4/10
 */
public class XhCsvFileUtil {

    public static List<List<String>> getRowList(String path, String charsetName) {
        List<List<String>> list = new ArrayList<>();
        final CsvData read = CsvUtil.getReader().read(new File(path), Charset.forName(charsetName));
        for (CsvRow row : read.getRows()) {
            list.add(row.getRawList());
        }
        return list;
    }

}
