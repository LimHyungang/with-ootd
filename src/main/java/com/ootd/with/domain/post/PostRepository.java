package com.ootd.with.domain.post;

import com.ootd.with.domain.enumtype.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndStatusType(Long postId, StatusType type);
}
