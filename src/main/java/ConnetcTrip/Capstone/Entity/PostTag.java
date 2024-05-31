package ConnetcTrip.Capstone.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Table(name = "postTag")
public class PostTag{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postMapTag")
    private Long id;

    private String actualTagName;

    @Setter
    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "postId", updatable = false)
    private Post post;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tagId")
    private Tags tag;




    private PostTag(String actualTagName, Post post, Tags tag) {
        if(!StringUtils.hasText(actualTagName)) throw new InvalidParameterException();
        if(post == null) throw new InvalidParameterException();
        if(tag == null) throw new InvalidParameterException();

        this.actualTagName = actualTagName;
        post.addPostTag(this);
        tag.addPostTag(this);
    }

    public static PostTag of(String actualTagName, Post post, Tags tag) {
        return new PostTag(actualTagName, post, tag);
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        PostTag postTag = (PostTag) o;
        return this.getId() != null && this.getId().equals(postTag.getId());
    }

    public int hastCode() {
        return Objects.hash(this.getId());
    }


}