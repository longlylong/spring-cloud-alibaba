package com.mm.admin.modules.system.controller;

import com.mm.admin.common.annotation.UserPermission;
import com.mm.admin.modules.logging.annotation.Log;
import com.mm.admin.modules.system.domain.DictDetail;
import com.mm.admin.modules.system.service.DictDetailService;
import com.mm.admin.modules.system.service.dto.DictDetailDto;
import com.mm.admin.modules.system.service.dto.DictDetailQueryCriteria;
import com.thatday.common.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dictDetail")
public class DictDetailController {

    private static final String ENTITY_NAME = "dictDetail";
    private final DictDetailService dictDetailService;

    @Log("查询字典详情")
    @GetMapping
    public ResponseEntity<Object> query(DictDetailQueryCriteria criteria,
                                        @PageableDefault(sort = {"dictSort"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(dictDetailService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("查询多个字典详情")
    @GetMapping(value = "/map")
    public ResponseEntity<Object> getDictDetailMaps(@RequestParam String dictName) {
        String[] names = dictName.split("[,，]");
        Map<String, List<DictDetailDto>> dictMap = new HashMap<>(16);
        for (String name : names) {
            dictMap.put(name, dictDetailService.getDictByName(name));
        }
        return new ResponseEntity<>(dictMap, HttpStatus.OK);
    }

    @Log("新增字典详情")
    @PostMapping
    @UserPermission("dict:add")
    public ResponseEntity<Object> create(@Validated @RequestBody DictDetail resources) {
        if (resources.getId() != null) {
            throw GlobalException.createParam("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        dictDetailService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改字典详情")
    @PutMapping
    @UserPermission("dict:edit")
    public ResponseEntity<Object> update(@Validated(DictDetail.Update.class) @RequestBody DictDetail resources) {
        dictDetailService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除字典详情")
    @DeleteMapping(value = "/{id}")
    @UserPermission("dict:del")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        dictDetailService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
