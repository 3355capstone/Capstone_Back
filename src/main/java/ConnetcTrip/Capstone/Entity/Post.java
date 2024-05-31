package ConnetcTrip.Capstone.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@ToString(exclude = "postTag")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post extends BaseTime{

    @Id
    @Column(name = "postId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "age")
    private int age;

    @Column(name = "viewCount", columnDefinition = "integer default 0", nullable = false)
    private int viewCount;

    private String filePath;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "place")
    private String place;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "userId", updatable = false)
    private User user;

    @Column(name = "createdTime", updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "updatedTime", insertable = false)
    private LocalDateTime updatedTime;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private Set<PostTag> postTag = new LinkedHashSet<>();


    public void addPostTag(PostTag postTag) {
        this.postTag.add(postTag);
        postTag.setPost(this);
    }


    public void confirmWriter(User user) {
        this.user = user;
        user.addPost(this);
    }


    public void clearPostTags() {
        this.postTag.clear();
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void updatePlace(String place) {
        this.place = place;
    }

    // 조회수 증가
    public void upViewCount() {
        this.viewCount++;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return this.getPostId() != null && this.getPostId().equals(post.getPostId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.postId);
    }
}
