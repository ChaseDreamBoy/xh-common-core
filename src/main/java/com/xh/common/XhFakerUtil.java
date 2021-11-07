package com.xh.common;

import com.github.javafaker.Faker;

import java.util.Locale;

/**
 * FakerUtil
 *
 * @author xh
 * @date 2021/4/10
 */
public class XhFakerUtil {

    public static final Faker FAKER = new Faker(Locale.CHINA);

    public static String mobile() {
        return FAKER.phoneNumber().cellPhone();
    }

    public static String name() {
        return FAKER.name().name();
    }

    public static String address() {
        return FAKER.address().fullAddress();
    }

    public static String email() {
        return "";
    }

    public static int getNumber(int min, int max) {
        return FAKER.number().numberBetween(min, max);
    }

}
