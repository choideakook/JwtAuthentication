package com.atowz.member.application;

import com.atowz.member.doamin.entity.Member;

public interface MemberQueryService {

    Member byUsername(String username);
}
