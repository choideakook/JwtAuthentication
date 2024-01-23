package com.atowz.global.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class UserResponse {

    private String username;
    private String nickname;

    @JsonProperty("profile_image")
    private String profileImage;

    public void addUsername(Long userId) {
        this.username = String.valueOf(userId);
    }
}
