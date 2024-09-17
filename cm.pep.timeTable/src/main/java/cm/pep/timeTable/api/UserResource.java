package cm.pep.timeTable.api;

import cm.pep.timeTable.dto.LoginUserDto;
import cm.pep.timeTable.dto.RegisterUserDto;
import java.util.UUID;

import cm.pep.timeTable.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserResource implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<Void> loginUser(LoginUserDto loginUserDto) {
        userService.loginUser(loginUserDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<UUID> registerUser(RegisterUserDto registerUserDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.registerUser(registerUserDto));
    }

}
