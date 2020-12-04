package com.thatday.user.wx.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.thatday.common.exception.GlobalException;
import com.thatday.common.exception.TDExceptionHandler;
import com.thatday.common.model.Result;
import com.thatday.user.modules.user.dto.LoginResultDTO;
import com.thatday.user.wx.miniapp.WxMaConfiguration;
import com.thatday.user.wx.vo.AuthorLoginVo;
import com.thatday.user.wx.vo.CommonLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Log4j2
@RequestMapping("/wx/user")
@Api(tags = "微信 小程序接口")
public class WxMaUserController {

    @PostMapping("/noneLogin")
    @ApiOperation("无感接口")
    public Result<LoginResultDTO> noneLogin(@Valid @RequestBody CommonLoginVo vo) {
        return getCommonLoginResult(vo, "appId");
    }

    private Result<LoginResultDTO> getCommonLoginResult(@RequestBody @Valid CommonLoginVo vo, String appId) {
        try {
            WxMaService wxMaService = WxMaConfiguration.getMaService(appId);
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(vo.getCode());
            String openId = session.getOpenid();

            Result<LoginResultDTO> token = null;

            return token;
        } catch (WxErrorException e) {
            throw TDExceptionHandler.throwGlobalException("getCommonLoginResult", e);
        }
    }

    @PostMapping("/authorLogin")
    @ApiOperation("普通授权登录")
    public Result<LoginResultDTO> authorLogin(@Valid @RequestBody AuthorLoginVo vo) {
        String appId = "app id";

        try {
            WxMaService wxMaService = WxMaConfiguration.getMaService(appId);
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(vo.getCode());

            // 解密用户信息
            WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(session.getSessionKey(), vo.getEncryptedData(), vo.getIv());

            return null;
        } catch (WxErrorException e) {
            throw TDExceptionHandler.throwGlobalException("tcyAuthorLogin", e);
        }
    }

    @PostMapping("/phoneLogin")
    @ApiOperation("手机授权")
    public Result<LoginResultDTO> phoneLogin(@Valid @RequestBody AuthorLoginVo vo) {
        String appId = "app id";

        try {
            WxMaService wxMaService = WxMaConfiguration.getMaService(appId);
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(vo.getCode());
            String openId = session.getOpenid();

            WxMaPhoneNumberInfo wxPhoneInfo = getWxPhoneInfo(wxMaService, session.getSessionKey(), vo.getEncryptedData(), vo.getIv());
            return null;
        } catch (WxErrorException e) {
            throw TDExceptionHandler.throwGlobalException("agentLogin", e);
        }
    }

    private WxMaPhoneNumberInfo getWxPhoneInfo(WxMaService wxMaService, String sessionKey, String encryptedData, String iv) {
        // 解密手机信息
        try {
            return wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        } catch (Exception e) {
            log.error("\n---------------------------------------");
            log.error("SessionKey：" + sessionKey);
            log.error("EncryptedData：" + encryptedData);
            log.error("Iv：" + iv);
            e.printStackTrace();
            throw GlobalException.createParam("校验失败,请再登录!");
        }
    }
}
