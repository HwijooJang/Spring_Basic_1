package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    } // 가장 기본적인 컨트롤러로 처음 시작할때 GetMapping을 통해서 만든다.
}
