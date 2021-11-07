package com.xh.common.util;

/**
 * XhSystemUtil
 *
 * @author xh
 * @date 2021/10/27
 */
public class XhSystemUtil {

    public static String getCallClassName(int stackDeepNum, String defaultValue) {
        try {
            return getCallClassName(stackDeepNum);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取类名
     *
     * @param stackDeepNum 0表示Thread，1表示XhSystemUtil，2表示开始调用XhSystemUtil的类
     * @return 类名(内部类是 CommonApp$AAA)
     */
    public static String getCallClassName(int stackDeepNum) {
        return Thread.currentThread().getStackTrace()[stackDeepNum].getClassName();
    }

    public static String getCallMethodName(int stackDeepNum, String defaultValue) {
        try {
            return Thread.currentThread().getStackTrace()[stackDeepNum].getMethodName();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String getCallMethodName(int stackDeepNum) {
        return Thread.currentThread().getStackTrace()[stackDeepNum].getMethodName();
    }

}
