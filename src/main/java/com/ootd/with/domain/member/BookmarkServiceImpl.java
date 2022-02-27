package com.ootd.with.domain.member;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Override
    public void bookmark(Long memberId, Long postId) {
        Bookmark bookmark = findOne(memberId, postId);
        if (bookmark == null) {
            save(memberId, postId);
            return;
        }
        delete(bookmark);
    }

    @Override
    public Long save(Long memberId, Long postId) {
        Member member = memberRepository.findById(memberId).orElse(null);
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
    public void delete(Bookmark bookmark) {
        bookmarkRepository.delete(bookmark);
    }

    @Override
    public Bookmark findOne(Long memberId, Long postId) {
        return bookmarkRepository.findByMemberIdAndPostId(memberId, postId).orElse(null);
    }

    @Override
    public Page<Post> findPostsByMemberId(Long memberId, Pageable pageable) {
        return bookmarkRepository.findPostsByMemberId(memberId, StatusType.NORMAL, pageable);
    }


}
