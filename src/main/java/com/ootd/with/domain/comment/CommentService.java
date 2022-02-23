package com.ootd.with.domain.comment;

import com.ootd.with.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Long saveComment(Member member, Long postId, String content);
    void updateComment(Long commentId, String content);
    void deleteComment(Long commentId);
    boolean isLoginMemberAuthor(Member loginMember, Long commentId);
    Comment findByCommentId(Long commentId);
    List<Comment> findAllByPostId(Long postId);
    Page<Comment> findCommentsByPostId(Long postId, Pageable pageable);
}
