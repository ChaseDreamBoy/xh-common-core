package com.xh.common.file;

import com.xh.common.XhFakerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ExcelUtilWriteTest
 *
 * @author xh
 * @date 2021/4/10
 */
public class ExcelUtilWriteTest {


    public static void main(String[] args) {
        List<List<Object>> list = new ArrayList<>();
        List<Object> first = Stream.of("号码", "名字", "（保持第一行不删除）").collect(Collectors.toList());
        list.add(first);
        for (int i = 0; i < 1000; i++) {
            List<Object> row = Stream.of(XhFakerUtil.mobile(), XhFakerUtil.name(), "").collect(Collectors.toList());
            list.add(row);
        }
        String path = "C:\\Users\\asus\\Desktop\\test-excel.xlsx";
        String sheetName = "test-sheet";
        XhExcelFileUtil.writeExcel(path, sheetName, list);
    }
    
}
