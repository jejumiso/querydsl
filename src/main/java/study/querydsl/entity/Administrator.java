package study.querydsl.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id"})
public class Administrator {

    @Id
    @GeneratedValue
    @Column(name = "administrator_id")
    private Long id;
    private String nickname; //노출됨 = 대화명 = 로그인id
    private String password;


    @OneToMany(mappedBy = "administrator")
    private List<Team> teams = new ArrayList<>();

    public Administrator(String nickname, String password){
        this.nickname = nickname;
        this.password = password;
    }

}
