package ConnetcTrip.Capstone.DTO.Request.Post;

import ConnetcTrip.Capstone.Entity.Tags;

public record TagRequest(Long id, String tagName) {

    public static TagRequest of(Long id, String tagName) {
        return new TagRequest(id, tagName);
    }

    public static TagRequest from(Tags entity) {
        return new TagRequest(
                entity.getTagId(),
                entity.getTagName()
        );
    }

    public TagRequest toEntity() {
        return TagRequest.of(id, tagName);
    }
}
