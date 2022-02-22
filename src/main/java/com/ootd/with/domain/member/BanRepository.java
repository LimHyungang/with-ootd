package com.ootd.with.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BanRepository extends JpaRepository<Ban, Long> {

    List<Ban> findAllByMemberId(Long memberId);
}
