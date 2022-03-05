package com.ootd.with.domain.member;

import com.ootd.with.domain.enumtype.SexType;
import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.web.login.LoginForm;
import com.ootd.with.web.member.CreateMemberForm;
import com.ootd.with.web.member.UpdateMemberForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    public void signUp() throws Exception {
        //given
        CreateMemberForm form = new CreateMemberForm();
        form.setEmail("asdf@test.com");
        form.setPassword("test!");
        form.setName("SignUpTester1");
        form.setPhoneNumber(new PhoneNumber("010", "5678", "1234"));
        form.setNickName("NickName");
        form.setSexType(SexType.MALE.name());

        //when
        Member saveMember = memberService.save(form);

        //then
        assertThat(saveMember).isNotNull();
        assertThat(saveMember.getEmail()).isEqualTo("asdf@test.com");
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void login() throws Exception {
        //given
        LoginForm form = new LoginForm();
        form.setEmail("test@test.com");
        form.setPassword("test!");

        //when
        Member loginMember = memberService.login(form);

        //then
        assertThat(loginMember).isNotNull();
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailure() throws Exception {
        //given
        LoginForm form = new LoginForm();
        form.setEmail("test@test.com");
        form.setPassword("test!!");

        //when
        Member loginMember = memberService.login(form);

        //then
        assertThat(loginMember).isNull();
    }

    @Test
    @DisplayName("회원 정보 변경 테스트")
    public void update() throws Exception {
        //given
        UpdateMemberForm form = new UpdateMemberForm();
        form.setId(1L);
        form.setName("변경된테스터이름");

        //when
        memberService.update(form);
        Member member = memberService.findById(1L);

        //then
        assertThat(member.getName()).isEqualTo("변경된테스터이름");
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    public void delete() throws Exception {
        //given
        memberService.delete(1L);

        //when
        Member member = memberService.findById(1L);

        //then
        assertThat(member.getStatusType()).isEqualTo(StatusType.DELETED);
    }

}