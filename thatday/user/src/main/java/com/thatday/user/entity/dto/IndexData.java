package com.thatday.user.entity.dto;

import com.thatday.user.entity.db.GroupInfo;
import lombok.Data;

@Data
public class IndexData {

    Long id;

    GroupInfo group;
}
