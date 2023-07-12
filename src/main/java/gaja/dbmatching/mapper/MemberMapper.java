package gaja.dbmatching.mapper;
import gaja.dbmatching.domain.Member;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface MemberMapper {
    void save(Member member);
}
