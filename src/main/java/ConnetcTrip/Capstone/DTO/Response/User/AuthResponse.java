package ConnetcTrip.Capstone.DTO.Response.User;

import ConnetcTrip.Capstone.Entity.Auth;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String tokenType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public AuthResponse(Auth entity) {
        this.tokenType = entity.getTokenType();
        this.accessToken = entity.getAccessToken();
        this.refreshToken = entity.getRefreshToken();
    }
}
