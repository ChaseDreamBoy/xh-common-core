package com.xh.common;

/**
 * Test001
 *
 * @author xh
 * @date 2021/4/10
 */
public class Test001 {

    public static void main(String[] args) {
        for (int i = 1; i < 11; i++) {
            System.out.println("user" + i + " " + XhFakerUtil.getNumber(45, 95) + " " + XhFakerUtil.getNumber(45, 95) + " " + XhFakerUtil.getNumber(45, 95) + " " + XhFakerUtil.getNumber(45, 95) + " " + XhFakerUtil.getNumber(45, 95));
        }
    }

}
