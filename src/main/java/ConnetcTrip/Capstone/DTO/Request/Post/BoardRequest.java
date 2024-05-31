package ConnetcTrip.Capstone.DTO.Request.Post;

import ConnetcTrip.Capstone.Entity.Gender;
import ConnetcTrip.Capstone.Entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequest {

    private String title;

    private String description;

    private Gender gender;

    private int age;

    private String place;

    private List<String> tags;

    private String filePath;

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.description)
                .age(this.age)
                .place(this.place)
                .filePath(this.filePath)
                .gender(this.gender)
                .build();
    }
}
