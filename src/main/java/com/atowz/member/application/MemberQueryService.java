package com.atowz.member.application;

import com.atowz.member.doamin.entity.Member;

import java.util.Optional;

public interface MemberQueryService {

    Optional<Member> byUsername(String username);

    Member byId(Long memberId);
}
