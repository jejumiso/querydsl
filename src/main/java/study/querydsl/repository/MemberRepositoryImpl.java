package study.querydsl.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import study.querydsl.dto.MemberSearchCondition;
//import study.querydsl.dto.MemberSignupCondition;
import study.querydsl.dto.MemberSignupCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static study.querydsl.entity.QMember.*;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;


public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

//    public MemberRepositoryImpl(JPAQueryFactory queryFactory) {  //김영한선생님이 아래꺼라 아무거나 해도 상관없댄 이해못함..구동되면 되지머..
//        this.queryFactory = queryFactory;
//    }
    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Autowired TeamRepository teamRepository;
    @Autowired MemberRepository memberRepository;

    @Override
    public List<MemberTeamDto> search(MemberSearchCondition condition) {
        return queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberID"),
                        member.nickname,
                        member.age,
                        team.id.as("teamId"),
                        team.teamname,
                        member.photo_title,
                        member.photos

                )).from(member)
                .leftJoin(member.team, team)
                .where(
                        nicknameEq(condition.getNickname())
                )
                .fetch();
    }

    @Override
    public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
        QueryResults<MemberTeamDto> results = queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberID"),
                        member.nickname,
                        member.age,
                        team.id.as("teamId"),
                        team.teamname,
                        member.photo_title,
                        member.photos

                )).from(member)
                .leftJoin(member.team, team)
                .where(
                        nicknameEq(condition.getNickname())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MemberTeamDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MemberTeamDto> searchPageBestBj(MemberSearchCondition condition, Pageable pageable) {
        QueryResults<MemberTeamDto> results = queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberID"),
                        member.nickname,
                        member.age,
                        team.id.as("teamId"),
                        team.teamname,
                        member.photo_title,
                        member.photos
                )).from(member)
                .leftJoin(member.team, team)
                .where(
                        member.isbj.eq(true)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MemberTeamDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public MemberTeamDto signup(MemberSignupCondition condition) {

//        Team findTeam = teamRepository.findById(condition.getTeamid()).get();

        List<Team> findTeams = teamRepository.findByteamname("teamA");
        Team findTeam = findTeams.stream().findFirst().get();

        Member newMember = new Member(condition.getNickname(), condition.getNickname(), condition.getPassword(), 20, "", "", findTeam);

        Member savedMember = memberRepository.save(newMember);

        return new MemberTeamDto(savedMember.getId(), savedMember.getNickname(), 20, 5L, "", "", "");
    }

    @Override
    public MemberTeamDto login(String nickname, String password) {

        Member findMember = queryFactory.select(member)
                .from(member)
                .where(member.nickname.eq(nickname).and(member.password.eq(password)))
                .fetchOne();


        if(findMember != null){
            System.out.println("loginloginloginloginloginloginYYYYYYYYYYYYYYY : " + nickname + "_______" + password);
            return new MemberTeamDto(findMember.getId(), nickname, findMember.getAge(), 5L, "", "", "");
        }
        else {
            System.out.println("loginloginloginloginloginloginNNNNNNNNNNNNNNN : " + nickname + "_______" + password);
            return null;
        }


    }

//    @Override
//    public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
//        return null;
//    }


    private Predicate nicknameEq(String nickname) {
        //StringUtils.isEmpty()

        return  nickname == null ? null : member.nickname.eq(nickname);
    }
}
