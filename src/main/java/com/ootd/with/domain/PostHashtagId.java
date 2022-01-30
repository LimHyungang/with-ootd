package com.ootd.with.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class PostHashtagId implements Serializable {

    @Column(name = "post_id")
    private Long post;

    @Column(name = "hashtag_id")
    private Long hashtag;

}
