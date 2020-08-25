package com.mm.admin.common.config;

public class MMPermissionConfig {

    public void check(String... permissions) {
//        // 获取当前用户的所有权限
//        List<String> elPermissions = SecurityUtils.getCurrentUser().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//        // 判断当前用户的所有权限是否包含接口上定义的权限
//        return elPermissions.contains("admin") || Arrays.stream(permissions).anyMatch(elPermissions::contains);
    }
}
