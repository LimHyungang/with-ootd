package com.ootd.with.domain.member;

import com.ootd.with.domain.enumtype.StatusType;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BanServiceImpl implements BanService {

    private final BanRepository banRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<Ban> findAllByMemberId(Long memberId) {
        // TODO : 행위 주체 member 의 상태 체크 추가개발

        return banRepository.findAllByMemberId(memberId);
    }

    @Override
    public Ban save(Long memberId, Long bannedMemberId) {
        // TODO : 행위 주체 member 의 상태 체크 추가개발

        // null 인 경우는 없다
        Member member = memberRepository.findById(memberId).orElse(null);
        Member bannedMember = memberRepository.findById(bannedMemberId).orElse(null);

        if(bannedMember.getStatusType() == StatusType.DELETED) {
            return null;
        }

        Ban ban = new Ban(member, bannedMember);
        return banRepository.save(ban);
    }

    @Override
    public void deleteBan(Long banId) {
        // TODO : 행위 주체 member 의 상태 체크 추가개발

        // ban, member 둘 다 null 인 경우 고려할 필요 없음

        Ban ban = banRepository.findById(banId).orElse(null);
        Member member = memberRepository.findById(ban.getMember().getId()).orElse(null);
        member.getBanList().remove(ban);  // 양방향 처리
        banRepository.deleteById(banId);
    }
}
