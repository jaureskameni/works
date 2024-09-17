package cm.pep.timeTable.domain.user;

import cm.pep.timeTable.domain.event.Event;
import cm.pep.timeTable.domain.user.impl.Email;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Builder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_user")
@AllArgsConstructor
public class User {

  @Builder.Default
  @EmbeddedId
  @AttributeOverride(name = "value", column = @Column(name = "c_id"))
  private UserID id = new UserID();

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "c_first_name"))
  private FirstName firstName;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "c_last_name"))
  private LastName lastName;

  @Column(name = "c_birth_day")
  private LocalDate birthDay;

  @Embedded
  @AttributeOverride(name = "email", column = @Column(name = "c_email"))
  private Email email;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "c_password_new"))
  private Password password;

  @Builder.Default
  @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "event_creator")
  private Set<Event> events = new HashSet<>();

}
