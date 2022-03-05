package com.ootd.with.domain.member;

import com.ootd.with.web.login.LoginForm;
import com.ootd.with.web.member.CreateMemberForm;
import com.ootd.with.web.member.UpdateMemberForm;

public interface MemberService {
    Member save(CreateMemberForm form);
    Member login(LoginForm form);
    Member findById(Long memberId);
    Member findByEmail(String email);
    void update(Member member, UpdateMemberForm form);
    void delete(Long memberId);
}
