package hello.hellospring.domain;

import javax.persistence.*;

@Entity // jpa 가 관리하는 entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 Id가 자동으로 추가되는 것을 identity 전략이라고 한다.
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
