package com.mm.admin.modules.system.service;

import com.mm.admin.modules.system.service.dto.UserDto;

import java.util.List;

/**
 * 数据权限服务类
 */
public interface DataService {

    /**
     * 获取数据权限
     */
    List<Long> getDeptIds(UserDto user);
}
