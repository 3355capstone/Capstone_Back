package ConnetcTrip.Capstone.DTO.Response.Post;

import ConnetcTrip.Capstone.Entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostSearchResponse {

    private Long postId;
    private String title;
    private List<String> tags;

    public PostSearchResponse(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();

        tags = post.getPostTag().stream()
                .map(i -> i.getActualTagName())
                .toList();
    }

}
