package com.thatday.user.modules.user.backend;

import com.thatday.common.model.Result;
import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.vo.LoginPhoneVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/web/backend/login")
@Api(tags = "后台管理 API")
public class BackendLoginController {

    @ApiOperation("帐号密码登录")
    @PostMapping(value = "/loginByPhone")
    public Result<User> loginByPhone(@Valid @RequestBody LoginPhoneVo vo) {
        return Result.buildSuccess();
    }

}
