package com.ootd.with.domain.member;

import com.ootd.with.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkService {
    void bookmark(Long memberId, Long postId);
    Long save(Long memberId, Long postId);
    void delete(Bookmark bookmark);
    Bookmark findOne(Long memberId, Long postId);
    Page<Post> findPostsByMemberId(Long memberId, Pageable pageable);
}
