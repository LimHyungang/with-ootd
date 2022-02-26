package com.ootd.with.domain.member;

import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;

    @Override
    public void bookmark(Member member, Long postId) {
        Bookmark bookmark = findOne(member, postId);
        if (bookmark == null) {
            saveBookmark(member, postId);
            return;
        }
        deleteBookmark(bookmark);
    }

    @Override
    public Long saveBookmark(Member member, Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (member == null || post == null) {
            return null;
        }
        Bookmark bookmark = Bookmark.builder()
                                        .member(member)
                                        .post(post)
                                        .build();
        Bookmark saveBookmark = bookmarkRepository.save(bookmark);
        member.getBookmarkList().add(saveBookmark);
        return saveBookmark.getId();
    }

    @Override
    public void deleteBookmark(Bookmark bookmark) {
        bookmarkRepository.delete(bookmark);
    }

    @Override
    public Bookmark findOne(Member member, Long postId) {
        return bookmarkRepository.findByMemberAndPostId(member, postId).orElse(null);
    }

    @Override
    public List<Bookmark> findAllByMember(Member member) {
        return bookmarkRepository.findAllByMember(member);
    }

    @Override
    public List<Post> findPostsByMemberAndBoardId(Member member, Long boardId) {
        return bookmarkRepository.findPostsByMemberAndBoardId(member, boardId);
    }
}
