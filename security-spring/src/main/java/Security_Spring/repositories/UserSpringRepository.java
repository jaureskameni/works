package Security_Spring.repositories;

import Security_Spring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSpringRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String email);
}
