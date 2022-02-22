package com.ootd.with.web.member;

import com.ootd.with.domain.enumtype.SexType;
import com.ootd.with.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/add")
    public String addMemberForm(@ModelAttribute("member") AddMemberForm form) {
        return "members/addMemberForm";
    }

    @ModelAttribute("SexTypes")
    public SexType[] sexTypes() {
        return SexType.values();
    }

    @PostMapping("/members/add")
    public String addMember(@Valid @ModelAttribute AddMemberForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }

        memberService.save(form);
        return "redirect:/";
    }

}
