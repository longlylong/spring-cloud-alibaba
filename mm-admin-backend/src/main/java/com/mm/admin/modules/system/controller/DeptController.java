package com.mm.admin.modules.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.mm.admin.common.annotation.UserPermission;
import com.mm.admin.common.exception.BadRequestException;
import com.mm.admin.common.utils.PageUtil;
import com.mm.admin.modules.logging.annotation.Log;
import com.mm.admin.modules.system.domain.Dept;
import com.mm.admin.modules.system.service.DeptService;
import com.mm.admin.modules.system.service.dto.DeptDto;
import com.mm.admin.modules.system.service.dto.DeptQueryCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dept")
public class DeptController {

    private static final String ENTITY_NAME = "dept";
    private final DeptService deptService;

    @Log("导出部门数据")
    @GetMapping(value = "/download")
    @UserPermission("dept:list")
    public void download(HttpServletResponse response, DeptQueryCriteria criteria) throws Exception {
        deptService.download(deptService.queryAll(criteria, false), response);
    }

    @Log("查询部门")
    @GetMapping
    @UserPermission("user:list,dept:list")
    public ResponseEntity<Object> query(DeptQueryCriteria criteria) throws Exception {
        List<DeptDto> deptDtos = deptService.queryAll(criteria, true);
        return new ResponseEntity<>(PageUtil.toPage(deptDtos, deptDtos.size()), HttpStatus.OK);
    }

    @Log("查询部门")
    @PostMapping("/superior")
    @UserPermission("user:list,dept:list")
    public ResponseEntity<Object> getSuperior(@RequestBody List<Long> ids) {
        Set<DeptDto> deptDtos = new LinkedHashSet<>();
        for (Long id : ids) {
            DeptDto deptDto = deptService.findById(id);
            List<DeptDto> depts = deptService.getSuperior(deptDto, new ArrayList<>());
            deptDtos.addAll(depts);
        }
        return new ResponseEntity<>(deptService.buildTree(new ArrayList<>(deptDtos)), HttpStatus.OK);
    }

    @Log("新增部门")
    @PostMapping
    @UserPermission("dept:add")
    public ResponseEntity<Object> create(@Validated @RequestBody Dept resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        deptService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改部门")
    @PutMapping
    @UserPermission("dept:edit")
    public ResponseEntity<Object> update(@Validated(Dept.Update.class) @RequestBody Dept resources) {
        deptService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除部门")
    @DeleteMapping
    @UserPermission("dept:del")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        Set<DeptDto> deptDtos = new HashSet<>();
        for (Long id : ids) {
            List<Dept> deptList = deptService.findByPid(id);
            deptDtos.add(deptService.findById(id));
            if (CollectionUtil.isNotEmpty(deptList)) {
                deptDtos = deptService.getDeleteDepts(deptList, deptDtos);
            }
        }
        // 验证是否被角色或用户关联
        deptService.verification(deptDtos);
        deptService.delete(deptDtos);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
