package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// 순수 자바문법으로 진행하는 단위테스트가 더 좋은 테스트 문법이다.

@SpringBootTest
@Transactional // DB는 기본적으로 Transaction 이라는 기능이 있어 이 애노테이션을 달면 실행을 하기 때문에 AfterEach 등을 사용하지 않아도 롤백을 시켜준다. (DB에 실제 데이터를 반영을 시키지 않는다)
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
//Given
        Member member = new Member();
        member.setName("spring"); // DB에 아이디가 있기 때문에 예외 에러가 난다.
                                  // DB를 삭제 후 다시 실행을 하면 테스트가 정상작동 된다.
//When
        Long saveId = memberService.join(member);

//Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }
    @Test
    public void 중복_회원_예외() throws Exception {

//Given
        Member member1 = new Member();
        member1.setName("spring"); // set을 입력하므로써 동일 값에서 예외 처리를 할건지 물어본다.
        Member member2 = new Member();
        member2.setName("spring");

//When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 예외가 발생하면 이게 입력이 된다.
    }
}