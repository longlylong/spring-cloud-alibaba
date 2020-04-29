package com.thatday.common.controller;

import com.thatday.common.constant.StatusCode;
import com.thatday.common.model.Result;
import com.thatday.common.token.TokenConstant;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Slf4j
public class BaseController {

    /**
     * 创建参数参数错误的提示
     */
    public static Result buildParamErrorResponseModel(BindingResult result) {
        var sb = new StringBuilder();
        for (ObjectError error : result.getAllErrors()) {
            sb.append(error.getDefaultMessage());
            break;
        }

        String msg = sb.toString();
        if (TokenConstant.Msg_Access_Token_Error.equals(msg)) {
            return Result.build(StatusCode.Token_Error, msg);
        }

        return Result.buildParamError(sb.toString());
    }

}
