package com.atowz.member.doamin;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 403011540L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final QExam exam;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QInterview interview;

    public final StringPath nickname = createString("nickname");

    public final BooleanPath personalInformation = createBoolean("personalInformation");

    // custom
    public final QMyProfile profile = new QMyProfile(forProperty("profile"));

    public final ListPath<String, StringPath> profileImg = this.<String, StringPath>createList("profileImg", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath recommendCode = createString("recommendCode");

    public final NumberPath<Integer> recommendCount = createNumber("recommendCount", Integer.class);

    public final EnumPath<MemberStatus> status = createEnum("status", MemberStatus.class);

    public final BooleanPath useAgreement = createBoolean("useAgreement");

    public final StringPath username = createString("username");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exam = inits.isInitialized("exam") ? new QExam(forProperty("exam")) : null;
        this.interview = inits.isInitialized("interview") ? new QInterview(forProperty("interview")) : null;
    }

}

