package com.xh.common.file;

import cn.hutool.poi.excel.ExcelReader;
import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.util.List;

/**
 * ExcelUtil
 *
 * @author xh
 * @date 2021/4/10
 */
public class XhExcelFileUtil {

    public static List<List<Object>> getRowList(String path, int startRowIndex) {
        final ExcelReader reader = cn.hutool.poi.excel.ExcelUtil.getReader(new File(path));
        // return reader.read(1)
        return reader.read(startRowIndex);
    }
    
    public static void writeExcel(String path, String sheetName, List<List<Object>> data) {
        EasyExcel.write(path).sheet(sheetName).doWrite(data);
    }
    

}
