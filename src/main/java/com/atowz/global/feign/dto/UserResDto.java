package com.atowz.global.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class UserResDto {

    private String username;
    private String nickname;
    private String profile_image;

    public void addUsername(Long userId) {
        this.username = String.valueOf(userId);
    }
}
