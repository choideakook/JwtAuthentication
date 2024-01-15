package com.atowz.member.doamin;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@Builder(toBuilder = true)
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class Exam {

    private String thought;
    private String date;
    private String liking;
    private String balance;
    private String marry;
    private Integer preferredSubject;

    private LocalDateTime testDate;
}
