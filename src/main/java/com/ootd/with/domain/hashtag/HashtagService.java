package com.ootd.with.domain.hashtag;

import com.ootd.with.domain.post.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HashtagService {

    Hashtag findByName(String name);  // 단건 조회

    Hashtag save(Hashtag hashtag);  // 등록

    List<Hashtag> findAllByPostId(Long postId);  // 다건 조회 (post 조회 시)

    Page<Post> findPostsByHashtagId(Long hashtagId, int page, int size);
}
