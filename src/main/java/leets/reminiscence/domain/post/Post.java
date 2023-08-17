package leets.reminiscence.domain.post;

import javax.persistence.*;

import leets.reminiscence.domain.user.User;
import lombok.*;

import java.util.Date;


@Entity
@Data
@Builder
@Table(name = "POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto일 경우 자동 스퀀시 생성됨. identity가 sql임
    private int postId;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User userid;

    private Date date;
    private String photographer;
    private String caption;
    private String snsLink;
    private String fileName;
    private String filePath;
    private String light;


}