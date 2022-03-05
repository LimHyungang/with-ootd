package com.ootd.with.domain.member;

import com.ootd.with.domain.enumtype.StatusType;
import com.ootd.with.web.login.LoginForm;
import com.ootd.with.web.member.CreateMemberForm;
import com.ootd.with.web.member.UpdateMemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member save(CreateMemberForm form) {
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        Member member = Member.createMember(form);
        return memberRepository.save(member);
    }

    @Override
    public Member login(LoginForm form) {
        Member member = memberRepository.findByEmail(form.getEmail()).orElse(null);
        if (member == null || !passwordEncoder.matches(form.getPassword(), member.getPassword())) {
            return null;
        }
        return member;
    }

    @Override
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void update(Long memberId, UpdateMemberForm form) {
        Member member = findById(memberId);
        member.update(form);
    }

    @Override
    public void delete(Long memberId) {
        Member member = findById(memberId);
        member.changeStatus(StatusType.DELETED);
    }

}
