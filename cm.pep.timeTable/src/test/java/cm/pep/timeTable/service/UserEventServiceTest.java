package cm.pep.timeTable.service;

import cm.pep.timeTable.domain.user.UserEvent;
import cm.pep.timeTable.domain.user.UserFactory;
import cm.pep.timeTable.domain.user.UserID;
import cm.pep.timeTable.dto.LoginUserDto;
import cm.pep.timeTable.dto.RegisterUserDto;
import cm.pep.timeTable.dto.UserDto;
import cm.pep.timeTable.mapper.UserMapper;
import cm.pep.timeTable.repository.UserSpringRepository;
import cm.pep.timeTable.util.data.UserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserEventServiceTest {

    @InjectMocks
    UserService objectUnderTest;

    @Mock
    private UserMapper mapper;

    @Mock
    private UserSpringRepository repository;

    @Mock
    private UserFactory userFactory;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager manager;

    @Test
    void registerUser() {

        //Given
        UUID expectedId = UUID.randomUUID();
        UserData userData = mock(UserData.class);
        UserID userID = mock(UserID.class);
        RegisterUserDto registerUserDto =mock(RegisterUserDto.class) ;
        UserEvent userEventToSave = mock(UserEvent.class);
        String encoder = "encoder";

        when(mapper.fromDtoToData(registerUserDto)).thenReturn(userData);
        when(userFactory.registerUser(any(UserData.class))).thenReturn(userEventToSave);
        when(this.passwordEncoder.encode(registerUserDto.getPassword())).thenReturn(encoder);
        when(userEventToSave.getId()).thenReturn(userID);
        when(userID.toUuid()).thenReturn(expectedId);

        //When
        UUID resultUnderTest = objectUnderTest.registerUser(registerUserDto);
        //Then
        assertThat(resultUnderTest).isEqualTo(expectedId);
        verify(passwordEncoder).encode(registerUserDto.getPassword());
    }

    @Test
    void loginUser() {

        //Given
        LoginUserDto loginUserDto = mock(LoginUserDto.class);
        UserEvent userEvent = mock(UserEvent.class);
        UserData userData = mock(UserData.class);
        UserDto userDto = mock(UserDto.class);

        when(mapper.fromDtoToData(loginUserDto)).thenReturn(userData);
        when(userFactory.loginUser(userData)).thenReturn(userEvent);
        when(mapper.fromEntityToDto2(userEvent)).thenReturn(userDto);

        //When
        var result = objectUnderTest.loginUser(loginUserDto);

        verify(manager).authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword())
        );
        verify(userFactory).loginUser(userData);
        verify(mapper).fromEntityToDto2(userEvent);

        assertNotNull(result);
        assertEquals(userDto, result);

    }

}