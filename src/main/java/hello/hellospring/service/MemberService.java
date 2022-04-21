package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

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


}
