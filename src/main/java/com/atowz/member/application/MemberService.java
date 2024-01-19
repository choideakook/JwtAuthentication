package com.atowz.member.application;

import com.atowz.global.feign.dto.UserResponse;
import com.atowz.member.doamin.entity.Member;

public interface MemberService {

    Member createMember(UserResponse dto);
}
