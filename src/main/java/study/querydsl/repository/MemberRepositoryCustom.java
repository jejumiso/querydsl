package study.querydsl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.querydsl.dto.MemberSearchCondition;
//import study.querydsl.dto.MemberSignupCondition;
import study.querydsl.dto.MemberSignupCondition;
import study.querydsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);

    Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable);


    Page<MemberTeamDto> searchPageBestBj(MemberSearchCondition condition, Pageable pageable);

    MemberTeamDto signup(MemberSignupCondition condition);
    MemberTeamDto login(String nickname, String password);
}
