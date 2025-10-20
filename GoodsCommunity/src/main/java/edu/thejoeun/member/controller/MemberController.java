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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@SessionAttributes({"loginUser"})
@Controller
public class MemberController {

    @Autowired
    MemberServiceImpl memberService;


    @GetMapping("/login")
    public String pageLogin(){
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
                        @RequestParam(required = false) String saveId, // 필수로 전달하지 않아도 되는 매개변수
                        HttpSession session,
                        HttpServletResponse res,
                        Model model,
                        RedirectAttributes ra){
        Member member = memberService.login(memberEmail, memberPassword);

        if(member == null){
            ra.addFlashAttribute("error", "이메일 또는 비밀번호가 일치하지 않습니다.");
            return "redirect:/login"; // 일치하지 않는게 맞다면 로그인 페이지로 돌려보내기
        }

        // 세션에 로그인 정보 저장 이 방법을 쓰게되면 매번 로그인정보를 코드마다 세팅
        // session.setAttribute("loginUser", member);
        SessionUtil.setLoginUser(session, member);

        // 쿠키에 사용자 정보 저장 (보안상 민감하지 않은 부분만 저장)

        Cookie userIdCookie = new Cookie("saveId", memberEmail);
        userIdCookie.setPath("/");
        // 유저 아이디를 아이디 저장이 체크되어있으면 30일간 유저 아이디 저장
        // 체크가 안되어 있으면 유저 아이디를 쿠키에 저장하지 않겠다
        /*
        체크박스에서 value 가 없을 때
        - 체크가   된 경우 : on
        - 체크가 안된 경우 : null
        아이디 저장과 같이 단순 체크는 on - null 이용해서 체크 유무 확인
        아이디를 작성 안했는데 쿠키에 저장할 이유가 없으므로 아이디값을 작성하고 아이디 저장 체크를 했을 경우만
        30일 동안 아이디 명칭을 저장하겠다
         */
        if ("on".equals(saveId)) {
       // if (userIdCookie != null && saveId.equals("on")){
            //                   60 초 60분 24시간 30일 곱하여 총 30일 유효하게 설정
            userIdCookie.setMaxAge(60 * 60 * 24 * 30); // 30일 초 단위로 지정
        } else {
            userIdCookie.setMaxAge(0); // 클라이언트 쿠키 삭제
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
