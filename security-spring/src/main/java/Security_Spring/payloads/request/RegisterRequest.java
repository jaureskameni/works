package Security_Spring.payloads.request;

import Security_Spring.enums.Role;
import Security_Spring.validation.StrongPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message ="firstname is required" )
    private String firstname;

    @NotBlank(message ="lastname is required" )
    private String lastname;

    @NotBlank(message ="email is required" )
    private String email;

    @NotBlank(message = "password is required")
    @StrongPassword
    private String password;

    @NotNull
    private Role role;
}
