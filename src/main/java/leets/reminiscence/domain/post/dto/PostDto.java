package leets.reminiscence.domain.post.dto;

import leets.reminiscence.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;


@Getter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    private Date date;
    @Column(nullable = true)
    private String photographer;
    private String caption;
    private String snsLink;
    private String fileName;
    private String filePath;
    private String light;
    private Integer userId;
}