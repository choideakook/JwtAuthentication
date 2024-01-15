package com.atowz.member.infrastructure;

import com.atowz.member.doamin.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}
