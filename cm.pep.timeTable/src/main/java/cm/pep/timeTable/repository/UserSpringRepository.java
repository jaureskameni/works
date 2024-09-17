package cm.pep.timeTable.repository;

import cm.pep.timeTable.domain.user.impl.Email;
import cm.pep.timeTable.domain.user.User;
import cm.pep.timeTable.domain.user.UserID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSpringRepository extends JpaRepository<User, UserID> {
    Optional<User> findByEmail(Email email);

    Optional<User> findByFirstName(String firstName);
}
