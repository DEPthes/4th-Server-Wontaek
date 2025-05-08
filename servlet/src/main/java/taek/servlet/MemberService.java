package taek.servlet;

public class MemberService {

    private MemberRepository memberRepository;

//    // setter 주입
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 생성자로 주입
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void register() {
        System.out.println("회원 등록 중...");
        memberRepository.save();
    }
}
