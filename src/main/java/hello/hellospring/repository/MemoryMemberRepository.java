package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jdk.dynalink.beans.StaticClass;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // Null값을 감싸기 위해 Optional.ofNullable 을 사용한다
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream().filter(member -> member.getName().equals(name)).findAny(); // 람다식 로직
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // 실무에서는 List를 많이 쓴다.
    }

    public void clearStore(){
        store.clear(); // Test 코드를 작성 후 콜백을 해주는 로직으로 Test문에 사용하려면 작성해야한다.
    }
}
