package com.mm.admin.modules.system.service.mapstruct;

import com.mm.admin.common.base.BaseMapper;
import com.mm.admin.modules.system.domain.Role;
import com.mm.admin.modules.system.service.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {MenuMapper.class, DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends BaseMapper<RoleDto, Role> {

}
