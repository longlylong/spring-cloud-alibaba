package com.mm.admin.modules.system.service.mapstruct;

import com.mm.admin.common.base.BaseMapper;
import com.mm.admin.modules.system.domain.Menu;
import com.mm.admin.modules.system.service.dto.MenuDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends BaseMapper<MenuDto, Menu> {
}
