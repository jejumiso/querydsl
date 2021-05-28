package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberDto;
import study.querydsl.entity.*;
import study.querydsl.repository.MemberRepository;

import javax.persistence.EntityManager;

import java.util.List;

import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@SpringBootTest
@Transactional
@Rollback(false)
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before(){
        queryFactory = new JPAQueryFactory(em);

        Administrator administratorA = new Administrator("관리자","pass1234");
        em.persist(administratorA);

        Team teamA = new Team("random_video_chat");
        Team teamB = new Team("teamB");
        teamA.changeAdministrator(administratorA);
        teamB.changeAdministrator(administratorA);
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10 , teamA);
        Member member2 = new Member("member2", 10 , teamA);
        Member member3 = new Member("member3", 10 , teamB);
        Member member4 = new Member("member4", 10 , teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        Member_Coin member_coin1 = new Member_Coin(3000,"가입축하금","가입축하금",member1);
        Member_Coin member_coin2 = new Member_Coin(3000,"활동보너스","활동보너스",member1);
        Member_Coin member_coin3 = new Member_Coin(5000,"활동보너스","활동보너스",member1);
        Member_Coin member_coin4 = new Member_Coin(10000,"계좌이체","활동보너스",member1);
        Member_Coin member_coin5 = new Member_Coin(-9000,"방송시청","활동보너스",member1);
        em.persist(member_coin1);
        em.persist(member_coin2);
        em.persist(member_coin3);
        em.persist(member_coin4);
        em.persist(member_coin5);

        Member_Coin member_coin10 = new Member_Coin(3000,"가입축하금","가입축하금",member2);
        Member_Coin member_coin11 = new Member_Coin(3000,"bj 방송 수익금","bj",member2);
        Member_Coin member_coin12 = new Member_Coin(5000,"bj 방송 수익금","bj",member2);
        em.persist(member_coin10);
        em.persist(member_coin11);
        em.persist(member_coin12);


        for (int i = 5; i < 10; i++){
            em.persist(new Member("member" + i, 10 + i , teamB));
            em.persist(new Member("memberA", 10 + i , teamB));
        }




        //초기화
        em.flush();
        em.clear();


    }

    @Test
    public void startJPQL(){
        Member member4 = new Member("test", 10 , null);
        em.persist(member4);
    }

    @Test
    public void testtest(){

//        Team teamB = new Team("teamB");
//        em.persist(teamB);

//        Member member1 = new Member("member1", 10 , null);
//        em.persist(member1);
//
//        Member_Coin member_coin1 = new Member_Coin(3000,"가입축하금","가입");
//        //Member_Coin member_coin1 = new Member_Coin(3000,"가입축하금","가입축하금",null);
//        em.persist(member_coin1);
    }

//    @Test
//    public void startQuerydsl(){
//
//         Member findMember = queryFactory
//                 .select(member)
//                 .from(member)
//                 .where(member.nickname.eq("member1"))
//                 .fetchOne();
//////
////        assertThat(findMember.getNickname()).isEqualTo("member1");
//
//    }
//    @Test
//    public void startQuerydsl_team(){
//
//        Team findTeam = queryFactory
//                .select(team)
//                .from(team)
//                .where(team.teamname.eq("teamB"))
//                .fetchOne();
//        System.out.println("findTeam = " + findTeam);
//////
////        assertThat(findMember.getNickname()).isEqualTo("member1");
//
//    }


    @Test
    public void findDtoByQueryProjection(){
        List<MemberDto> result = queryFactory
                .select(new QMemberDto(member.nickname, member.age,member.bank))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result){
            System.out.println("memberDto.memberDto = " + memberDto);
        }

    }

    @Test
    public void repositorycustom(){



        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setNickname("memberA");
        List<MemberTeamDto> result = memberRepository.search(condition);

        for (MemberTeamDto memberTeamDto : result){
            System.out.println("memberTeamDto = " + memberTeamDto);
        }
    }

    @Test
    public void join() throws Exception {
        List<Member> members = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.teamname.eq("teamB"))
                .fetch();

        for (Member member : members){
            System.out.println("result.member = " + member.getNickname());
        }



    }



}
