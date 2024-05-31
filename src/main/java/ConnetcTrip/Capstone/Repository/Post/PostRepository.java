package ConnetcTrip.Capstone.Repository.Post;

import ConnetcTrip.Capstone.Entity.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository{
    @EntityGraph(attributePaths = {"user"})
    Optional<Post> findWithWriterByPostId(Long id);

    Optional<Post> findByPostId(Long id);

    @Modifying
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :id")
    void updateViewCount(@Param("id") Long Id);
}
