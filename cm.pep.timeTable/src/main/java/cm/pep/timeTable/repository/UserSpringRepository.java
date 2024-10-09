package cm.pep.timeTable.repository;

import cm.pep.timeTable.domain.user.UserEvent;
import cm.pep.timeTable.domain.user.impl.Email;
import cm.pep.timeTable.domain.user.UserID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSpringRepository extends JpaRepository<UserEvent, UserID> {
    Optional<UserEvent> findByEmail(Email email);

    Optional<UserEvent> findByFirstName(String firstName);
}
