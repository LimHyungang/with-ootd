package com.ootd.with.domain.board;

import com.ootd.with.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_name")
    private String name;

    @OneToMany(mappedBy = "board")
    private List<Post> posts = new ArrayList<>();
}
