package hello.hellospring;

import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {

    private EntityManager em;  // JPA 방식

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    /* private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    } // 여기와 같은 방법으로 Constructer를 사용해서 DataSource를 Autowired를 해도 되고*/

   // @Autowired DataSource dataSource; 이런식으로 AutoWired를 해서 DataSource를 끌어올 수 있다.

    @Bean // Bean은 Configuration을 인식한 후 스프링 빈에 등록을 해달라는 명령어이다. 아래 로직을 호출해 스프링 빈에 등록한다.
    public MemberService memberService(){
        return new MemberService(memberRepository()); // MemberService에서 객체가 필요하기 때문에 memberRepository를 Bean을 만들고 객체에 넣어준다.
    }

    @Bean
    public MemberRepository memberRepository(){
        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource); // 기존의 코드는 손대지 않고 SpringConfig 수정 즉 Assembely만 수정을 하면 나머지 코드들은
        // 전혀 손을 대지 않아도 수정이 되는것을 볼 수 있다. 현재도 이러한 방식을 사용한다.
        // MemoryMemberRepository -> JdbcMemberRepository 로 바꾸는 방식을 진행한것이다.
       // return new JdbcTemplateMemberRepository(dataSource); // 스프링 JdbcTemplate 로 진행하는 방식이다. // JdbcTemplate로 사용을 해 간결하고 깔끔한 코드가 되는 것을 볼 수 있다.
        return new JpaMemberRepository(em); // JPA 방식
    }
}
