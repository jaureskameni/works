package cm.pep.timeTable.service;

import cm.pep.timeTable.domain.user.User;
import cm.pep.timeTable.domain.user.UserFactory;
import cm.pep.timeTable.domain.user.UserID;
import cm.pep.timeTable.dto.LoginUserDto;
import cm.pep.timeTable.dto.RegisterUserDto;
import cm.pep.timeTable.mapper.UserMapper;
import cm.pep.timeTable.repository.UserSpringRepository;
import cm.pep.timeTable.util.data.UserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService objectUnderTest;

    @Mock
    private UserMapper mapper;

    @Mock
    private UserSpringRepository repository;

    @Mock
    private UserFactory userFactory;

    @Test
    void registerUser() {

        //Given
        UUID expectedId = UUID.randomUUID();
        UserData userData = mock(UserData.class);
        UserID userID = mock(UserID.class);
        RegisterUserDto registerUserDto =mock(RegisterUserDto.class) ;
        User userToSave = mock(User.class);

        when(mapper.fromDtoToData(registerUserDto)).thenReturn(userData);
        when(userFactory.registerUser(any(UserData.class))).thenReturn(userToSave);
        when(userToSave.getId()).thenReturn(userID);
        when(userID.toUuid()).thenReturn(expectedId);

        //When
        UUID resultUnderTest = objectUnderTest.registerUser(registerUserDto);
        //Then
        assertThat(resultUnderTest).isEqualTo(expectedId);
    }

    @Test
    void loginUser() {

        //Given
        LoginUserDto loginUserDto = mock(LoginUserDto.class);
        User user = mock(User.class);
        UserData userData = mock(UserData.class);

        when(mapper.fieldToExtract1(loginUserDto)).thenReturn(userData);
        when(userFactory.loginUser(userData)).thenReturn(user);

        //When
        objectUnderTest.loginUser(loginUserDto);

    }

}