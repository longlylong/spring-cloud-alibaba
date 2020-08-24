package com.mm.admin.modules.security.service.dto;

import com.mm.admin.modules.system.service.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class JwtUserDto {

    private final UserDto user;

    private final List<Long> dataScopes;
}
