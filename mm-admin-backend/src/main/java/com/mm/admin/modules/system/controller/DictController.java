package com.mm.admin.modules.system.controller;

import com.mm.admin.common.exception.BadRequestException;
import com.mm.admin.modules.logging.annotation.Log;
import com.mm.admin.modules.system.domain.Dict;
import com.mm.admin.modules.system.service.DictService;
import com.mm.admin.modules.system.service.dto.DictQueryCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@RestController
@RequiredArgsConstructor
//@Api(tags = "系统：字典管理")
@RequestMapping("/api/dict")
public class DictController {

    private static final String ENTITY_NAME = "dict";
    private final DictService dictService;

    @Log("导出字典数据")
    //@ApiOperation("导出字典数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@mm.check('dict:list')")
    public void download(HttpServletResponse response, DictQueryCriteria criteria) throws IOException {
        dictService.download(dictService.queryAll(criteria), response);
    }

    @Log("查询字典")
    //@ApiOperation("查询字典")
    @GetMapping(value = "/all")
    @PreAuthorize("@mm.check('dict:list')")
    public ResponseEntity<Object> queryAll() {
        return new ResponseEntity<>(dictService.queryAll(new DictQueryCriteria()), HttpStatus.OK);
    }

    @Log("查询字典")
    //@ApiOperation("查询字典")
    @GetMapping
    @PreAuthorize("@mm.check('dict:list')")
    public ResponseEntity<Object> query(DictQueryCriteria resources, Pageable pageable) {
        return new ResponseEntity<>(dictService.queryAll(resources, pageable), HttpStatus.OK);
    }

    @Log("新增字典")
    //@ApiOperation("新增字典")
    @PostMapping
    @PreAuthorize("@mm.check('dict:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Dict resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        dictService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改字典")
    //@ApiOperation("修改字典")
    @PutMapping
    @PreAuthorize("@mm.check('dict:edit')")
    public ResponseEntity<Object> update(@Validated(Dict.Update.class) @RequestBody Dict resources) {
        dictService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除字典")
    //@ApiOperation("删除字典")
    @DeleteMapping
    @PreAuthorize("@mm.check('dict:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        dictService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
