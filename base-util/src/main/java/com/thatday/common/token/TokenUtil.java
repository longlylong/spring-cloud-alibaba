package com.thatday.common.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thatday.common.exception.TDExceptionHandler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    public static void main(String[] args) {
        String token = getAccessToken("1", "", 0);
        System.out.println(token);
        System.out.println(checkToken(token));
        System.out.println(getUserInfo(token));
    }

    private static Algorithm algorithm;

    static {
        try {
            algorithm = Algorithm.HMAC256(TokenConstant.Secret);
        } catch (Exception ignored) {
        }
    }

    public static String getAccessToken(String uid) {
        return getAccessToken(uid, "", -1);
    }

    public static String getAccessToken(Long uid) {
        return getAccessToken(uid + "", "", -1);
    }

    public static String getAccessToken(Long uid, String role, Integer deviceId) {
        return getAccessToken(uid + "", role, deviceId);
    }

    public static String getAccessToken(String uid, String role, Integer deviceId) {
        Map<String, Object> map = new HashMap<>();
        map.put(TokenConstant.USER_ID, uid);
        map.put(TokenConstant.ROLE, role);
        map.put(TokenConstant.DEVICE_ID, deviceId);
        map.put(TokenConstant.CREATE_TIME, new Date().getTime());
        return makeToken(map);
    }

    public static boolean checkToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            jwtVerifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public static void checkTokenAndThrowException(String token) {
        if (!checkToken(token)) {
            throw TDExceptionHandler.throwTokenException();
        }
    }

    private static String makeToken(Map<String, Object> map) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String payloadJson = mapper.writeValueAsString(map);
            JWTCreator.Builder builder = JWT.create().withClaim("payload", payloadJson);
            builder.withExpiresAt(new Date(new Date().getTime() + TokenConstant.Token_Expires));
            return builder.sign(algorithm);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从token取得用户信息
     */
    public static UserInfo getUserInfo(String token) {
        checkTokenAndThrowException(token);

        DecodedJWT decode = JWT.decode(token);
        String payloadJson = decode.getClaim("payload").asString();

        try {
            ObjectMapper mapper = new ObjectMapper();
            UserInfo userInfo = mapper.readValue(payloadJson, UserInfo.class);
            userInfo.setExpireTime(decode.getExpiresAt().getTime());
            return userInfo;
        } catch (IOException e) {
            throw TDExceptionHandler.throwGlobalException("getUserInfo", e);
        }
    }
}
