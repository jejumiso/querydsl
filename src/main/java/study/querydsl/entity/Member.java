package study.querydsl.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String nickname; //노출됨 = 대화명 = 로그인id
    private String password;

    private String google_id = "";
    private String facebook_id = "";



    private String username; //실명 , 노출되지 않음
    private int age = 0;         //나이
    private String gender = ""; //  man,woman
    private String phone_number = "";

    private int point = 0; // 수익포인트 1000포인트 = 1000원
    private int coin = 0;  //잔여 coin   = 1천원 = 900coin

    private Date connection_date; // 마지막 접속일 // 마지막 수정일과는 다름 마지막 수정일은 관리자가 건드려서 될수도 있으니....
    private int connection_count = 0; // 접속횟수
    private boolean connection_is = false; //접속유무
    
    //최초 가입일 : 자동생성
    //마지막 수정일 : 자동생성

    @Lob
    private String photo_title = ""; //대표사진
    @Lob
    private String photos = ""; //사진목록

    //알림설정
    private boolean alarm_chatting = false;      //채팅요청알람 수신여부
    private boolean alram_note = false;          //쪽지수신여부
    private boolean alram_one_to_one = false;    //1:1대화요청





    //수익(point)계좌정보 = bj
    private boolean isbj = false;          //bj등록 여부
    private Long play_second = 0l;         //활동시간(초)
    private int bj_popularity_ranking = -1; // -1 : 미등록 / 0:랭킹없음(최초등록) / 1~100위
    private int bj_profit_ranking = -1; // -1 : 미등록 / 0:랭킹없음(최초등록) / 1~100위
    private String bank = "";//은행
    private String account = "";//계좌번호
    private String registration_num = ""; //주민등록번호
    private String depositor = "";//예금주

    //추천인/등급
    private String recommender = "";
    private String playclass = "친구";   // bj등급 1등급(월1백이상): coin*2, 2등급(월50이상): coin*1.5, 3등급(월10이상/1회이상결제): coin*1.0  ,
    private String bjclass = "친구";     // bj등급 1등급(오픈맴버): coin*2, 2등급(열성회원): coin*1.5, 3등급: coin*1.0

    private Boolean is_activate = true; //활성화 유무 : 접속 1개월 지난거 off

    private Boolean unregister = false; //회원탈퇴 = 고객이 탈퇴를 하면 true가 되고 탈퇴신청 1개월후 모든 기록 삭제.
    private Date unregister_data; //회원탈퇴신청일



    public Member(String nickname){
        this("", nickname,0,"",null);
    }

    public Member(String nickname ,int age, Team team){
        this(nickname, nickname,age,"",team);
    }

    public Member(String username, String nickname,int age, String recommender, Team team){
        this.username = username;
        this.nickname = nickname;
        this.age = age;
        this.recommender = recommender;
        if(team != null){
            changeTeam(team);
        }
    }

    public Member(String username, String nickname, String password ,int age, String recommender, Team team){
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.age = age;
        this.recommender = recommender;
        if(team != null){
            changeTeam(team);
        }
    }

    public Member(String username, String nickname, String password ,int age, String photo_title, String recommender,  Team team){
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.age = age;
        this.photo_title = photo_title;
        this.recommender = recommender;
        if(team != null){
            changeTeam(team);
        }
    }

    public Member(String username, String nickname, String password ,int age, String photo_title, String recommender, Boolean isbj, Team team){
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.age = age;
        this.photo_title = photo_title;
        this.recommender = recommender;
        this.isbj = isbj;
        if(team != null){
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    public void changePhotos(String photos){
        this.photos = photos;
    }



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;


    @OneToMany(mappedBy = "member")
    private List<Member_Coin> member_coins = new ArrayList<>();;





}
