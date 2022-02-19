package com.ootd.with.web.member;

import com.ootd.with.domain.enumtype.SexType;
import com.ootd.with.domain.member.Member;
import com.ootd.with.domain.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 화면 진입 테스트")
    public void addMemberForm() throws Exception {
        mvc.perform(get("/members/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("members/addMemberForm"));
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void addMember() throws Exception {
        //given
        String name = "aaa";
        String email = "aaa@abc.com";
        String password = "asdf";
        String firstPhoneNumber = "010";
        String midPhoneNumber = "5678";
        String lastPhoneNumber = "1234";
        String nickName = "AAA";
        String sexType = SexType.MALE.name();

        //when
        mvc.perform(post("/members/add")
                        .param("name", name)
                        .param("email", email)
                        .param("password", password)
                        .param("firstPhoneNumber", firstPhoneNumber)
                        .param("midPhoneNumber", midPhoneNumber)
                        .param("lastPhoneNumber", lastPhoneNumber)
                        .param("nickName", nickName)
                        .param("sexType", sexType))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Member member = memberRepository.findByEmail(email).orElse(null);

        //then
        assertThat(member).isNotNull();
        assertThat(member.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("회원가입 오류 테스트")
    public void addMemberFailure() throws Exception {
        //given
        String name = "";
        String email = "aaa@abc.com";
        String password = "asdf";
        String firstPhoneNumber = "010";
        String midPhoneNumber = "5678";
        String lastPhoneNumber = "1234";
        String nickName = "AAA";
        String sexType = SexType.MALE.name();

        //when
        //then
        assertThatThrownBy(() ->
                mvc.perform(post("/members/add")
                                .param("name", name)
                                .param("email", email)
                                .param("password", password)
                                .param("firstPhoneNumber", firstPhoneNumber)
                                .param("midPhoneNumber", midPhoneNumber)
                                .param("lastPhoneNumber", lastPhoneNumber)
                                .param("nickName", nickName)
                                .param("sexType", sexType))
                        .andExpect(model().hasErrors())
                        .andExpect(redirectedUrl("members/addMemberForm"))
        ).hasNoNullFieldsOrProperties();
    }

}