package com.atowz.member.application;

import com.atowz.global.feign.dto.UserResDto;
import com.atowz.member.doamin.entity.Member;

public interface MemberService {

    Member createMember(UserResDto dto);
}
