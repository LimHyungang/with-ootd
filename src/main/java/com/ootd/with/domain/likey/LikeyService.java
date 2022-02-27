package com.ootd.with.domain.likey;

import com.ootd.with.domain.comment.CommentLikey;
import com.ootd.with.domain.member.Member;
import com.ootd.with.domain.post.PostLikey;

public interface LikeyService {
    void postLikey(Member member, Long postId);
    void commentLikey(Member member, Long commentId);
    Long savePostLikey(Member member, Long postId);
    void deletePostLikey(PostLikey postLikey);
    Long saveCommentLikey(Member member, Long commentId);
    void deleteCommentLikey(CommentLikey commentLikey);
    PostLikey findPostLikeyByMemberAndPostId(Member member, Long postId);
    CommentLikey findCommentLikeyByMemberAndPostId(Member member, Long commentId);
}
