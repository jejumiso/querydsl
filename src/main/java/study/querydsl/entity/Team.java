package study.querydsl.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//앱 이름..
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "teamname"})
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String teamname;

    public Team(String teamname){
        this.teamname = teamname;
    }


    public void changeAdministrator(Administrator administrator) {
        this.administrator = administrator;
        administrator.getTeams().add(this);
    }

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator_id")
    private Administrator administrator;


}
