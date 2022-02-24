package com.ootd.with.domain.comment;

import com.ootd.with.domain.enumtype.StatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByContent(String content);
    List<Comment> findAllByPostIdAndStatusType(Long postId, StatusType statusType);
    Page<Comment> findByPostIdAndStatusType(Long postId, StatusType statusType, Pageable pageable);
}
