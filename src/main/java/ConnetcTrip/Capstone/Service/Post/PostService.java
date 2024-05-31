package ConnetcTrip.Capstone.Service.Post;

import ConnetcTrip.Capstone.DTO.Request.Post.PostSaveRequest;
import ConnetcTrip.Capstone.DTO.Request.Post.PostUpdateRequest;

public interface PostService {

    void save(PostSaveRequest postSaveRequest, String token);

    void update(Long id, PostUpdateRequest postUpdateRequest, String token);

    void delete(Long id, String token);


}
