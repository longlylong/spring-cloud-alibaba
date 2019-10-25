package com.thatday.common.controller;

import com.thatday.common.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Slf4j
public class BaseController {

    /**
     * 创建参数参数错误的提示
     */
    public static ResponseModel buildParamErrorResponseModel(BindingResult result) {
        var sb = new StringBuilder();
        for (ObjectError error : result.getAllErrors()) {
            sb.append(error.getDefaultMessage());
            break;
        }

        return ResponseModel.buildParamError(sb.toString());
    }

}
