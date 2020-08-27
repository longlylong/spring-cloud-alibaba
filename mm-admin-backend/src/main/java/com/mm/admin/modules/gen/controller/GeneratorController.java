package com.mm.admin.modules.gen.controller;

import com.mm.admin.common.utils.PageUtil;
import com.mm.admin.modules.gen.domain.ColumnInfo;
import com.mm.admin.modules.gen.service.GenConfigService;
import com.mm.admin.modules.gen.service.GeneratorService;
import com.thatday.common.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/generator")
public class GeneratorController {

    private final GeneratorService generatorService;
    private final GenConfigService genConfigService;

    @Value("${generator.enabled}")
    private Boolean generatorEnabled;

    @GetMapping(value = "/tables/all")
    public ResponseEntity<Object> queryTables() {
        return new ResponseEntity<>(generatorService.getTables(), HttpStatus.OK);
    }

    @GetMapping(value = "/tables")
    public ResponseEntity<Object> queryTables(@RequestParam(defaultValue = "") String name,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size) {
        int[] startEnd = PageUtil.transToStartEnd(page, size);
        return new ResponseEntity<>(generatorService.getTables(name, startEnd), HttpStatus.OK);
    }

    @GetMapping(value = "/columns")
    public ResponseEntity<Object> queryColumns(@RequestParam String tableName) {
        List<ColumnInfo> columnInfos = generatorService.getColumns(tableName);
        return new ResponseEntity<>(PageUtil.toPage(columnInfos, columnInfos.size()), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> save(@RequestBody List<ColumnInfo> columnInfos) {
        generatorService.save(columnInfos);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "sync")
    public ResponseEntity<HttpStatus> sync(@RequestBody List<String> tables) {
        for (String table : tables) {
            generatorService.sync(generatorService.getColumns(table), generatorService.query(table));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{tableName}/{type}")
    public ResponseEntity<Object> generator(@PathVariable String tableName, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response) {
        if (!generatorEnabled && type == 0) {
            throw GlobalException.createParam("此环境不允许生成代码，请选择预览或者下载查看！");
        }
        switch (type) {
            // 生成代码
            case 0:
                generatorService.generator(genConfigService.find(tableName), generatorService.getColumns(tableName));
                break;
            // 预览
            case 1:
                return generatorService.preview(genConfigService.find(tableName), generatorService.getColumns(tableName));
            // 打包
            case 2:
                generatorService.download(genConfigService.find(tableName), generatorService.getColumns(tableName), request, response);
                break;
            default:
                throw GlobalException.createParam("没有这个选项");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
