package com.thatday.common.exception;

import com.thatday.common.controller.BaseController;
import com.thatday.common.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Slf4j
public class TDExceptionHandler {

    /**
     * 异常处理逻辑
     */
    public static ResponseModel handle(String path, Exception e) {
        log.error("GlobalExceptionHandler >> {}", path, e);
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return ResponseModel.buildError(ex.getCodeMsg());

        } else if (e instanceof MethodArgumentNotValidException) {
            var exception = (MethodArgumentNotValidException) e;
            return BaseController.buildParamErrorResponseModel(exception.getBindingResult());

        } else if (e instanceof BindException) {
            var exception = ((BindException) e);
            return BaseController.buildParamErrorResponseModel(exception.getBindingResult());

        } else {
            return ResponseModel.buildError("操作失败，请联系客服");
        }
    }
}
