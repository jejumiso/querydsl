package study.querydsl.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"coin", "type", "comment" })
public class Member_Bj {

    @Id
    @GeneratedValue
    @Column(name = "member_bj_id")
    private Long id;
    private int point;  // 수익 포인트 / 감점포인트
    private String type;  //최초가입 / 영상통화 / 음성통화
    private String comment;  //최초가입 / 영상통화 / 음성통화
    private int play_second; //활동시간(영상통화 60초)


}
