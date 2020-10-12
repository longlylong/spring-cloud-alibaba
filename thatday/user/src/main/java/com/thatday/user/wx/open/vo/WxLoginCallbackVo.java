package com.thatday.user.wx.open.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxLoginCallbackVo {

    private String redirect;

    private String code;
}
