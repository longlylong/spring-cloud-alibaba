package com.thatday.common.model;

import lombok.Data;

@Data
public class NextIdVo {

    private String bizType;

    private String token;

    private Integer batchSize = 1;

    private Integer formatSize = 9;

    public static NextIdVo of(String bizType, String token) {
        NextIdVo nextIdVo = new NextIdVo();
        nextIdVo.setBizType(bizType);
        nextIdVo.setToken(token);
        return nextIdVo;
    }

    public static NextIdVo orderReqVo() {
        return of("OrderId", "a7a4ac1c78fb29ccbb07c12ae867a5ce");
    }

    public static NextIdVo userReqVo() {
        return of("UserId", "e051e12a920bc015c57759cc017ce00a");
    }
}
