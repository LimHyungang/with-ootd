package com.ootd.with;

import com.ootd.with.domain.enumtype.SexType;
import com.ootd.with.domain.member.MemberService;
import com.ootd.with.domain.member.PhoneNumber;
import com.ootd.with.web.member.CreateMemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class TestDataInit {

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        CreateMemberForm form = new CreateMemberForm();
        form.setName("테스터");
        form.setEmail("test@test.com");
        form.setPassword("test!");
        form.setPhoneNumber(new PhoneNumber("010", "1234", "5678"));
        form.setNickName("Tester");
        form.setSexType(SexType.MALE.name());

        memberService.save(form);
    }
}
