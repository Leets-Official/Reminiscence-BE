package leets.reminiscence.domain.post.controller;

import leets.reminiscence.domain.post.dto.PostDto;
import leets.reminiscence.domain.post.Post;
import leets.reminiscence.domain.post.service.PostService;
import leets.reminiscence.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping ()
    public Post postWriteForm(@RequestPart PostDto postDto, @RequestPart MultipartFile multipartFile, @RequestPart User user) throws Exception {
        postService.saveUser(user);
        return postService.write(postDto, multipartFile, user);
    }

    @GetMapping("/{id}")
    public Optional<Post> get(@PathVariable Integer id) {return postService.postView(id);
    }


    @DeleteMapping("/delete/{id}")
    public boolean postDelete(@PathVariable Integer id){

        postService.postDelete(id);
        return true;
    }

}