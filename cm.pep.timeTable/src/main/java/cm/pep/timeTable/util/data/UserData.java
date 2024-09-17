package cm.pep.timeTable.util.data;

import cm.pep.timeTable.domain.user.impl.Email;
import lombok.Builder;

import java.time.LocalDate;


@Builder
public record UserData(
        String firstName, String lastName, LocalDate birthDay, Email email, String password
){}
