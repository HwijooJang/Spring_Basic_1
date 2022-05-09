package hello.hellospring.controller;

public class MemberForm {
    private String name; // createMemberForm.html 을 통해서 입력된 name을 가져온다.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
