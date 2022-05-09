package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController { // @Controller를 적는 순간 스프링 컨테이너가 생기는데 거기에 MemberController를 생성하고 관리를 한다

    private final MemberService memberService; // new로 사용해도 되지만 이렇게 하면 다른쪽에서도 memberService를 가져다 쓰기 때문에 복잡해진다.
                                               // 그래서 new를 사용하지말고 스프링 컨테이너에 등록을 해놓고 사용을 하면 된다.


    @Autowired // 이게 되어있으면 memberService에 있던 거를 스프링 컨테이너에 연결을 해준다.
    // Autowired를 사용하면 MemberController가 생성이 될때 스프링 빈에 등록되어있는 객체를 넣어준다 (의존성 주입, DI)
    public MemberController(MemberService memberService) {
        this.memberService = memberService; // 생성자 주입 방식 (이것을 젤 많이 쓰고 권장한다.)

        // @Autowired private MemberService memberService; // (DI) 필드 주입 방식 (잘 쓰지 않는다.)

   /* private MemberService memberService;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;*/ // setter 주입 방식 (public으로 노출이 되어있기때문에 중간에 잘못 바꾸면 문제가 된다.)
    }

    @GetMapping("/members/new") // url로 직접 치면 GetMapping으로 한다. (주로 조회할때 사용한다.)
    public String createFoem(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") // 데이터를 입력 후 전달을 할때 주로 PostMapping으로 한다.
    public String create(MemberForm form) {
       Member member = new Member();
        member.setName(form.getName());

       // System.out.println("member = " + member.getName()); // 출력결과 확인용
        
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";

    }
}

