package cm.pep.timeTable.service;

import cm.pep.timeTable.domain.user.User;
import cm.pep.timeTable.domain.user.UserFactory;
import cm.pep.timeTable.domain.user.UserID;
import cm.pep.timeTable.dto.LoginUserDto;
import cm.pep.timeTable.dto.RegisterUserDto;
import cm.pep.timeTable.mapper.UserMapper;
import cm.pep.timeTable.util.data.UserData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final UserFactory   userFactory;
    private UserData userData;
    @Transactional
    public UUID registerUser(RegisterUserDto registerUserDto) {
        return Optional.ofNullable(registerUserDto)
                .map(mapper::fromDtoToData)
                .map(userFactory::registerUser)
                .map(User::getId)
                .map(UserID::toUuid)
                .orElseThrow();

    }

    public void loginUser(LoginUserDto loginUserDto) {
        Optional.ofNullable(loginUserDto)
                .map(mapper::fieldToExtract1)
                .map(userFactory::loginUser)
                .orElseThrow();
    }

}
