package com.atowz.member.doamin;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.atowz.member.doamin.entity.Exam;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExam is a Querydsl query type for Exam
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QExam extends BeanPath<Exam> {

    private static final long serialVersionUID = -1059017767L;

    public static final QExam exam = new QExam("exam");

    public final StringPath balance = createString("balance");

    public final StringPath date = createString("date");

    public final StringPath liking = createString("liking");

    public final StringPath marry = createString("marry");

    public final NumberPath<Integer> preferredSubject = createNumber("preferredSubject", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> testDate = createDateTime("testDate", java.time.LocalDateTime.class);

    public final StringPath thought = createString("thought");

    public QExam(String variable) {
        super(Exam.class, forVariable(variable));
    }

    public QExam(Path<? extends Exam> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExam(PathMetadata metadata) {
        super(Exam.class, metadata);
    }

}

