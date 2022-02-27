package com.ootd.with.domain.member;

import java.util.List;

public interface BanService {

    List<Ban> findAllByMemberId(Long memberId);  // 조회

    Ban save(Long memberId, Long bannedMemberId);

    void delete(Long banId);
}
