package com.mm.admin.modules.gen.service;

import com.mm.admin.modules.gen.domain.ColumnInfo;
import com.mm.admin.modules.gen.domain.GenConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface GeneratorService {

    /**
     * 查询数据库元数据
     */
    Object getTables(String name, int[] startEnd);

    /**
     * 得到数据表的元数据
     */
    List<ColumnInfo> getColumns(String name);

    /**
     * 同步表数据
     */
    @Async
    void sync(List<ColumnInfo> columnInfos, List<ColumnInfo> columnInfoList);

    /**
     * 保持数据
     */
    void save(List<ColumnInfo> columnInfos);

    /**
     * 获取所有table
     */
    Object getTables();

    /**
     * 代码生成
     */
    void generator(GenConfig genConfig, List<ColumnInfo> columns);

    /**
     * 预览
     */
    ResponseEntity<Object> preview(GenConfig genConfig, List<ColumnInfo> columns);

    /**
     * 打包下载
     */
    void download(GenConfig genConfig, List<ColumnInfo> columns, HttpServletRequest request, HttpServletResponse response);

    /**
     * 查询数据库的表字段数据数据
     */
    List<ColumnInfo> query(String table);
}
