package com.mm.admin.modules.system.domain;

import com.mm.admin.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "sys_dict_detail")
public class DictDetail extends BaseEntity implements Serializable {

    @Id
    @Column(name = "detail_id")
    @NotNull(groups = Update.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "dict_id")
    @ManyToOne(fetch = FetchType.LAZY)
    //@ApiModelProperty(value = "字典", hidden = true)
    private Dict dict;

    //@ApiModelProperty(value = "字典标签")
    private String label;

    //@ApiModelProperty(value = "字典值")
    private String value;

    //@ApiModelProperty(value = "排序")
    private Integer dictSort = 999;
}
