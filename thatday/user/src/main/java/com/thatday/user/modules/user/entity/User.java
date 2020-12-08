package com.thatday.user.modules.user.entity;

import com.thatday.base.repository.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 用户表
 */
@Data
@Entity(name = "td_user")
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    private String id;

    @NotNull
    private String nickname;

    @ManyToMany
    @JoinTable(name = "rel_user_dir",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "dir_id", referencedColumnName = "dir_id")})
    Set<Dir> dirs;
}
