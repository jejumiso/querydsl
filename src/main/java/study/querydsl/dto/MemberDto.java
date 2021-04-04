package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberDto {
    private String nickname;
    private int age;
    private String bank;

    @QueryProjection
    public MemberDto(String nickname, int age){
        this.nickname = nickname;
        this.age = age;

    }

    @QueryProjection
    public MemberDto(String nickname, int age,String bank){
        this.nickname = nickname;
        this.age = age;
        this.bank = bank;

    }

}
