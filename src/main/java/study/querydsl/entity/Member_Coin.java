package study.querydsl.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"coin", "type", "comment" })
public class Member_Coin {

    @Id @GeneratedValue
    @Column(name = "member_coin_id")
    private Long id;
    private int coin = 0;  // = 현금성
    private int coin_payment = 0;  // = 고객이 지불한 실제 금액
    private int coin_fee_admin = 0;  // = 수수료 앱 수수료
    private int coin_fee_team = 0;  // = 수수료 앱 수수료
    private int coin_fee_market_android = 0;  //
    private int coin_fee_market_iphone = 0;  //
    private int coin_fee_market_card = 0;  //

    private String type = "";  // 무료제공,계좌이체,카드결제,서비스,가입축하금등.
    private String comment = "";  //잔여 coin   = 1천원 = 900coin
    //날짜 : 자동생성

    public Member_Coin(int coin, String type, String comment) {
        this.coin =coin;
        this.type = type;
        this.comment = comment;

    }


    public Member_Coin(int coin,String type, String comment,Member member) {
        this.coin =coin;
        this.type = type;
        this.comment = comment;
        if (member!= null){
            changeMember(member);
        }
    }

    public void changeMember(Member member) {
        this.member = member;
        member.getMember_coins().add(this);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
