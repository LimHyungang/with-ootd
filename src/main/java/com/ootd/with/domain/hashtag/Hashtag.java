package com.ootd.with.domain.hashtag;

import com.ootd.with.domain.BaseTimeEntity;
import com.ootd.with.domain.post.PostHashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Constraint;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Hashtag extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;

    @Column(name = "hashtag_name", unique = true)
    private String name;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL)
    private List<PostHashtag> postHashtagList = new ArrayList<>();

    @Builder
    public Hashtag(String name) {
        this.name = name;
    }
}
