package com.ootd.with.web.login;

import com.ootd.with.domain.login.LoginService;
import com.ootd.with.domain.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class LoginControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("로그인 화면 진입 테스트")
    public void loginForm() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/loginForm"));
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void login() throws Exception {
        //given
        String email = "test@test.com";
        String password = "test!";

        //when
        //then
        mvc.perform(post("/login")
                        .param("email", email)
                        .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }


    @Test
    @DisplayName("로그인 실패 (이메일) 테스트")
    public void loginFailureEmail() throws Exception {
        //given
        String email = "asdf@test.com";
        String password = "test!";

        //when
        //then
        mvc.perform(post("/login")
                        .param("email", email)
                        .param("password", password))
                .andExpect(model().hasErrors())
                .andExpect(view().name("login/loginForm"));
    }

    @Test
    @DisplayName("로그인 실패 (패스워드) 테스트")
    public void loginFailurePassword() throws Exception {
        //given
        String email = "test@test.com";
        String password = "test!123";

        //when
        //then
        mvc.perform(post("/login")
                        .param("email", email)
                        .param("password", password))
                .andExpect(model().hasErrors())
                .andExpect(view().name("login/loginForm"));
    }

}