package com.ootd.with.web.member;

import com.ootd.with.domain.member.Ban;
import com.ootd.with.domain.member.BanService;
import com.ootd.with.domain.member.Member;
import com.ootd.with.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BanController {

    private final BanService banService;

    @GetMapping("/ban")
    public String getBanList(@Login Member loginMember, Model model) {
        if(loginMember == null) {
            return "/";
        }

        // banList 가 비어있으면 그냥 안 띄워주면 된다 -> 여기서 empty 처리 따로 하지 않음
        List<Ban> banList = banService.findAllByMemberId(loginMember.getId());
        model.addAttribute("banList", banList);
        return "";  // banList 페이지로
    }

    @PostMapping("/ban")
    public String addBan(@Login Member loginMember,
                         @ModelAttribute("bannedMemberId") Long bannedMemberId) {
        if(loginMember == null) {
            return "/";
        }

        Ban ban = banService.save(loginMember.getId(), bannedMemberId);
        if(ban == null) {
            return "";  // 탈퇴 회원이라 밴 불가 메세지
        }else {
            return "";  // 현재 페이지로 redirect? or 확인창만 띄워주기?
        }

    }

    @DeleteMapping("/ban")  // Ban 은 이력관리 하지 않으므로 바로 DELETE 사용
    public String deleteBan(@Login Member loginMember,
                            @ModelAttribute("banId") Long banId) {
        if(loginMember == null) {
            return "/";
        }

        // 밴 해제는 상대 member 의 탈퇴 상태 고려하지 않음
        banService.deleteBan(banId);
        return "";  // banList 페이지로 redirect
    }
}
