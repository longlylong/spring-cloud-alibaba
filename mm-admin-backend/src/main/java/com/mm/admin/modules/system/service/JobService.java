package com.mm.admin.modules.system.service;

import com.mm.admin.modules.system.domain.Job;
import com.mm.admin.modules.system.service.dto.JobDto;
import com.mm.admin.modules.system.service.dto.JobQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JobService {

    /**
     * 根据ID查询
     */
    JobDto findById(Long id);

    /**
     * 创建
     */
    void create(Job resources);

    /**
     * 编辑
     */
    void update(Job resources);

    /**
     * 删除
     */
    void delete(Set<Long> ids);

    /**
     * 分页查询
     */
    Map<String, Object> queryAll(JobQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     */
    List<JobDto> queryAll(JobQueryCriteria criteria);

    /**
     * 导出数据
     */
    void download(List<JobDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 验证是否被用户关联
     */
    void verification(Set<Long> ids);
}
