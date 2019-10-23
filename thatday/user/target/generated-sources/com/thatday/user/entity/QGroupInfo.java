package com.thatday.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGroupInfo is a Querydsl query type for GroupInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGroupInfo extends EntityPathBase<GroupInfo> {

    private static final long serialVersionUID = -655396129L;

    public static final QGroupInfo groupInfo = new QGroupInfo("groupInfo");

    public final DateTimePath<java.sql.Timestamp> createTime = createDateTime("createTime", java.sql.Timestamp.class);

    public final StringPath groupIcon = createString("groupIcon");

    public final StringPath groupTitle = createString("groupTitle");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.sql.Timestamp> updateTime = createDateTime("updateTime", java.sql.Timestamp.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QGroupInfo(String variable) {
        super(GroupInfo.class, forVariable(variable));
    }

    public QGroupInfo(Path<? extends GroupInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroupInfo(PathMetadata metadata) {
        super(GroupInfo.class, metadata);
    }

}

