package com.atowz.member.doamin;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@Builder(toBuilder = true)
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class Interview {

    private String toIdeal;
    private LocalDateTime createSelfIntroduce;

    private String location;
    private String job;
    private String education;
    private String smoke;
    private String drink;
    private String religion;
    private String mbti;
    private Integer height;
    private Integer birth;

    @Enumerated(STRING)
    private Gender gender;
}
