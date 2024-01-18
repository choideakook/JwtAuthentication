package com.atowz.member.application;

import com.atowz.member.doamin.entity.Member;

import java.util.Optional;

public interface MemberQueryService {

    Optional<Member> findByUsername(String username);

    Member findById(Long memberId);
}
