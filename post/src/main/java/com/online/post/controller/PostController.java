package com.online.post.controller;

import com.online.post.entity.Post;
import com.online.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PostController {

    @Autowired
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //글 작성 페이지
    @GetMapping("/post/write") // "localhost:8888/post/write"로 접근시 연결됨.(메인 화면)
    public String postWriteForm(){

        return "postwrite"; //생성한 html 파일이름을 연결하는 것
    }

    //글 작성 완료 확인 페이지
    @PostMapping("/post/writepro") // html 파일에 입력된, 메인 화면에서 내용 입력 후 제출(action)된 주소 (내용전달 페이지)
    public String postWritePro (Post post, MultipartFile file) throws Exception{ //정보가 많아질 경우에는 테이블 entity 에 저장된 정보를 불러오는 형식으로 매개 변수를 설정한다.
        // so, @entity가 적힌 class인 Board를 매개 변수로 적는다.
        postService.write(post, file);
        return "postwritepro";
    }

    //글 불러오는 페이지
    @GetMapping("/post/view") //localhost:8888/post/view?id=1
    public String postView(Model model, Integer id) {

        model.addAttribute("post", postService.postView(id));
        return "postview";
    }

    //글 삭제하기
    @GetMapping("/post/delete") //localhost:8888/post/delete?id=1
    public String postDelete(Integer id){

        postService.postDelete(id);
        //return "redirect:/post/write";
        return "postdelete"; //글 삭제 완료 페이지 연결해둠..나중에 변경
    }




}
