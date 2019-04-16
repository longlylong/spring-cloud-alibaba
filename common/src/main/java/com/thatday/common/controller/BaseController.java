package com.thatday.common.controller;

import com.thatday.common.constant.StatusCode;
import com.thatday.common.controller.callback.*;
import com.thatday.common.model.ExtPageInfo;
import com.thatday.common.model.PageInfo;
import com.thatday.common.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

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

        //token错误返回
        if (sb.toString().contains(StatusCode.Des_User_Info)
                || sb.toString().contains(StatusCode.Des_User_Info_Null)) {
            return buildUserInfoErrorResponseModel();
        }
        return ResponseModel.buildParamError(sb.toString());
    }

    /**
     * 创建用户信息无效或过期的返回
     */
    public static ResponseModel buildUserInfoErrorResponseModel() {
        return ResponseModel.build(StatusCode.Token_Error, StatusCode.Des_User_Info);
    }

    /**
     * 处理了异常的Controller层代码，返回结果给前端
     */
    public static <T> ResponseModel handleResponse(OnListCallBack<T> onListCallBack) {
        checkIsNull(onListCallBack);
        return getResponseModel(() -> {
            List<T> list = onListCallBack.callBack();
            return ResponseModel.buildSuccess(list);
        });
    }

    /**
     * 处理了异常的Controller层代码，返回结果给前端
     */
    public static ResponseModel handleResponse(OnVoidCallBack onVoidCallBack) {
        checkIsNull(onVoidCallBack);
        return getResponseModel(() -> {
            onVoidCallBack.callBack();
            return ResponseModel.buildSuccess();
        });
    }

    /**
     * 处理了异常的Controller层代码，返回结果给前端
     */
    public static <T> ResponseModel handleResponse(OnObjectCallBack<T> onObjectCallBack) {
        checkIsNull(onObjectCallBack);
        return getResponseModel(() -> {
            T t = onObjectCallBack.callBack();
            return ResponseModel.buildSuccess(t);
        });
    }

    /**
     * 处理了异常的Controller层代码，返回结果给前端
     *
     * @return 返回分页对象
     */
    public static <T> ResponseModel handleResponse(OnPageInfoCallBack<T> pageInfoCallBack) {
        checkIsNull(pageInfoCallBack);
        return getResponseModel(() -> {
            PageInfo<T> pageInfo = pageInfoCallBack.callBack();
            return ResponseModel.buildSuccess(pageInfo);
        });
    }

    /**
     * 处理了异常的Controller层代码，返回结果给前端
     *
     * @return 返回分页对象 ,携带了额外数据
     */
    public static <T, E> ResponseModel handleResponse(OnExtPageInfoCallBack<T, E> extPageInfoCallBack) {
        checkIsNull(extPageInfoCallBack);
        return getResponseModel(() -> {
            ExtPageInfo<T, E> pageInfo = extPageInfoCallBack.callBack();
            return ResponseModel.buildSuccess(pageInfo);
        });
    }

    /**
     * 返回reponseModel
     */
    private static ResponseModel getResponseModel(OnResponseListener listener) {
        try {
            checkIsNull(listener);
            return listener.getResponseModel();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("exception = {}", e);
            return ResponseModel.buildError(e);
        }
    }

    /**
     * 检查变量是否为nuLl
     */
    private static void checkIsNull(Object object) {
        if (object == null) {
            throw new RuntimeException("服务端错误 object 不能为null !");
        }
    }


    /**
     * 返回值的相应逻辑
     */
    public interface OnResponseListener {
        ResponseModel getResponseModel() throws Exception;
    }

}
