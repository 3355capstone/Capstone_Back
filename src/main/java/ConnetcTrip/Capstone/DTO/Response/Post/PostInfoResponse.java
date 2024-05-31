package ConnetcTrip.Capstone.DTO.Response.Post;

import ConnetcTrip.Capstone.DTO.Response.Tag.TagInfoResponse;
import ConnetcTrip.Capstone.DTO.Response.User.UserInfoResponse;
import ConnetcTrip.Capstone.Entity.Post;
import ConnetcTrip.Capstone.Entity.PostTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class PostInfoResponse {

    private Long postId;
    private String title;
    private String content;
    private String filePath;
    private int viewCount;
    private String place;

    private List<String> tags;

    private UserInfoResponse userInfo;

    public PostInfoResponse(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.filePath = post.getFilePath();
        this.place = post.getPlace();
        this.viewCount = post.getViewCount();

        this.userInfo = new UserInfoResponse(post.getUser());

        tags = post.getPostTag().stream()
                .map(i -> i.getActualTagName())
                .toList();
    }

}
