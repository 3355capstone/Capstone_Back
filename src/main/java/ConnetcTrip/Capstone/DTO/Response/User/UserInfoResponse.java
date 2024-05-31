package ConnetcTrip.Capstone.DTO.Response.User;

import ConnetcTrip.Capstone.Entity.Gender;
import ConnetcTrip.Capstone.Entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    private Long userId;
    private String email;
    private String nickname;
    private int age;
    private String country;
    private Gender gender;

    @Builder
    public UserInfoResponse(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.age = user.getAge();
        this.country = getCountry();
        this.gender = getGender();
    }
}
