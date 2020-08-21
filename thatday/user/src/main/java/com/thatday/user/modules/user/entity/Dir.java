package com.thatday.user.modules.user.entity;

import com.thatday.common.utils.IdGen;
import com.thatday.user.repository.BaseEntity;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import org.apache.activemq.artemis.utils.IDGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 文件夹表
 */
@Data
@Entity(name = "td_dir")
public class Dir extends BaseEntity {

    @Id
    private String id;

    @NotNull
    private String userId;

    @NotNull
    private String title;

    private String icon;
}
