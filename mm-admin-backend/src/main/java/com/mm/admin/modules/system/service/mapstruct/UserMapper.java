package com.mm.admin.modules.system.service.mapstruct;

import com.mm.admin.common.base.BaseMapper;
import com.mm.admin.modules.system.domain.User;
import com.mm.admin.modules.system.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserDto, User> {
}
