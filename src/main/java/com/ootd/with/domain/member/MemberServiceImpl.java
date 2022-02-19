package com.ootd.with.domain.member;

import com.ootd.with.web.member.AddMemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member save(AddMemberForm form) {
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        Member member = Member.addMemberFromAddMemberForm(form);
        return memberRepository.save(member);
    }

}
