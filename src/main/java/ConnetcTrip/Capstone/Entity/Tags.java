package ConnetcTrip.Capstone.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@ToString(exclude = "postTag")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tag")
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagId")
    private Long tagId;

    @Column(name="tagName", nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private final Set<PostTag> postTag = new LinkedHashSet<>();


    public Tags(String tagName) {
        this.tagName = tagName;
    }

    public static Tags of(String tagName) {
        return new Tags(tagName);
    }

    public void addPostTag(PostTag postTag) {
        this.postTag.add(postTag);
        postTag.setTag(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Tags tag = (Tags) o;
        return this.getTagId() != null && this.getTagId().equals(tag.getTagId());
    }

    public int hashCode() {
        return Objects.hash(this.getTagId());
    }
}
