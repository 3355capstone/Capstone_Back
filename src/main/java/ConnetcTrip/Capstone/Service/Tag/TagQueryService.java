package ConnetcTrip.Capstone.Service.Tag;

import ConnetcTrip.Capstone.Entity.Tags;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface TagQueryService {

    Set<Tags> tagListByTagNames(Set<String> tagNames);
}
