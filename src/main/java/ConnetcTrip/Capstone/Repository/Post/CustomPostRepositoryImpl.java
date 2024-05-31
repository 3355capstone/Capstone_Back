package ConnetcTrip.Capstone.Repository.Post;

import ConnetcTrip.Capstone.DTO.Response.Post.PostInfoResponse;
import ConnetcTrip.Capstone.Entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ConnetcTrip.Capstone.Entity.QPost.post;
import static ConnetcTrip.Capstone.Entity.QPostTag.postTag;
import static ConnetcTrip.Capstone.Entity.QTags.tags;

@Slf4j
@Repository
public class CustomPostRepositoryImpl implements CustomPostRepository{

    private final JPAQueryFactory query;

    public CustomPostRepositoryImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }


    /**
     * Search Condition(해시태그 리스트) 으로 검색 후 해당 postId 리스트 구하기
     * */
    @Override
    public List<Post> searchPost(PostSearchCondition postSearchCondition) {

        List<Long> postIdList = query.select(postTag.post.postId)
                .distinct()
                .from(postTag)
                .where(eqAllTag(postSearchCondition.getTags()))
                .orderBy(postTag.post.postId.asc())
                .fetch();


        log.info("bool" + eqAllTag(postSearchCondition.getTags()));

        List<Post> postList = query.selectFrom(post)
                .where(eqAllPost(postIdList))
                .orderBy(post.createdTime.asc())
                .fetch();

        log.info("List: " + postIdList);

        return postList;
    }

    
    /**
     * 조회수가 높은 순으로 게시글 나열 후 상위 3개만 값 가져오기
     * */
    @Override
    public List<Post> searchPostByView() {

        List<Post> postList = query.selectFrom(post)
                .orderBy(post.viewCount.desc())
                .limit(3)
                .fetch();

        return postList;
    }

    

    /**
     * Boolean Expression
     * */

    private BooleanExpression eqAllTag(List<String> tag) {
        return tag != null
                ? Expressions.anyOf(tag.stream().map(this::eqTag).toArray(BooleanExpression[]::new))
                : null;
    }

    private BooleanExpression eqTag(String tagName) {
        return postTag.actualTagName.eq(tagName);
    }

    private BooleanExpression eqAllPost(List<Long> idList) {
        return idList != null
                ? Expressions.anyOf(idList.stream().map(this::eqPostId).toArray(BooleanExpression[]::new))
                : null;
    }

    private BooleanExpression eqPostId(Long id) {
        return post.postId.eq(id);
    }
}
