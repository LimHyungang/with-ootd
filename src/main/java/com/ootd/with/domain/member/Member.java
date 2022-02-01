package com.ootd.with.domain.member;

import com.ootd.with.domain.comment.Comment;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.enumtype.RoleType;
import com.ootd.with.domain.enumtype.SexType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String name;

    private String password;

    private String email;

    private String firstPhoneNumber;

    private String midPhoneNumber;

    private String lastPhoneNumber;

    @Enumerated(EnumType.STRING)
    private SexType sex;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private String nickName;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();
}
