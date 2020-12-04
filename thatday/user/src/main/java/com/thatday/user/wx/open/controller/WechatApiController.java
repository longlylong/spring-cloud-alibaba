package com.thatday.user.wx.open.controller;

import cn.hutool.json.JSONUtil;
import com.thatday.user.wx.open.dto.WXOpenLoginDTO;
import com.thatday.user.wx.open.vo.WxLoginCallbackVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.charset.StandardCharsets;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Controller
@Log4j2
@Api(tags = "微信 开放平台接口")
public class WechatApiController {

    @GetMapping("/wx/admin/wx_login_callback")
    @ApiOperation("扫码登录回调")
    public String wxLoginCallback(WxLoginCallbackVo loginCallback) throws Exception {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=xxx&secret=xxx" +
                "&code=" + loginCallback.getCode() + "&grant_type=authorization_code";

        CloseableHttpClient aDefault = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse execute = aDefault.execute(get);
        int statusCode = execute.getStatusLine().getStatusCode();
        String content = IOUtils.toString(execute.getEntity().getContent(), StandardCharsets.UTF_8);
        log.info("wxLoginCallback:---------------");
        log.info(loginCallback);
        log.info("-------------------");
        log.info(content);
        log.info("---------------------");

        if (statusCode == 200) {
            WXOpenLoginDTO wxOpenLoginDTO = JSONUtil.toBean(content, WXOpenLoginDTO.class);
            String token = "";
            if (token == null) {
                return "redirect:" + loginCallback.getRedirect() + "&isAuthor=false";
            }
            return "redirect:" + loginCallback.getRedirect() + "&token=" + token;
        } else {
            return content;
        }
    }

}
