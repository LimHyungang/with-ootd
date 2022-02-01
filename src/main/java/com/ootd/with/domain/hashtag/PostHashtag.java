package com.ootd.with.domain.hashtag;

import com.ootd.with.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@IdClass(PostHashtagId.class)
public class PostHashtag {

    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

}
