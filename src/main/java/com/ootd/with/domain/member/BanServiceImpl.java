package com.ootd.with.domain.member;

import com.ootd.with.domain.enumtype.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BanServiceImpl implements BanService {

    private final BanRepository banRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<Ban> findAllByMemberId(Long memberId) {
        return banRepository.findAllByMemberId(memberId);
    }

    @Override
    public Ban save(Long memberId, Long bannedMemberId) {
        // null 인 경우는 없다
        Member member = memberRepository.findById(memberId).orElse(null);
        Member bannedMember = memberRepository.findById(bannedMemberId).orElse(null);

        if(bannedMember.getStatusType() == StatusType.DELETED) {
            return null;
        }

        Ban ban = Ban.builder()
                .member(member)
                .bannedMember(bannedMember)
                .build();
        return banRepository.save(ban);
    }

    @Override
    public void delete(Long banId) {
        // ban, member 둘 다 null 인 경우 고려할 필요 없음

        Ban ban = banRepository.findById(banId).orElse(null);
        Member member = memberRepository.findById(ban.getMember().getId()).orElse(null);
        member.getBanList().remove(ban);  // 양방향 처리
        banRepository.deleteById(banId);
    }
}
