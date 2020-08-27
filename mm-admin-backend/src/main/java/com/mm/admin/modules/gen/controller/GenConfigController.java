package com.mm.admin.modules.gen.controller;

import com.mm.admin.modules.gen.domain.GenConfig;
import com.mm.admin.modules.gen.service.GenConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genConfig")
public class GenConfigController {

    private final GenConfigService genConfigService;

    @GetMapping(value = "/{tableName}")
    public ResponseEntity<Object> query(@PathVariable String tableName) {
        return new ResponseEntity<>(genConfigService.find(tableName), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> update(@Validated @RequestBody GenConfig genConfig) {
        return new ResponseEntity<>(genConfigService.update(genConfig.getTableName(), genConfig), HttpStatus.OK);
    }
}
