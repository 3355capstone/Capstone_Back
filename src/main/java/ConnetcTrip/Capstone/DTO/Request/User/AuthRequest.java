package ConnetcTrip.Capstone.DTO.Request.User;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
}
