package com.ootd.with.domain.login;

import com.ootd.with.domain.member.Member;
import com.ootd.with.web.login.LoginForm;

public interface LoginService {
    Member login(LoginForm loginForm);
}
