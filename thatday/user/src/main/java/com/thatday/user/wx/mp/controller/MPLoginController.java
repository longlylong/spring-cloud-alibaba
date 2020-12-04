package com.thatday.user.wx.mp.controller;

import com.thatday.common.model.Result;
import com.thatday.user.wx.mp.vo.MPAuthorLoginVo;
import com.thatday.user.wx.mp.vo.MPRedirectUrlVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "微信 公众号接口")
public class MPLoginController {

    @Autowired
    WxMpService wxMpService;

    @PostMapping("/wx/redirectUrl")
    @ApiOperation("获取跳转链接")
    public Result<String> redirectUrl(@Valid @RequestBody MPRedirectUrlVo vo) {
        String url = wxMpService.getOAuth2Service().buildAuthorizationUrl(vo.getRedirectURI(), vo.getScope(), "");
        return Result.buildSuccess(url);
    }

    @PostMapping("/wx/authorLogin")
    @ApiOperation("授权登录")
    public Result<String> authorLogin(@Valid @RequestBody MPAuthorLoginVo vo) {
        try {
            WxOAuth2AccessToken auth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(vo.getCode());
            String unionId = auth2AccessToken.getUnionId();
            return Result.buildSuccess("token");
        } catch (WxErrorException e) {
            e.printStackTrace();
            return Result.buildParamError(e.getMessage());
        }

    }
}
