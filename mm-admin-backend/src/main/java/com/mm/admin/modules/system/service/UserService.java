package com.mm.admin.modules.system.service;

import com.mm.admin.modules.system.domain.User;
import com.mm.admin.modules.system.service.dto.UserDto;
import com.mm.admin.modules.system.service.dto.UserQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    /**
     * 根据ID查询
     */
    UserDto findById(long id);

    /**
     * 新增用户
     */
    void create(User resources);

    /**
     * 编辑用户
     */
    void update(User resources);

    /**
     * 删除用户
     */
    void delete(Set<Long> ids);

    /**
     * 根据用户名查询
     */
    UserDto findByName(String userName);

    /**
     * 修改密码
     */
    void updatePass(String username, String encryptPassword);

    /**
     * 修改头像
     */
    Map<String, String> updateAvatar(MultipartFile file);

    /**
     * 修改邮箱
     */
    void updateEmail(String username, String email);

    /**
     * 查询全部
     */
    Object queryAll(UserQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部不分页
     */
    List<UserDto> queryAll(UserQueryCriteria criteria);

    /**
     * 导出数据
     */
    void download(List<UserDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 用户自助修改资料
     */
    void updateCenter(User resources);
}
