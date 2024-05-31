package ConnetcTrip.Capstone.Service.Post;

import ConnetcTrip.Capstone.Config.JwtTokenProvider;
import ConnetcTrip.Capstone.DTO.Request.Post.PostSaveRequest;
import ConnetcTrip.Capstone.DTO.Request.Post.PostUpdateRequest;
import ConnetcTrip.Capstone.Entity.Post;
import ConnetcTrip.Capstone.Entity.PostTag;
import ConnetcTrip.Capstone.Entity.Tags;
import ConnetcTrip.Capstone.Exception.PostException;
import ConnetcTrip.Capstone.Exception.PostExceptionType;
import ConnetcTrip.Capstone.Repository.Post.PostRepository;
import ConnetcTrip.Capstone.Repository.Post.PostTagRepository;
import ConnetcTrip.Capstone.Repository.Tag.TagRepository;
import ConnetcTrip.Capstone.Repository.User.UserRepository;
import ConnetcTrip.Capstone.Service.Tag.TagQueryServiceImpl;
import ConnetcTrip.Capstone.global.file.Service.FileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ConnetcTrip.Capstone.Exception.PostExceptionType.POST_NOT_POUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostTagRepository postTagRepository;
    private final FileService fileService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TagRepository tagRepository;
    private final TagQueryServiceImpl tagQueryService;


    @Override
    public void save(PostSaveRequest postSaveRequest, String token) {
        Post post = postSaveRequest.toEntity();
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        log.info("userId : " + userId);
        post.confirmWriter(userRepository.findByUserId(jwtTokenProvider.getUserIdFromToken(token))
                .orElseThrow());

        postSaveRequest.uploadFile().ifPresent(
                file -> post.updateFilePath(fileService.save(file))
        );
        postRepository.save(post);
        renewTagsFromList(postSaveRequest.tagNames(), post);
    }

    @Override
    public void update(Long id, PostUpdateRequest postUpdateRequest, String token) {
        postTagRepository.bulkDeleteByPostId(id);

        var post = postRepository.findById(id).orElseThrow(() ->
                new PostException(POST_NOT_POUND));

        checkAuthority(post, PostExceptionType.NOT_AUTHORITY_UPDATE_POST, token);


        postUpdateRequest.title().ifPresent(post::updateTitle);
        postUpdateRequest.content().ifPresent(post::updateContent);
        postUpdateRequest.place().ifPresent(post::updatePlace);

        if (post.getFilePath() != null) {
            fileService.delete(post.getFilePath());
        }

        postUpdateRequest.updateFile().ifPresentOrElse(
                multipartFile -> post.updateFilePath(fileService.save(multipartFile)),
                () -> post.updateFilePath(null)
        );

        post.clearPostTags();

        renewTagsFromList(postUpdateRequest.tagNames(), post);

    }

    @Override
    public void delete(Long id, String token) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new PostException(PostExceptionType.POST_NOT_POUND));

        checkAuthority(post, PostExceptionType.NOT_AUTHORITY_DELETE_POST, token);


        if (post.getFilePath() != null) {
            fileService.delete(post.getFilePath());//기존에 올린 파일 지우기
        }

        postRepository.delete(post);
    }


    private void checkAuthority(Post post, PostExceptionType postExceptionType, String token) {
        if (!post.getUser().getUserId().equals(jwtTokenProvider.getUserIdFromToken(token)))
            throw new PostException(postExceptionType);
    }


    private Set<PostTag> renewTagsFromList(List<String> tagList, Post post) {
        var set = new HashSet<>(tagList);

        log.info("set : " + set);
        var existingTagsSet = tagQueryService.tagListByTagNames(set);

        var tags = new HashSet<PostTag>();

        for (String actualTagName : set) {
            existingTagsSet.stream()
                    .filter(h -> h.getTagName().equalsIgnoreCase(actualTagName))
                    .findFirst()
                    .ifPresentOrElse(h -> tags.add(PostTag.of(actualTagName, post, h)),
                            () -> tags.add(PostTag.of(
                                    actualTagName,
                                    post,
                                    tagRepository.save(Tags.of(actualTagName)))));
        }
        return tags;
    }


    public void updateViewCount(Long id) {
        postRepository.updateViewCount(id);
    }
}
