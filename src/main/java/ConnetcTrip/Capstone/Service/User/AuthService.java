package ConnetcTrip.Capstone.Service.User;

import ConnetcTrip.Capstone.Config.JwtTokenProvider;
import ConnetcTrip.Capstone.DTO.Request.User.AuthRequest;
import ConnetcTrip.Capstone.DTO.Request.User.JoinRequest;
import ConnetcTrip.Capstone.DTO.Response.User.AuthResponse;
import ConnetcTrip.Capstone.Entity.Auth;
import ConnetcTrip.Capstone.Entity.Role;
import ConnetcTrip.Capstone.Entity.User;
import ConnetcTrip.Capstone.Repository.User.AuthRepository;
import ConnetcTrip.Capstone.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public AuthResponse login(AuthRequest request) {
        User user = this.userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = this.jwtTokenProvider.generateAccessToken(
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), user.getPassword()));
        String refreshToken = this.jwtTokenProvider.generateRefreshToken(
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), user.getPassword()));

        if (this.authRepository.existsByUser(user)) {
            user.getAuth().updateAccessToken(accessToken);
            user.getAuth().updateRefreshToken(refreshToken);
            return new AuthResponse(user.getAuth());
        }


        Auth auth = this.authRepository.save(Auth.builder()
                .user(user)
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());

        return new AuthResponse(auth);
    }

    public Boolean signup(JoinRequest request) {
        // SAVE USER ENTITY
        if (userRepository.existsByEmail(request.getEmail())){
            return false;
        }
        request.setRole(Role.ROLE_USER);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        this.userRepository.save(request.toEntity());
        return true;
    }

    public String refreshToken(String refreshToken) {
        // CHECK IF REFRESH_TOKEN EXPIRATION AVAILABLE, UPDATE ACCESS_TOKEN AND RETURN
        if (this.jwtTokenProvider.validateToken(refreshToken)) {
            Auth auth = this.authRepository.findByRefreshToken(refreshToken).orElseThrow(
                    () -> new IllegalArgumentException("해당 REFRESH_TOKEN 을 찾을 수 없습니다.\nREFRESH_TOKEN = " + refreshToken));

            String newAccessToken = this.jwtTokenProvider.generateAccessToken(
                    new UsernamePasswordAuthenticationToken(
                            new CustomUserDetails(auth.getUser()), auth.getUser().getPassword()));
            auth.updateAccessToken(newAccessToken);
            return newAccessToken;
        }

        // IF NOT AVAILABLE REFRESH_TOKEN EXPIRATION, REGENERATE ACCESS_TOKEN AND REFRESH_TOKEN
        // IN THIS CASE, USER HAVE TO LOGIN AGAIN, SO REGENERATE IS NOT APPROPRIATE
        return null;
    }
}
