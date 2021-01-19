package com.thatday.es.common.pojo;

import lombok.Data;

@Data
public class SuggestEntity extends CommonEntity {

    //排序列
    private String sortField;
    //自动补全建议列
    private String suggestField;
    //自动补全建议值
    private String suggestValue;
    //自动补全返回个数
    private Integer suggestCount;

    public Integer getSuggestCount() {
        if (suggestCount == null || suggestCount < 1) {
            return 1;
        }
        return suggestCount;
    }
}
