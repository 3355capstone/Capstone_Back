package ConnetcTrip.Capstone.DTO.Request.Post;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public record PostUpdateRequest(
        Optional<String> title,
        Optional<String> content,
        Optional<MultipartFile> updateFile,
        Optional<String> place,
        List<String> tagNames) {
}
