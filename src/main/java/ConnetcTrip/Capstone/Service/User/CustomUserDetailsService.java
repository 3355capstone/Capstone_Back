package ConnetcTrip.Capstone.Service.User;

import ConnetcTrip.Capstone.Entity.User;
import ConnetcTrip.Capstone.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(username).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserByUserId(Long userId) throws IllegalArgumentException {
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserByEmail(String email) throws IllegalArgumentException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        return new CustomUserDetails(user);
    }
}
