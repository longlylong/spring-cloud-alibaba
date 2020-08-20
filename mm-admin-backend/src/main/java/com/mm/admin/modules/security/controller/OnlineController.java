package com.mm.admin.modules.security.controller;

import com.mm.admin.common.utils.EncryptUtils;
import com.mm.admin.modules.logging.annotation.Log;
import com.mm.admin.modules.security.service.OnlineUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/online")
//@Api(tags = "系统：在线用户管理")
public class OnlineController {

    private final OnlineUserService onlineUserService;

    //"查询在线用户"
    @GetMapping
    @PreAuthorize("@mm.check()")
    public ResponseEntity<Object> query(String filter, Pageable pageable) {
        return new ResponseEntity<>(onlineUserService.getAll(filter, pageable), HttpStatus.OK);
    }

    @Log("导出数据")
    //@ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@mm.check()")
    public void download(HttpServletResponse response, String filter) throws IOException {
        onlineUserService.download(onlineUserService.getAll(filter), response);
    }

    //@ApiOperation("踢出用户")
    @DeleteMapping
    @PreAuthorize("@mm.check()")
    public ResponseEntity<Object> delete(@RequestBody Set<String> keys) throws Exception {
        for (String key : keys) {
            // 解密Key
            key = EncryptUtils.desDecrypt(key);
            onlineUserService.kickOut(key);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
