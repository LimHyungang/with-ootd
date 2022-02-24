package com.ootd.with.domain.likey;

import com.ootd.with.domain.comment.*;
import com.ootd.with.domain.member.Member;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.post.PostLikey;
import com.ootd.with.domain.post.PostLikeyRepository;
import com.ootd.with.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class LikeyServiceImpl implements LikeyService {

    private final PostRepository postRepository;
    private final CommentService commentService;
    private final PostLikeyRepository postLikeyRepository;
    private final CommentLikeyRepository commentLikeyRepository;

    @Override
    public void postLikey(Member member, Long postId) {
        // member가 id={postId}인 post에 좋아요 버튼을 누름
        // 좋아요 한 적 없다면 save, 좋아요 한 적 있다면 delete
        PostLikey postLikey = findPostLikeyByMemberAndPostId(member, postId);
        if (postLikey == null) {
            savePostLikey(member, postId);
            return;
        }
        deletePostLikey(postLikey);
    }

    @Override
    public void commentLikey(Member member, Long commentId) {
        // member가 id={commentId}인 comment에 좋아요 버튼을 누름
        // 좋아요 한 적 없다면 save, 좋아요 한 적 있다면 delete
        CommentLikey commentLikey = findCommentLikeyByMemberAndPostId(member, commentId);
        if (commentLikey == null) {
            saveCommentLikey(member, commentId);
            return;
        }
        deleteCommentLikey(commentLikey);
    }

    @Override
    public Long savePostLikey(Member member, Long postId) {
        // TODO : postService.findById ? findOne? 만들어서 호출하는 걸로 변경
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null || member == null) {
            return null;
        }

        PostLikey postLikey = PostLikey.builder()
                                        .post(post)
                                        .member(member)
                                        .build();
        PostLikey savePostLikey = postLikeyRepository.save(postLikey);
        post.addLikeyCount();
        return savePostLikey.getId();
    }

    @Override
    public void deletePostLikey(PostLikey postLikey) {
        postLikey.getPost().subtractLikeyCount();
        postLikeyRepository.delete(postLikey);
    }

    @Override
    public Long saveCommentLikey(Member member, Long commentId) {
        Comment comment = commentService.findByCommentId(commentId);
        if (comment == null || member == null) {
            return null;
        }

        CommentLikey commentLikey = CommentLikey.builder()
                                                    .member(member)
                                                    .comment(comment)
                                                    .build();
        CommentLikey saveCommentLikey = commentLikeyRepository.save(commentLikey);
        comment.addLikeyCount();
        return saveCommentLikey.getId();
    }

    @Override
    public void deleteCommentLikey(CommentLikey commentLikey) {
        commentLikey.getComment().subtractLikeyCount();
        commentLikeyRepository.delete(commentLikey);
    }

    @Transactional(readOnly = true)
    @Override
    public PostLikey findPostLikeyByMemberAndPostId(Member member, Long postId) {
        return postLikeyRepository.findByMemberAndPostId(member, postId).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public CommentLikey findCommentLikeyByMemberAndPostId(Member member, Long commentId) {
        return commentLikeyRepository.findByMemberAndCommentId(member, commentId).orElse(null);
    }
}
