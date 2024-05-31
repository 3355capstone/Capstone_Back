package ConnetcTrip.Capstone.DTO.Response.User;

import ConnetcTrip.Capstone.Entity.Gender;
import ConnetcTrip.Capstone.Entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long userId;
    private String email;
    private String password;
    private String nickname;
    private int age;
    private String country;
    private Gender gender;


    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.age = user.getAge();
        this.country = user.getNationality();
        this.gender = user.getGender();
    }
}
