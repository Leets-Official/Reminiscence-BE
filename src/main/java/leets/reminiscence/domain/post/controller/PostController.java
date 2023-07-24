package leets.reminiscence.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import leets.reminiscence.domain.post.dto.PostDto;
import leets.reminiscence.domain.post.Post;
import leets.reminiscence.domain.post.service.PostService;
import leets.reminiscence.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Operation(summary = "사진 업로드 폼", description = "사진이 올라갑니다.", tags = { "Post Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = PostController.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Post postWriteForm(@RequestPart PostDto postDto, @RequestPart MultipartFile multipartFile, @RequestPart User user) throws Exception {
        postService.saveUser(user);
        return postService.write(postDto, multipartFile, user);
    }

    @Operation(summary = "사진 게시글 폼", description = "사진을 볼 수 있어용.", tags = { "Post Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = PostController.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/{id}")
    public Optional<Post> get(@PathVariable Integer id) {return postService.postView(id);
    }


    @Operation(summary = "사진 삭제 폼", description = "사진이 없어져용.", tags = { "Post Controller" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = PostController.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @DeleteMapping("/delete/{id}")
    public boolean postDelete(@PathVariable Integer id){

        postService.postDelete(id);
        return true;
    }

}