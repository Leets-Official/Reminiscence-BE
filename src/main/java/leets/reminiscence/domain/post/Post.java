package leets.reminiscence.domain.post;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.*;

import java.util.Date;


@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto일 경우 자동 스퀀시 생성됨. identity가 sql임
    private Integer postId;
    private Date date;
    private String photographer;
    private String caption;
    private String snsLink;
    private String fileName;
    private String filePath;
    private String light;
}
