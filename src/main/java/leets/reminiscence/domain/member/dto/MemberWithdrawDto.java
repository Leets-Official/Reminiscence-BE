package leets.reminiscence.domain.member.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class MemberWithdrawDto {
    @NotBlank(message = "비밀번호를 입력해주세요")
    String checkPassword;
}
