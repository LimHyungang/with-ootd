package com.ootd.with.domain.post;

import com.ootd.with.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeyRepository extends JpaRepository<PostLikey, Long> {
    Optional<PostLikey> findByMemberAndPostId(Member member, Long postId);
}
