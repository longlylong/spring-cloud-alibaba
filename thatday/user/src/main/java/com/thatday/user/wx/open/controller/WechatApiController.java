package com.thatday.user.wx.open.controller;

import cn.hutool.json.JSONUtil;
import com.thatday.common.utils.TemplateCodeUtil;
import com.thatday.user.wx.open.dto.WXOpenLoginDTO;
import com.thatday.user.wx.open.vo.WxLoginCallbackVo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.nio.charset.StandardCharsets;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Controller
@Log4j2
@ApiIgnore
public class WechatApiController {

    @GetMapping("/wx/admin/wx_login_callback")
    public String wxLoginCallback(WxLoginCallbackVo loginCallback) throws Exception {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx3ab0f1c61c5591d2&secret=d3c077425ebbec39040d715cf11b9c2e" +
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
