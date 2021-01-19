package com.thatday.es.common.thread;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 使用线程本地局部变量处理结果集
 */
public class ResponseThreadLocal {

    private static final ThreadLocal<List<JSONObject>> threadLocal = new ThreadLocal<List<JSONObject>>();

    /*
     * @Description: 通过本地线程局部变量获取结果集
     * @Method: getList
     * @Param: []
     * @Update:
     * @since: 1.0.0
     * @Return: java.util.List<com.alibaba.fastjson.JSONObject>
     *
     */
    public static List<JSONObject> get() {
        return threadLocal.get();
    }

    /*
     * @Description:
     * @Method: 将统计后的数据集放入到当前线程
     * @Param: [list]
     * @Update:
     * @since: 1.0.0
     * @Return: void
     *
     */
    public static void set(final List<JSONObject> list) {
        threadLocal.set(list);
    }

    /*
     * @Description:
     * @Method: 清空当前线程本地局部变量值；否则内存泄露
     * @Param: []
     * @Update:
     * @since: 1.0.0
     * @Return: void
     *
     */
    public static void clear() {
        threadLocal.set(null);
    }

}
