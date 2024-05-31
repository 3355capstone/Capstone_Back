package ConnetcTrip.Capstone.Service.Post;

import ConnetcTrip.Capstone.DTO.Response.Post.PostInfoResponse;
import ConnetcTrip.Capstone.DTO.Response.Post.PostSearchResponse;
import ConnetcTrip.Capstone.Entity.PostSearchCondition;
import ConnetcTrip.Capstone.Exception.PostException;
import ConnetcTrip.Capstone.Repository.Post.PostRepository;
import ConnetcTrip.Capstone.Repository.Tag.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static ConnetcTrip.Capstone.Exception.PostExceptionType.POST_NOT_POUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostSearchServiceImpl implements PostSearchService{

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Override
    public PostInfoResponse getPostInfo(Long id) {
        return new PostInfoResponse(postRepository.findWithWriterByPostId(id)
                .orElseThrow(() -> new PostException(POST_NOT_POUND)));
    }

    @Override
    public List<PostSearchResponse> getPostList() {
        return postRepository.findAll().stream()
                .map(h -> new PostSearchResponse(h))
                .toList();
    }

    @Override
    public List<PostSearchResponse> getPostListByTag(PostSearchCondition condition) {
        return postRepository.searchPost(condition).stream()
                .map(h -> new PostSearchResponse(h))
                .toList();
    }


    @Override
    public List<PostSearchResponse> getPostListByViewCount() {
        return postRepository.searchPostByView().stream()
                .map(h -> new PostSearchResponse(h))
                .toList();
    }

}
