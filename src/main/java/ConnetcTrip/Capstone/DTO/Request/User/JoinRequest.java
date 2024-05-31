package ConnetcTrip.Capstone.DTO.Request.User;


import ConnetcTrip.Capstone.Entity.Gender;
import ConnetcTrip.Capstone.Entity.Role;
import ConnetcTrip.Capstone.Entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    private Role role;
    private String email;
    private String password;
    private String passwordCheck;
    private String nickname;
    private int age;
    private Gender gender;
    private String nationality;

    public User toEntity() {
        return User.builder()
                .role(this.role)
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .age(this.age)
                .gender(this.gender)
                .nationality(this.nationality)
                .build();

    }

    // 비밀번호 암호화
    public User toEntity(String encodedPassword) {
        return User.builder()
                .email(this.email)
                .password(encodedPassword)
                .nickname(this.nickname)
                .age(this.age)
                .gender(this.gender)
                .nationality(this.nationality)
                .build();
    }
}
