package ConnetcTrip.Capstone.Repository.Post;

import ConnetcTrip.Capstone.Entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM PostTag pt WHERE pt.post.postId = :postId")
    void bulkDeleteByPostId(Long postId);
}
