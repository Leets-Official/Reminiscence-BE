package leets.reminiscence.domain.post.dto;

import leets.reminiscence.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Getter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    private Date date;
    private String photographer;
    private String caption;
    private String snsLink;
    private String fileName;
    private String filePath;
    private String light;
    private Integer userId;
}