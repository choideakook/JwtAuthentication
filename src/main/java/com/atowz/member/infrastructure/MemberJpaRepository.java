package com.atowz.member.infrastructure;

import com.atowz.member.doamin.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsByRecommendCode(String recommendCode);

    Optional<Member> findByUsername(String username);
}
