package com.ootd.with.domain.post;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.web.post.CreatePostForm;
import com.ootd.with.web.post.UpdatePostForm;

public interface PostService {
    Post save(CreatePostForm form);

    Post findByIdAndStatusType(Long postId, StatusType type);

    void update(Post post, UpdatePostForm form);

    void delete(Post post);
}
