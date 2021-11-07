package com.xh.common.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * RandomUtil -- 随机数工具类
 *
 * @author xh
 * @date 2020/8/30
 */
public class XhRandomUtil {

    private XhRandomUtil() {

    }

    /**
     * 根据 {@link Math#random()} 获取随机数
     *
     * <p>
     * Math 类中的 random 方法返回一个 [0.0, 1.0) 区间的 double 值
     * </p>
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static int randomByMath(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * 以近似相等概率返回任意 int 值，因此很可能会得到负数
     *
     * @return 随机数
     */
    public static int randomByRandom() {
        Random random = new Random();
        return random.nextInt();
    }

    /**
     * 调用 nextInt 时带上 bound 参数，将得到预期区间内的随机数
     *
     * <p>
     * max 必须大于 Min。否则会抛出 java.lang.IllegalArgumentException 异常。
     * </p>
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static int randomByRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    /**
     * 通过 java8 的 ints 获取随机值
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static int randomByStream(int min, int max) {
        Random random = new Random();
        IntStream stream = random.ints(1, min, max);
        return stream.findFirst().orElse(0);
    }

    /**
     * 通过 {@link ThreadLocalRandom} 获取随机值
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static int randomByThreadLocalRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * 通过 {@link SplittableRandom} 获取随机值
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static int randomBySplittableRandom(int min, int max) {
        SplittableRandom splittableRandom = new SplittableRandom();
        return splittableRandom.nextInt(min, max);
    }

    public static int randomBySecureRandom(int min, int max) {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(max - min) + min;
    }
}
