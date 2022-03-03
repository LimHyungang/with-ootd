package com.ootd.with.domain.member;

import com.ootd.with.domain.BaseTimeEntity;
import com.ootd.with.domain.comment.Comment;
import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.domain.post.Post;
import com.ootd.with.domain.enumtype.RoleType;
import com.ootd.with.domain.enumtype.SexType;
import com.ootd.with.web.member.CreateMemberForm;
import com.ootd.with.web.member.UpdateMemberForm;
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

    @Embedded
    private PhoneNumber phoneNumber;

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
    public Member(String name, String password, String email, PhoneNumber phoneNumber, String sexType, String nickName) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sexType = SexType.valueOf(sexType);
        this.roleType = RoleType.MEMBER;
        this.nickName = nickName;
        this.statusType = StatusType.NORMAL;
    }

    //==연관 관계 메서드==//
    //Member 생성
    public static Member createMember(CreateMemberForm form) {
        return Member.builder()
                .name(form.getName())
                .email(form.getEmail())
                .password(form.getPassword())
                .phoneNumber(form.getPhoneNumber())
                .nickName(form.getNickName())
                .sexType(form.getSexType())
                .build();
    }

    //Member 정보 변경
    public void update(UpdateMemberForm form) {
        // 이름, 핸드폰 번호, 성별, 닉네임
        if (form.getName() != null) {
            this.name = form.getName();
        }
        if (form.getPhoneNumber() != null) {
            this.phoneNumber = form.getPhoneNumber();
        }
        if (form.getSexType() != null) {
            this.sexType = SexType.valueOf(form.getSexType());
        }
        if (form.getNickName() != null) {
            this.nickName = form.getNickName();
        }
    }

    //Member 탈퇴
    public void delete() {
        this.statusType = StatusType.DELETED;
    }
}


