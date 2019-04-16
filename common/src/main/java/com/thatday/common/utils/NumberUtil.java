package com.thatday.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {

    public static double half_up_2(double d) {
        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.HALF_UP);
        return bg.doubleValue();
    }

    public static double up_2(double d) {
        //直接向上取整
        BigDecimal bg = new BigDecimal(d).setScale(0, RoundingMode.UP);
        return bg.doubleValue();
    }

    public static BigDecimal roundHalfUp(BigDecimal inputDecimal) {
        Integer intValue = inputDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        return new BigDecimal(intValue);
    }
}
