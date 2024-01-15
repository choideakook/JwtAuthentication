package com.atowz.member.doamin;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInterview is a Querydsl query type for Interview
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QInterview extends BeanPath<Interview> {

    private static final long serialVersionUID = 844177927L;

    public static final QInterview interview = new QInterview("interview");

    public final NumberPath<Integer> birth = createNumber("birth", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createSelfIntroduce = createDateTime("createSelfIntroduce", java.time.LocalDateTime.class);

    public final StringPath drink = createString("drink");

    public final StringPath education = createString("education");

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final StringPath job = createString("job");

    public final StringPath location = createString("location");

    public final StringPath mbti = createString("mbti");

    public final StringPath religion = createString("religion");

    public final StringPath smoke = createString("smoke");

    public final StringPath toIdeal = createString("toIdeal");

    public QInterview(String variable) {
        super(Interview.class, forVariable(variable));
    }

    public QInterview(Path<? extends Interview> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInterview(PathMetadata metadata) {
        super(Interview.class, metadata);
    }

}

