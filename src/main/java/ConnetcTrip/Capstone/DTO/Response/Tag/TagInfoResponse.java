package ConnetcTrip.Capstone.DTO.Response.Tag;

import ConnetcTrip.Capstone.Entity.PostTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TagInfoResponse {

    private String tagName;

    public TagInfoResponse(PostTag postTag) {
        this.tagName = postTag.getActualTagName();
    }
}
