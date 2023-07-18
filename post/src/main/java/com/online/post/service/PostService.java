package com.online.post.service;

import com.online.post.entity.Post;
import com.online.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class PostService {

    @Autowired //2.이걸로 아래와 같이 간단하게 표기가능
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    //글 작성 처리
    public void write(Post post, MultipartFile file) throws Exception {

        //MultipartFile로 매개 변수로 이미지 파일도 받는다. static files을 만들어 이미지 파일을 저장한다.
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);//경로와 이름을 지정하여 FILE이라는 껍떼기를 만들어서 저장함
        file.transferTo(saveFile);

        //post.getDate(Date);
        post.setFilename(fileName);
        post.setFilepath("/files/" + fileName);


        postRepository.save(post); //3.반환값을 저장한다.
    }

    //게시물 리스트 처리
    /*public List<Post> postList(){
        return postRepository.findAll();
    }
*/
    //게시물 읽어오기 처리
    public Post postView(Integer id){
        return postRepository.findById(id).get();
    }

    //특정 게시물 삭제

    public void postDelete(Integer id){


        postRepository.deleteById(id);
    }


}
