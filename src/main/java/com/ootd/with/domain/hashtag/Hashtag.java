package com.ootd.with.domain.hashtag;

import com.ootd.with.domain.BaseTimeEntity;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.post.PostHashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Hashtag extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;

    @Column(name = "hashtag_name")
    private String name;

    @Builder
    public Hashtag(String name) {
        this.name = name;
    }
}
