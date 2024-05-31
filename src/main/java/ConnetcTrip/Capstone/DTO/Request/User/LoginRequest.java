package ConnetcTrip.Capstone.DTO.Request.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
