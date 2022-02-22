package com.ootd.with.web.member;

import com.ootd.with.domain.member.Ban;
import com.ootd.with.domain.member.BanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BanController {

    private final BanService banService;

    @GetMapping("/ban/{id}")
    public String getBanList(@PathVariable("id") Long memberId, Model model) {
        // banList 가 비어있으면 그냥 안 띄워주면 된다 -> 여기서 empty 처리 따로 하지 않음
        List<Ban> banList = banService.findAllByMemberId(memberId);
        model.addAttribute("banList", banList);
        return "";  // banList 페이지로
    }

    @PostMapping("/ban/{from}/{to}")
    public String addBan(@PathVariable("from") Long memberId,
                         @PathVariable("to") Long bannedMemberId) {
        Ban ban = banService.save(memberId, bannedMemberId);

        if(ban == null) {
            return "";  // 탈퇴 회원이라 밴 불가 메세지
        }else {
            return "";  // 현재 페이지로 redirect? or 확인창만 띄워주기?
        }

    }

    @DeleteMapping("/ban/{id}")  // Ban 은 이력관리 하지 않으므로 바로 DELETE 사용
    public String deleteBan(@PathVariable("id") Long banId) {
        // 밴 해제는 상대 member 의 탈퇴 상태 고려하지 않음

        banService.deleteBan(banId);
        return "";  // banList 페이지로 redirect
    }
}
