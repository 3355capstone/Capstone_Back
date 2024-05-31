package ConnetcTrip.Capstone.DTO.Request.Post;

import ConnetcTrip.Capstone.Entity.Gender;
import ConnetcTrip.Capstone.Entity.Post;
import ConnetcTrip.Capstone.Entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Builder
public record PostSaveRequest(
        @NotBlank(message = "제목을 입력해주세요") String title,
        @NotBlank(message = "내용을 입력해주세요") String description,
        Optional<MultipartFile> uploadFile,
        int age,
        String place,
        Gender gender,
        List<String> tagNames) {

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.description)
                .age(this.age)
                .place(this.place)
                .gender(this.gender)
                .build();
    }
}
