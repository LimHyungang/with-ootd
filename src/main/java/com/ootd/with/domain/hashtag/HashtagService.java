package com.ootd.with.domain.hashtag;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HashtagService {

    Hashtag save(Hashtag hashtag);  // 등록

    List<Hashtag> findAllByPostId(Long postId);  // 목록 조회 (post 조회 시)

    Page<Post> findPostsByHashtagNameAndStatusType(String hashtagName, StatusType type, Pageable pageable);
}
