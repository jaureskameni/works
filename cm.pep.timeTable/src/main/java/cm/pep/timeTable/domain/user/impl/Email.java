package cm.pep.timeTable.domain.user.impl;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.apache.commons.validator.routines.EmailValidator;

@Builder
@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class Email {

    private String email;

    public Email(String email) {

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }

}
