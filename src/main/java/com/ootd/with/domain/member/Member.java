package com.ootd.with.domain.member;

import com.ootd.with.domain.comment.Comment;
import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.enumtype.RoleType;
import com.ootd.with.domain.enumtype.SexType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
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
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @OneToMany(mappedBy = "member")
    private List<Ban> banList = new ArrayList<>();

    @Builder
    public Member(String name, String password, String email, String firstPhoneNumber, String midPhoneNumber, String lastPhoneNumber, SexType sex, RoleType roleType, String nickName) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.firstPhoneNumber = firstPhoneNumber;
        this.midPhoneNumber = midPhoneNumber;
        this.lastPhoneNumber = lastPhoneNumber;
        this.sex = sex;
        this.roleType = roleType;
        this.nickName = nickName;
    }
}


