package com.ootd.with.domain.login;

import com.ootd.with.domain.member.Member;

public interface LoginService {
    Member login(LoginForm loginForm);
}
