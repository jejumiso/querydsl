package study.querydsl.dto;

import lombok.Data;

@Data
public class MemberSignupCondition {
    private String nickname;
    private String password;
    private String teamname;
}
