package com.mm.admin.modules.security.config.bean;

import com.mm.admin.common.utils.StringUtils;
import com.mm.admin.modules.system.domain.Menu;
import com.mm.admin.modules.system.domain.Role;
import com.mm.admin.modules.system.service.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class JwtUserDto {

    private final UserDto user;

    private final List<Long> dataScopes;

    private Set<Role> roleLit;

    public Set<String> getRoles() {
        Set<String> roleStr = new HashSet<>();
        if (user.getIsAdmin()) {
            roleStr.add(Role.ADMIN);
            return roleStr;
        }
        roleStr = roleLit.stream().flatMap(role -> role.getMenus().stream())
                .filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                .map(Menu::getPermission).collect(Collectors.toSet());
        return roleStr;
    }
}
