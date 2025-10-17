package edu.thejoeun.myblog.controller;

// @RestController 는 ReactJs 할 때 까지 안녕~ !
// 백엔드로직 작업 우선 진행 필요할 수 있다.


import edu.thejoeun.myblog.model.Member;
import edu.thejoeun.myblog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String getIndexPage(){
        return "index";
    }
    @GetMapping("/member/list")
    public String getMemberPage(Model model){
        List<Member> members = memberService.selectMemberList();
        model.addAttribute("members",members);
        return "member";
    }

    @GetMapping("/member/register")
    public String getMemberRegisterPage(){
        return "member_register";
    }
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }
    @GetMapping("/member/{id}")
    public String getMemberPage(@PathVariable int id, Model model){
        return  "member-detail";
    }
    @GetMapping("/member/myPage")
    public String getMyPage(){
        return  "myPage";
    }
}
