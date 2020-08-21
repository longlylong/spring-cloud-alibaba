package com.thatday.user.modules.user.entity;

import com.thatday.user.repository.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户表
 */
@Data
@Entity(name = "td_user")
public class User extends BaseEntity {

    @Id
    private String id;

    @NotNull
    private String nickname;

    @OneToMany
    @JoinTable(name = "td_dir",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    List<Dir> dirs;
}
