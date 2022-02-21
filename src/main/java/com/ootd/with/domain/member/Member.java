package com.ootd.with.domain.member;

import com.ootd.with.domain.BaseTimeEntity;
import com.ootd.with.domain.comment.Comment;
import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.enumtype.RoleType;
import com.ootd.with.domain.enumtype.SexType;
import com.ootd.with.web.member.AddMemberForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name", nullable = false)
    private String name;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private String firstPhoneNumber;

    @NotNull
    private String midPhoneNumber;

    @NotNull
    private String lastPhoneNumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SexType sexType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    @NotNull
    private String nickName;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusType statusType;

    @OneToMany(mappedBy = "member")
    private List<Ban> banList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookmarkList = new ArrayList<>();


    @Builder
    public Member(String name, String password, String email, String firstPhoneNumber, String midPhoneNumber, String lastPhoneNumber, String sexType, String nickName) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.firstPhoneNumber = firstPhoneNumber;
        this.midPhoneNumber = midPhoneNumber;
        this.lastPhoneNumber = lastPhoneNumber;
        this.sexType = SexType.valueOf(sexType);
        this.roleType = RoleType.MEMBER;
        this.nickName = nickName;
        this.statusType = StatusType.NORMAL;
    }

    public static Member addMemberFromAddMemberForm(AddMemberForm form) {
        return Member.builder()
                .name(form.getName())
                .email(form.getEmail())
                .password(form.getPassword())
                .firstPhoneNumber(form.getFirstPhoneNumber())
                .midPhoneNumber(form.getMidPhoneNumber())
                .lastPhoneNumber(form.getLastPhoneNumber())
                .nickName(form.getNickName())
                .sexType(form.getSexType())
                .build();
    }
}


