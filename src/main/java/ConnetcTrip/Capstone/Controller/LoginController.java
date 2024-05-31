package ConnetcTrip.Capstone.Controller;

import ConnetcTrip.Capstone.DTO.Request.User.AuthRequest;
import ConnetcTrip.Capstone.DTO.Request.User.JoinRequest;
import ConnetcTrip.Capstone.DTO.Response.User.AuthResponse;
import ConnetcTrip.Capstone.Service.User.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "http://14.52.103.211:3000")
public class LoginController {

    private final AuthService authService;


    /// 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest requestDto) {
        AuthResponse responseDto = this.authService.login(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> singUp(@RequestBody JoinRequest requestDto) {
        if (!this.authService.signup(requestDto)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 중복 오류");
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // 토큰 갱신
    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("REFRESH_TOKEN") String refreshToken) {
        String newAccessToken = this.authService.refreshToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(newAccessToken);
    }





}
