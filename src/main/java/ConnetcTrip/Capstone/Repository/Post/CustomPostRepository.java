package ConnetcTrip.Capstone.Repository.Post;

import ConnetcTrip.Capstone.DTO.Response.Post.PostInfoResponse;
import ConnetcTrip.Capstone.Entity.Post;
import ConnetcTrip.Capstone.Entity.PostSearchCondition;
import ConnetcTrip.Capstone.Entity.PostTag;
import ConnetcTrip.Capstone.Entity.Tags;

import javax.swing.*;
import java.util.List;

public interface CustomPostRepository {
    List<Post> searchPost(PostSearchCondition postSearchCondition);

    List<Post> searchPostByView();
}
