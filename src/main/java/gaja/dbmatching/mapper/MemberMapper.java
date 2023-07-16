package gaja.dbmatching.mapper;
import gaja.dbmatching.domain.Member;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface MemberMapper {

    //로그인
    Member getMemberAccount(String email);

    // 회원가입
    void save(Member member);
}
