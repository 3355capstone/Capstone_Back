package ConnetcTrip.Capstone.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class User{

    @Id
    @Column(name = "userId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "age")
    private int age;

    @Enumerated(STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "country")
    private String nationality;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Auth auth;

    
    // 회원 탈퇴 시 작성한 게시글 전부 삭제
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();



//    @Builder
//    public User(Long userId, String email, String password, String nickname, int age, Gender gender, String nationality, List<Post> postList, Role role, Auth auth) {
//        this.userId = userId;
//        this.email = email;
//        this.password = password;
//        this.nickname = nickname;
//        this.age = age;
//        this.gender = gender;
//        this.nationality = nationality;
//        this.postList = postList;
//        this.role = role;
//        this.auth = auth;
//    }

    // 연관관계 메소드
    public void addPost(Post post) {
        postList.add(post);
    }


    // 정보 수정
    public void updatePassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateAge(int age){
        this.age = age;
    }

    // 패스워드 암호화
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
        return passwordEncoder.matches(checkPassword, getPassword());
    }

    public void addUserAuthority() {
        this.role = Role.ROLE_USER;
    }

}
