package com.ootd.with.domain.comment;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.member.Member;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    @Override
    public Long saveComment(Member member, Long postId, String content) {
        // TODO : postService.findById ? findOne? 만들어서 호출하는 걸로 변경
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null || member == null) {
            return null;
        }

        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .content(content)
                .likeyCount(0)
                .statusType(StatusType.NORMAL)
                .build();
        if (comment == null) {
            return null;
        }

        Comment saveComment = commentRepository.save(comment);
        post.getCommentList().add(saveComment);
        member.getCommentList().add(saveComment);
        return saveComment.getId();
    }

    @Transactional
    @Override
    public void updateComment(Long commentId, String content) {
        Comment comment = findByCommentId(commentId);
        if (comment == null) {
            // TODO : exception을 터뜨려야 할 거 같은데, 우선 나중에 생각
            return;
        }

        comment.update(content);
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        Comment comment = findByCommentId(commentId);
        if (comment == null) {
            // TODO : exception을 터뜨려야 할 거 같은데, 우선 나중에 생각
            return;
        }

        comment.delete();
    }

    @Override
    public boolean isLoginMemberAuthor(Member loginMember, Long commentId) {
        Comment comment = findByCommentId(commentId);

        // null 받으면 해당 comment 없음
        if (comment == null) {
            return false;
        }
        return Objects.equals(comment.getMember().getId(), loginMember.getId());
    }

    @Override
    public Comment findByCommentId(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostIdAndStatusType(postId, StatusType.NORMAL);
    }

    @Override
    public Page<Comment> findCommentsByPostId(Long postId, Pageable pageable) {
        // TODO : postService.findById ? findOne? 만들어서 호출하는 걸로 변경
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return null;
        }
        return commentRepository.findByPostIdAndStatusType(postId, StatusType.NORMAL, pageable);
    }
}
