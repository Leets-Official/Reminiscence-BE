package leets.reminiscence.domain.post.service;

import leets.reminiscence.domain.post.dto.PostDto;
import leets.reminiscence.domain.post.Post;
import leets.reminiscence.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post write(PostDto postDto, MultipartFile file) throws Exception {
        Post post = Post.builder().date(postDto.getDate())
                .caption(postDto.getCaption())
                .photographer(postDto.getPhotographer())
                .snsLink(postDto.getSnsLink())
                .light(postDto.getLight())
                .build();

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        //post.getDate(Date);
        post.setFileName(fileName);
        post.setFilePath("/files/" + fileName);

        return postRepository.save(post); //3.반환값을 저장한다.
    }

    public Optional<Post> postView(Integer id){
        return postRepository.findById(id);
    }

    public void postDelete(Integer id){
        postRepository.deleteById(id);
    }
}
