package com.mm.admin.modules.logging.service.mapstruct;

import com.mm.admin.common.base.BaseMapper;
import com.mm.admin.modules.logging.domain.Log;
import com.mm.admin.modules.logging.service.dto.LogSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogSmallMapper extends BaseMapper<LogSmallDTO, Log> {

}
