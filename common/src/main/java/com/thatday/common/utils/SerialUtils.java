package com.thatday.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 生成订单的
 */
public class SerialUtils {

    private final static Random random = new Random();
    private static final String Format_Order_Time = "yyMMddHHmmss";
    private final static SimpleDateFormat sdf = new SimpleDateFormat(Format_Order_Time);

    private static String getRandomString() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getOrderNum(SerialType serialType) {
        String format = sdf.format(new Date()) + getRandomString();

        if (serialType == SerialType.SaaS_Buy) {
            format = "100" + format;

        } else {
            format = "000" + format;
        }
        return format;
    }
}
