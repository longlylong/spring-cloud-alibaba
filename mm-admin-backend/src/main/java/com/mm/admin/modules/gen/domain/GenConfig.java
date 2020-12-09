package com.mm.admin.modules.gen.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 代码生成配置
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "code_gen_config")
public class GenConfig implements Serializable {

    @Id
    @Column(name = "config_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    //@ApiModelProperty(value = "表名")
    private String tableName;

    //@ApiModelProperty(value = "接口名称")
    private String apiAlias;

    @NotBlank
    //@ApiModelProperty(value = "后台管理包路径")
    private String adminPack;

    @NotBlank
    //@ApiModelProperty(value = "前台用的包路径")
    private String apiPack;

    @NotBlank
    //@ApiModelProperty(value = "模块名")
    private String moduleName;

    @NotBlank
    //@ApiModelProperty(value = "前端文件路径")
    private String path;

    //@ApiModelProperty(value = "前端文件路径")
    private String apiPath;

    //@ApiModelProperty(value = "作者")
    private String author;

    //@ApiModelProperty(value = "表前缀")
    private String prefix;

    //@ApiModelProperty(value = "是否覆盖")
    private Boolean cover = false;

    public GenConfig(String tableName) {
        this.tableName = tableName;
    }
}
