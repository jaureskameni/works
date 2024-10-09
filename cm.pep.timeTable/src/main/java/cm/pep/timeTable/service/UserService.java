package cm.pep.timeTable.service;

import cm.pep.timeTable.domain.user.UserEvent;
import cm.pep.timeTable.domain.user.UserFactory;
import cm.pep.timeTable.domain.user.UserID;
import cm.pep.timeTable.dto.LoginUserDto;
import cm.pep.timeTable.dto.RegisterUserDto;
import cm.pep.timeTable.dto.UserDto;
import cm.pep.timeTable.mapper.UserMapper;
import cm.pep.timeTable.security.service.JwtService;
import cm.pep.timeTable.util.data.UserData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final UserFactory   userFactory;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private UserData userData;
    @Transactional
    public UUID registerUser(RegisterUserDto registerUserDto) {
       return   Optional.ofNullable(registerUserDto)
                .map(mapper::fromDtoToData)
                .map(userFactory::registerUser)
                .map(userEvent -> {
                    // Définit le mot de passe et retourne l'userEvent mis à jour
                    userEvent.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
                    return userEvent; // Retourne l'userEvent modifié
                })
                .map(UserEvent::getId)
                .map(UserID::toUuid)
                .orElseThrow();
    }


    public UserDto loginUser(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );
        UserEvent userEvent = Optional.of(loginUserDto)
                .map(mapper::fromDtoToData)
                .map(userFactory::loginUser)
                .orElseThrow();
        return mapper.fromEntityToDto2(userEvent);
    }


}
