package com.ootd.with.domain.comment;

import com.ootd.with.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeyRepository extends JpaRepository<CommentLikey, Long> {
    Optional<CommentLikey> findByMemberAndCommentId(Member member, Long commentId);
}
