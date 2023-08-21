package leets.reminiscence.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import leets.reminiscence.domain.post.controller.PostController;
import leets.reminiscence.domain.user.dto.UserSignUpDto;
import leets.reminiscence.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "이메일 회원가입 폼", description = "사진을 볼 수 있어요.", tags = { "User Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = UserController.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @Operation(summary = "로그인을 위한 jwt 토큰 테스트 폼", description = "회원가입 할 시 생성되는 authorization 토큰을 대조하여 맞는지 확인합니다.", tags = { "User Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = UserController.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }
}