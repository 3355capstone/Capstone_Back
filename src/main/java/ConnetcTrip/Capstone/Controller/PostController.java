package ConnetcTrip.Capstone.Controller;

import ConnetcTrip.Capstone.DTO.Request.Post.PostSaveRequest;
import ConnetcTrip.Capstone.DTO.Request.Post.PostUpdateRequest;
import ConnetcTrip.Capstone.Entity.PostSearchCondition;
import ConnetcTrip.Capstone.Service.Post.PostSearchServiceImpl;
import ConnetcTrip.Capstone.Service.Post.PostServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://14.52.103.211:3000")
public class PostController {

    private final PostServiceImpl postService;
    private final PostSearchServiceImpl postSearchService;

    /**
     * 게시글 저장
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post")
    public void save(@Valid @RequestBody PostSaveRequest postSaveRequest, @RequestHeader("Authorization") String accessToken) {
        postService.save(postSaveRequest, accessToken);
    }

    /**
     * 게시글 수정
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/post/{postId}")
    public void update(@PathVariable("postId") Long postId, @RequestBody PostUpdateRequest postUpdateRequest,
                       @RequestHeader("Authorization") String accessToken) {
        postService.update(postId, postUpdateRequest, accessToken);
    }

    /**
     * 게시글 삭제
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/post/{postId}")
    public void delete(@PathVariable("postId") Long postId, @RequestHeader("Authorization") String accessToken) {
        postService.delete(postId, accessToken);
    }


    /**
     * 게시글 상세 조회
     * */
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getInfo(@PathVariable("postId") Long postId,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {

        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + postId.toString() + "]")) {
                this.postService.updateViewCount(postId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + postId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        }
        else {
            this.postService.updateViewCount(postId);
            Cookie newCookie = new Cookie("postView", "[" + postId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24); 								// 쿠키 시간
            response.addCookie(newCookie);
        }

        return ResponseEntity.ok(postSearchService.getPostInfo(postId));
    }

    /**
     * 게시글 전체 조회
     */
    @GetMapping("/post/list")
    public ResponseEntity<?> getPostList() {
        return ResponseEntity.ok(postSearchService.getPostList());
    }


    /**
     * 해시태크로 게시글 조회
     */
    @PostMapping("/post/tag")
    public ResponseEntity<?> getPostByTag(@RequestBody PostSearchCondition condition) {
        log.info("condition" + condition);
        return ResponseEntity.ok(postSearchService.getPostListByTag(condition));
    }

    /**
     * 메인페이지의 인기 게시글 목록 조회 (조회수 높은 순)
     */
    @GetMapping("post/view")
    ResponseEntity<?> getPostByViewCount() {
        return ResponseEntity.ok(postSearchService.getPostListByViewCount());
    }
}
