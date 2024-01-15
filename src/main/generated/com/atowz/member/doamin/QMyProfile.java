package com.atowz.member.doamin;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMyProfile is a Querydsl query type for MyProfile
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMyProfile extends BeanPath<MyProfile> {

    private static final long serialVersionUID = -1616057629L;

    public static final QMyProfile myProfile = new QMyProfile("myProfile");

    public QMyProfile(String variable) {
        super(MyProfile.class, forVariable(variable));
    }

    public QMyProfile(Path<? extends MyProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMyProfile(PathMetadata metadata) {
        super(MyProfile.class, metadata);
    }

}

