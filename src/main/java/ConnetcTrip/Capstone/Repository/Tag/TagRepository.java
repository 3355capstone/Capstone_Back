package ConnetcTrip.Capstone.Repository.Tag;

import ConnetcTrip.Capstone.Entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tags, Long> {

    List<Tags> findByTagNameIn(Set<String> tagName);
}
