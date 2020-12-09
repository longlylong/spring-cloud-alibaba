package com.thatday.common.utils;

import cn.hutool.json.JSONUtil;
import com.thatday.common.exception.TDExceptionHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpUtil extends cn.hutool.http.HttpUtil {

    public static <T> T getResult(HttpUriRequest httpUriRequest, Class<T> clazz) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse execute = httpClient.execute(httpUriRequest);

            int statusCode = execute.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = execute.getEntity();
                String toString = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
                System.out.println(toString);
                T t = JSONUtil.toBean(toString, clazz);
                return t;
            } else {
                return null;
            }

        } catch (IOException e) {
            throw TDExceptionHandler.throwGlobalException("getResult", e);
        }
    }
}
