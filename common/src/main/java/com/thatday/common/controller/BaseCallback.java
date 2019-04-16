package com.thatday.common.controller;

/**
 * @desc 有返回值的回调继承该接口，无返回值的则无需继承
 */
public interface BaseCallback<E> {
    E callBack() throws Exception;
}
