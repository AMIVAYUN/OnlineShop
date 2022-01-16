package com.pipe09.OnlineShop.Controller;

import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Dto.memberForm;
import com.pipe09.OnlineShop.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String Login(){

        return "MemberShip/login";
    }
    @GetMapping("/newMember")
    public String Enter_NewMember(Model model){
        model.addAttribute("form",new memberForm());
        return "MemberShip/newMember";
    }
    @PostMapping("/members/new")
    public String create(memberForm memberForm, BindingResult bindingResult){
        log.info("new member come");
        if(bindingResult.hasErrors()){
            return "redirect:/";
        }
        Member newMember=Member.createMember(memberForm.getMember_form_ID(),memberForm.getName(),memberForm.getPhone_Num(), memberForm.getPwd());
        memberService.save(newMember);

        return "redirect:/";
    }
}
