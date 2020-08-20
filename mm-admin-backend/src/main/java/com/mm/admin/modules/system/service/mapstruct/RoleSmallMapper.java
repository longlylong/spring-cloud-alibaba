package com.mm.admin.modules.system.service.mapstruct;

import com.mm.admin.common.base.BaseMapper;
import com.mm.admin.modules.system.domain.Role;
import com.mm.admin.modules.system.service.dto.RoleSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleSmallMapper extends BaseMapper<RoleSmallDto, Role> {

}
