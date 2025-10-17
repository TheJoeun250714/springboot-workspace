package edu.thejoeun.myblog.controller;

// @RestController 는 ReactJs 할 때 까지 안녕~ !
// 백엔드로직 작업 우선 진행 필요할 수 있다.


import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    public String getIndexPage(){
        return "/";
    }

    public String getMemberPage(){
        return "member";
    }
}
