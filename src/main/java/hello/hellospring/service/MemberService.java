package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // 이거를 해줘야 자바문법을 스프링컨테이너에 인식을 하게 만든다. (컴포넌트 스캔과 의존관계 설정)
@Component // 서비스가 아니고 Component로 해도된다. 하지만 Service안에 Component가 등록되어 있기때문에 Service를 사용해도 되고 그 반대도 된다.
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired // Controller와 연결하기 위해 Autowired를 사용해서 연결해준다.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository; // 이 부분은 테스트 후 실행을 클리어 시키기 위한 로직
    }

    // 회원가입
    public Long join(Member member) {


        // 이 처럼 Optional을 사용해도 되지만 위처럼 사용하면 깔끔하다.
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다");
//        });

        validateDuplicateMember(member); // 알트 + 쉬프트 + M 키로 Extract Method를 사용한 것이다. // 중복 회원 검증
        memberRepository.save(member);
       return member.getId();

    }

    private void validateDuplicateMember(Member member) { // 같은 이름이 있는 중복 회원X
        memberRepository.findByName(member.getName()).ifPresent(m -> { // 컨트롤 + 알트 + v 으로 하면 옵셔널이 생성된다.
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }); // 람다식과 IllegalStateException을 사용해서 중복하는 회원을 걸러낸다. (null이 아니라 값이 있으면 동작한다)
    }

    // 전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();

    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
        // 옵셔널을 사용해서 Id를 통해 한명을 찾아낸다.

}
