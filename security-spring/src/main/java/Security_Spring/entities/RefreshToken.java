package Security_Spring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "c_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "c_user_id", referencedColumnName = "c_id")
    private User user;

    @Column(name = "c_token", nullable = false, unique = true)
    private String token;

    @Column(name = "c_expiry_date", nullable = false)
    private Instant expiryDate;

    @Column(name = "c_revoked")
    public boolean revoked;
}
