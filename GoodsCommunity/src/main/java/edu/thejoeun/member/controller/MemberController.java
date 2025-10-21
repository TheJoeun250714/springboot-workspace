package edu.thejoeun.member.controller;

import edu.thejoeun.common.util.SessionUtil;
import edu.thejoeun.member.model.dto.Member;
import edu.thejoeun.member.model.service.MemberService;
import edu.thejoeun.member.model.service.MemberServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@SessionAttributes({"loginUser"})
@Controller
public class MemberController {

    @Autowired
    MemberServiceImpl memberService;

    @GetMapping("/")
    public String pageMain(){
       // return "main";
        return "index";
    }

    //쿠키 설정할 때 아이디 저장 안되면 가장먼저하는 작업
    // @CookieView 와 Model 은 필요 없음!!!
    @GetMapping("/login")
    public String pageLogin(
    ){
        return "pages/login";
    }

    @GetMapping("/member/myPage")
    public String getMyPage(){
        return  "pages/myPage";
    }


    // GPT or AI 경우 Model 로 모든 것을 처리함
    // Model 과 RedirectAttributes 구분해서 결과값을 클라이언트 전달
    @PostMapping("/login")
    public String login(@RequestParam String memberEmail,
                        @RequestParam String memberPassword,
                        @RequestParam(required = false) String saveIdCheck, // 필수로 전달하지 않아도 되는 매개변수
                        HttpSession session,
                        HttpServletResponse res,
                        Model model,
                        RedirectAttributes ra){
        Member member = memberService.login(memberEmail, memberPassword);
        if(member == null){
            ra.addFlashAttribute("error", "이메일 또는 비밀번호가 일치하지 않습니다.");
            return "redirect:/login";
        }
        SessionUtil.setLoginUser(session, member);

        // 쿠키에 사용자 정보 저장 (보안상 민감하지 않은 부분만 저장)

        Cookie userIdCookie = new Cookie("saveId", memberEmail);
        userIdCookie.setPath("/");

        if (userIdCookie != null && saveIdCheck.equals("on")){
            userIdCookie.setMaxAge(60 * 60 * 24 * 30);
        } else {
            userIdCookie.setMaxAge(0);
        }
        res.addCookie(userIdCookie);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse res){
        SessionUtil.invalidateLoginUser(session);

        Cookie userIdCookie = new Cookie("saveId", null);
        userIdCookie.setMaxAge(0);
        userIdCookie.setPath("/");
        res.addCookie(userIdCookie);
        return "redirect:/"; //로그아웃 선택시 모든 쿠키 데이터 지우고 메인으로 돌려보내기
    }
}
