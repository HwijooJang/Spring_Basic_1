package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
        // 전체 실행을 한다면 실행순서가 랜덤으로 잡혀 findByName과 findAll에 사용한 spring1, spring2 라는 답이 같이 잡히기 때문에
        // 중간에 클리어를 시켜주는 로직문을 작성해야 한다.
        // AfterEach를 사용을 하려면 MemoryMemberRepository.java에서 clearStore 로직을 작성해 주어야 한다.

    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result); // Assertions를 임포트 하여 사용한다. 임포트 하지 않으면 assertThat앞에 Assertions. 을 달아주면 된다
                                              // Assertions를 사용하는 이유는 중간에 에러가 나는지 확인하려고 사용을 하는것이다.
    }
    @Test
    public void fingByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get(); // assertThat과 같이 spring1과 member1이 일치하기 때문에 정상작동 하는것을 확인할수 있다.

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);

    }
    // TTD : 테스트 주도 개발, 테스트 코드를 먼저 짠 후에 개발을 시작하는 것 (현 로직은 TTD가 아니다.)
}
