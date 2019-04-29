package com.thatday.common.validation;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thatday.common.model.RequestVo;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    public static final long Token_Expires = 7 * 24 * 60 * 60 * 1000;
    private static final String Secret = "hdsfhj78dsfbffuih@#778*&1";
    private static Algorithm algorithm;

    static {
        try {
            algorithm = Algorithm.HMAC256(Secret);
        } catch (Exception ignored) {
        }
    }

    public static String getUserToken(Integer uid, Integer role, Integer device) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", uid);
        map.put("roleId", role);
        map.put("device", device);
        map.put("expireTime", new Date().getTime() + Token_Expires);
        return makeToken(map);
    }

    //校验用户信息的
    public static boolean checkToken(RequestVo.UserInfo userInfo) {
        if (userInfo == null || userInfo.getUserId() == null || userInfo.getRoleId() == null || userInfo.getDevice() == null
                || StringUtils.isEmpty(userInfo.getToken())) {
            return false;
        }

        String[] tokens = userInfo.getToken().split("\\.");
        if (tokens.length != 2) {
            return false;
        }

        RequestVo.UserInfo infoMap = getUserInfo(userInfo.getToken());

        if (infoMap == null || infoMap.getUserId() == null || infoMap.getRoleId() == null
                || infoMap.getDevice() == null || infoMap.getExpireTime() == null) {
            return false;
        }

        if (System.currentTimeMillis() > infoMap.getExpireTime()) {
            return false;
        }

        return userInfo.getUserId().equals(infoMap.getUserId())
                && userInfo.getRoleId().equals(infoMap.getRoleId())
                && userInfo.getDevice().equals(infoMap.getDevice())
                && tokens[1].equals(getSign(tokens[0]));
    }

    //普通的校验token
    public static boolean checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }

        String[] tokens = token.split("\\.");
        if (tokens.length != 2) {
            return false;
        }

        RequestVo.UserInfo map = getUserInfo(token);
        if (map == null) {
            return false;
        }

        long endTime = map.getExpireTime();
        if (System.currentTimeMillis() > endTime) {
            return false;
        }

        return tokens[1].equals(getSign(tokens[0]));
    }

    private static String makeToken(Map<String, Object> map) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            String payloadJson = mapper.writeValueAsString(map);
            String base64Payload = Base64.encodeBase64URLSafeString(payloadJson.getBytes());

            byte[] signatureBytes = algorithm.sign(base64Payload.getBytes(StandardCharsets.UTF_8));
            String signature = Base64.encodeBase64URLSafeString(signatureBytes);

            return String.format("%s.%s", base64Payload, signature);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSign(String base64Payload) {
        byte[] signatureBytes = algorithm.sign(base64Payload.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64URLSafeString(signatureBytes);
    }

    /**
     * 从token取得用户信息
     */
    public static RequestVo.UserInfo getUserInfo(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        String[] tokens = token.split("\\.");
        if (tokens.length != 2) {
            return null;
        }

        String payloadJson = new String(Base64Utils.decodeFromUrlSafeString(tokens[0]), StandardCharsets.UTF_8);
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(payloadJson, RequestVo.UserInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
