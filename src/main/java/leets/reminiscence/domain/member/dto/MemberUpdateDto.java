package leets.reminiscence.domain.member.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class MemberUpdateDto {
    private final Optional<String> username;
    private final Optional<String> birthday;
}
