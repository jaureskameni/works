package cm.pep.demoSecurity.repository;

import cm.pep.demoSecurity.user.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApp, Integer> {

    Optional<UserApp> findByEmail(String email);
}
