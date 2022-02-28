package com.ootd.with.domain.post;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.hashtag.Hashtag;
import com.ootd.with.domain.hashtag.HashtagService;
import com.ootd.with.web.post.CreatePostForm;
import com.ootd.with.web.post.UpdatePostForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostHashtagRepository postHashtagRepository;
    private final HashtagService hashtagService;

    @Override
    public Post save(CreatePostForm form) {
        Post post = Post.createPost(form);

        String[] hashtagNames = form.getHashtagNames().split(" ");
        for(String hashtagName : hashtagNames) {
            Hashtag hashtag = hashtagService.save(hashtagName);

            PostHashtag postHashtag = PostHashtag.builder()
                    .post(post)
                    .hashtag(hashtag)
                    .build();
            postHashtagRepository.save(postHashtag);

            // 연관관계 편의
            hashtag.getPostHashtagList().add(postHashtag);
            post.getPostHashtagList().add(postHashtag);
        }
        // Ban, Board, Hashtag, PostHashtag

        return postRepository.save(post);
    }

    @Override
    public Post findByIdAndStatusType(Long postId, StatusType type) {
        return postRepository.findByIdAndStatusType(postId, type).orElse(null);
    }

    @Override
    public void update(Post post, UpdatePostForm form) {
        post.updatePost(form);
    }

    @Override
    public void delete(Post post) {
        post.changeStatus(StatusType.DELETED);
    }
}
