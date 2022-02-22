package com.ootd.with.domain.login;

import com.ootd.with.domain.member.Member;
import com.ootd.with.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member login(LoginForm loginForm) {
        Member member = memberRepository.findByEmail(loginForm.getEmail()).orElse(null);
        if (member == null || !passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
            return null;
        }
        return member;
    }
}
