package ConnetcTrip.Capstone.Service.Tag;

import ConnetcTrip.Capstone.Entity.Tags;
import ConnetcTrip.Capstone.Repository.Tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TagQueryServiceImpl implements TagQueryService {

    private final TagRepository tagRepository;


    @Override
    public Set<Tags> tagListByTagNames(Set<String> tagNames) {
        return new HashSet<>(
                tagRepository.findByTagNameIn(
                        tagNames.stream().collect(Collectors.toUnmodifiableSet())
                )
        );
    }

}
