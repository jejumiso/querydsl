package study.querydsl.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberSignupCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.repository.MemberRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;


//    @GetMapping("/v1/members")
//    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition){
//
////        MemberSearchCondition condition = new MemberSearchCondition();
////        condition.setNickname("memberA");
//
//
//        return memberRepository.search(condition);
//    }

    @GetMapping("/v2/members")
    public Page<MemberTeamDto> searchMemberV2(MemberSearchCondition condition, Pageable pageable ){

//        MemberSearchCondition condition = new MemberSearchCondition();
//        condition.setNickname("memberA");
//        PageRequest pageRequest = PageRequest.of(0,3);
//
//        Page<MemberTeamDto> result = memberRepository.searchPageSimple(condition, pageRequest);


        return memberRepository.searchPageSimple(condition, pageable);
    }

    @GetMapping("/v2/members_bj")
    public Page<MemberTeamDto> searchPageBestBj(MemberSearchCondition condition, Pageable pageable ){

//        MemberSearchCondition condition = new MemberSearchCondition();
//        condition.setNickname("memberA");
//        PageRequest pageRequest = PageRequest.of(0,3);
//
//        Page<MemberTeamDto> result = memberRepository.searchPageSimple(condition, pageRequest);


        return memberRepository.searchPageBestBj(condition, pageable);
    }

    @PostMapping("/signup")
    public MemberTeamDto signup(@RequestBody MemberSignupCondition condition ){

//        MemberSearchCondition condition = new MemberSearchCondition();
//        condition.setNickname("memberA");
//        PageRequest pageRequest = PageRequest.of(0,3);
//
//        Page<MemberTeamDto> result = memberRepository.searchPageSimple(condition, pageRequest);

        System.out.println("condition.getPassword()" + condition.getPassword());

        return memberRepository.signup(condition);
    }

    @PostMapping("/login")
    public MemberTeamDto login(@RequestBody LoginMemberRequest loginMemberRequest ){

//        MemberSearchCondition condition = new MemberSearchCondition();
//        condition.setNickname("memberA");
//        PageRequest pageRequest = PageRequest.of(0,3);
//
//        Page<MemberTeamDto> result = memberRepository.searchPageSimple(condition, pageRequest);

        System.out.println("loginloginlogin : " + loginMemberRequest);

        return memberRepository.login(loginMemberRequest.nickname, loginMemberRequest.password);
    }

    @Data
    static class LoginMemberRequest{
        private String nickname;
        private String password;
    }

}
;