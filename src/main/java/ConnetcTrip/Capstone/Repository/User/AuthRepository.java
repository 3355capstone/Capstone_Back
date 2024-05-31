package ConnetcTrip.Capstone.Repository.User;

import ConnetcTrip.Capstone.Entity.Auth;
import ConnetcTrip.Capstone.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    boolean existsByUser(User user);

    Optional<Auth> findByRefreshToken(String refreshToken);
}
