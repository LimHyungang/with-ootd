package com.ootd.with.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BanServiceImplTest {

    @Autowired BanService banService;
    @Autowired BanRepository banRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    public void findAllBanByMemberIdTest() {  // 등록, 조회
        // given
        List<Member> memberList = memberRepository.findAll();
        Member member1 = memberList.get(0);
        Member member2 = memberList.get(1);
        Ban ban1 = banService.save(member1.getId(), member2.getId());
        Ban ban2 = banService.save(member2.getId(), member1.getId());

        // when
        List<Ban> banList = banService.findAllByMemberId(member1.getId());

        // then
        assertThat(banList.size()).isEqualTo(1);
        assertThat(banList.get(0)).isEqualTo(ban1);
        assertThat(banList.get(0)).isNotEqualTo(ban2);
    }

    @Test
    public void deleteBanTest() {  // 등록, 삭제
        // given
        List<Member> memberList = memberRepository.findAll();
        Member member1 = memberList.get(0);
        Member member2 = memberList.get(1);
        Ban ban1 = banService.save(member1.getId(), member2.getId());
        Ban ban2 = banService.save(member2.getId(), member1.getId());

        // when
        banService.delete(ban1.getId());
        List<Ban> banList = banService.findAllByMemberId(ban1.getId());

        // then
        assertThat(banList.size()).isEqualTo(0);
    }

    @BeforeEach
    public void init() {
        Member member1 = memberRepository.save(new Member("임현강", "asdf", "hyungang7@gmail.com",
                new PhoneNumber("010", "6878", "1422"),
                "MALE", "헤으응"));
        Member member2 = memberRepository.save(new Member("정우용", "asdf", "wooyong7@gmail.com",
                new PhoneNumber("010", "1234", "5678"),
                "MALE", "헤우웅"));
    }
}