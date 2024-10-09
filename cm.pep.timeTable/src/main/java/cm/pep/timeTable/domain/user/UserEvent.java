package cm.pep.timeTable.domain.user;

import cm.pep.timeTable.domain.event.Event;
import cm.pep.timeTable.domain.user.impl.Email;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_user")
@AllArgsConstructor
public class UserEvent implements UserDetails {

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

  @Enumerated(EnumType.STRING)
  private Role role;

  @Builder.Default
  @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "event_creator")
  private Set<Event> events = new HashSet<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return email.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public String getPassword() {
    return password.getValue();
  }

  public void setPassword(String password) {
    this.password.setValue(password);
  }
}
