package hello.hellospring.service;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean // Bean은 Configuration을 인식한 후 스프링 빈에 등록을 해달라는 명령어이다. 아래 로직을 호출해 스프링 빈에 등록한다.
    public MemberService memberService(){
        return new MemberService(memberRepository()); // MemberService에서 객체가 필요하기 때문에 memberRepository를 Bean을 만들고 객체에 넣어준다.
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
