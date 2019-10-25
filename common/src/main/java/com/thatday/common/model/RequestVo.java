package com.thatday.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求的基类 其他类必须集成这个
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestVo implements Serializable {

}
