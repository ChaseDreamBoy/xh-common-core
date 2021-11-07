package com.xh.common.file.txt;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XhTxtFileModifyUtil
 *
 * @author xh
 * @date 2021/11/3
 */
public class XhTxtFileModifyOperator implements Closeable {

    private final String txtFilePath;

    private final Map<Integer, String> lineMap = new HashMap<>();

    private List<String> cacheLine;

    private boolean needFlush = true;

    private boolean needReWrite = false;

    public XhTxtFileModifyOperator(String path, List<String> lines) {
        initLineMap(lines);
        this.txtFilePath = path;
    }

    public XhTxtFileModifyOperator(String path) throws IOException {
        initLineMap(XhTxtFileLineUtil.lines(path));
        this.txtFilePath = path;
    }

    private void initLineMap(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            lineMap.put(i, lines.get(i));
        }
    }

    /**
     * 修改莫一行的数据
     *
     * @param lineNo 要修改的行号，从0开始
     * @param value  改行的内容
     */
    public void updateLine(int lineNo, String value) {
        if (lineNo >= lineMap.size()) {
            insertLine(lineNo, value);
            return;
        }
        if (lineNo < 0) {
            throw new IllegalArgumentException("lineNo [" + lineNo + "] is less than 0");
        }
        lineMap.put(lineNo, value);
        needFlush = true;
        needReWrite = true;
    }

    /**
     * 添加一行
     *
     * @param lineNo 行号
     * @param value  添加的内容
     */
    public void insertLine(int lineNo, String value) {
        final int len = lineMap.size();
        if (lineNo < lineMap.size()) {
            int no = len - 1;
            while (no >= lineNo) {
                lineMap.put(no + 1, lineMap.get(no));
                no--;
            }
        }
        lineMap.put(Math.max(Math.min(lineNo, len), 0), value);
        needFlush = true;
        needReWrite = true;
    }

    public void insertLast(String value) {
        insertLine(Integer.MAX_VALUE, value);
    }

    public void insertFirst(String value) {
        insertLine(0, value);
    }

    /**
     * 获取所有的行
     *
     * @return 该文件所有的行
     */
    public List<String> getLineList() {
        if (needFlush) {
            reCalcLineList();
        }
        return cacheLine;
    }

    /**
     * 从新计算所有行
     */
    private void reCalcLineList() {
        int no = 0;
        List<String> lines = new ArrayList<>(lineMap.size());
        while (true) {
            final String line = lineMap.get(no);
            if (line == null) {
                break;
            }
            lines.add(line);
            no++;
        }
        needFlush = false;
        cacheLine = lines;
    }

    @Override
    public void close() throws IOException {
        if (needReWrite) {
            XhTxtFileLineUtil.writeLine(txtFilePath, getLineList(), false, false);
            needReWrite = false;
        }
    }

}
