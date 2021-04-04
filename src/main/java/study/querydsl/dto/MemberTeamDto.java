package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberTeamDto {

    private Long memberId;
    private String nickname;
    private int age;
    private Long teamId;
    private String teamname;
    private String photo_title;
    private String photos;



    @QueryProjection
    public MemberTeamDto(Long memberId, String nickname, int age, Long teamId, String teamname, String photo_title, String photos) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.age = age;
        this.teamId = teamId;
        this.teamname = teamname;
        this.photo_title = photo_title;
        this.photos = photos;
    }
}
