package com.ootd.with.domain.member;

import com.ootd.with.domain.post.Post;

import java.util.List;

public interface BookmarkService {
    void bookmark(Member member, Long postId);
    Long saveBookmark(Member member, Long postId);
    void deleteBookmark(Bookmark bookmark);
    Bookmark findOne(Member member, Long postId);
    List<Bookmark> findAllByMember(Member member);
    List<Post> findPostsByMemberAndBoardId(Member member, Long boardId);
}
