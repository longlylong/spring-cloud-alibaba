package com.thatday.common.token;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thatday.common.exception.TDExceptionHandler;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    public static final long Token_Expires = 2 * 24 * 60 * 60 * 1000L;
    private static final String Secret = "hds##hsh55578*&1";
    private static Algorithm algorithm;

    static {
        try {
            algorithm = Algorithm.HMAC256(Secret);
        } catch (Exception ignored) {
        }
    }

    public static String getAccessToken(Long uid) {
        return getAccessToken(uid, -1, "-1");
    }

    public static String getAccessToken(Long uid, Integer roleId, String deviceId) {
        Map<String, Object> map = new HashMap<>();
        map.put(TokenConstant.USER_ID, uid);
        map.put(TokenConstant.ROLE_ID, roleId);
        map.put(TokenConstant.DEVICE_ID, deviceId);
        map.put(TokenConstant.EXPIRES_TIME, new Date().getTime() + Token_Expires);
        return makeToken(map);
    }

    public static boolean checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }

        String[] tokens = token.split("\\.");
        if (tokens.length != 2) {
            return false;
        }

        UserInfo map = getUserInfo(token);
        if (map == null) {
            return false;
        }

        if (System.currentTimeMillis() > map.getExpireTime()) {
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
        return "" ;
    }

    private static String getSign(String base64Payload) {
        byte[] signatureBytes = algorithm.sign(base64Payload.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64URLSafeString(signatureBytes);
    }

    /**
     * 从token取得用户信息
     */
    public static UserInfo getUserInfo(String token) {
        if (StringUtils.isEmpty(token)) {
            throw TDExceptionHandler.throwTokenException();
        }

        String[] tokens = token.split("\\.");
        if (tokens.length != 2) {
            throw TDExceptionHandler.throwTokenException();
        }

        String payloadJson = new String(Base64Utils.decodeFromUrlSafeString(tokens[0]), StandardCharsets.UTF_8);
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(payloadJson, UserInfo.class);
        } catch (IOException e) {
            throw TDExceptionHandler.throwGlobalException("getUserInfo", e);
        }
    }
}
