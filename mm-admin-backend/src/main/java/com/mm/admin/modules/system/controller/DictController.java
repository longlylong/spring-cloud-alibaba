package com.mm.admin.modules.system.controller;

import com.mm.admin.common.annotation.UserPermission;
import com.mm.admin.modules.logging.annotation.Log;
import com.mm.admin.modules.system.domain.Dict;
import com.mm.admin.modules.system.service.DictService;
import com.mm.admin.modules.system.service.dto.DictQueryCriteria;
import com.thatday.common.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dict")
public class DictController {

    private static final String ENTITY_NAME = "dict";
    private final DictService dictService;

    @Log("导出字典数据")
    @GetMapping(value = "/download")
    @UserPermission("dict:list")
    public void download(HttpServletResponse response, DictQueryCriteria criteria) throws IOException {
        dictService.download(dictService.queryAll(criteria), response);
    }

    @Log("查询字典")
    @GetMapping(value = "/all")
    @UserPermission("dict:list")
    public ResponseEntity<Object> queryAll() {
        return new ResponseEntity<>(dictService.queryAll(new DictQueryCriteria()), HttpStatus.OK);
    }

    @Log("查询字典")
    @GetMapping
    @UserPermission("dict:list")
    public ResponseEntity<Object> query(DictQueryCriteria resources, Pageable pageable) {
        return new ResponseEntity<>(dictService.queryAll(resources, pageable), HttpStatus.OK);
    }

    @Log("新增字典")
    @PostMapping
    @UserPermission("dict:add")
    public ResponseEntity<Object> create(@Validated @RequestBody Dict resources) {
        if (resources.getId() != null) {
            throw GlobalException.createParam("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        dictService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改字典")
    @PutMapping
    @UserPermission("dict:edit")
    public ResponseEntity<Object> update(@Validated(Dict.Update.class) @RequestBody Dict resources) {
        dictService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除字典")
    @DeleteMapping
    @UserPermission("dict:del")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        dictService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
