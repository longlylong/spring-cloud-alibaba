package com.mm.admin.modules.system.service.mapstruct;

import com.mm.admin.common.base.BaseMapper;
import com.mm.admin.modules.system.domain.Dict;
import com.mm.admin.modules.system.service.dto.DictSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictSmallMapper extends BaseMapper<DictSmallDto, Dict> {

}
