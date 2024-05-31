package ConnetcTrip.Capstone.Service.Post;

import ConnetcTrip.Capstone.DTO.Response.Post.PostInfoResponse;
import ConnetcTrip.Capstone.DTO.Response.Post.PostSearchResponse;
import ConnetcTrip.Capstone.Entity.PostSearchCondition;

import java.util.List;

public interface PostSearchService {

    PostInfoResponse getPostInfo(Long id);

    List<PostSearchResponse> getPostList();

    List<PostSearchResponse> getPostListByTag(PostSearchCondition condition);

    List<PostSearchResponse> getPostListByViewCount();
}
