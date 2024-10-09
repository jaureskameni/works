package cm.pep.timeTable.domain.impl;

import cm.pep.timeTable.domain.user.Password;
import cm.pep.timeTable.domain.user.UserEvent;
import cm.pep.timeTable.domain.user.impl.Email;
import cm.pep.timeTable.domain.user.impl.UserFactoryImpl;
import cm.pep.timeTable.repository.UserSpringRepository;
import cm.pep.timeTable.util.data.UserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserEventFactoryImplTest {

    @InjectMocks
    private UserFactoryImpl objectUnderTest;
    @Mock
    private UserSpringRepository repository;

    @Test
    void registerUserTest() {

        String firstName = "jaures";
        String lastName = "kameni";
        LocalDate birthDay = LocalDate.of(2024,1,23);
        String password = "jaures";
        Email email = new Email("www.jaureskameni@gmail.com");

        UserData userData = UserData.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDay(birthDay)
                .email(email)
                .password(password)
                .build();
        UserEvent expectedUserEvent = mock(UserEvent.class);

        when(repository.findByEmail(userData.email())).thenReturn(Optional.empty());

        objectUnderTest.registerUser(userData);

        verify(repository)
                .save(
                        assertArg(
                                user -> {
                            assertThat(user)
                                    .returns(firstName, user1 -> user1.getFirstName().getValue())
                                    .returns(lastName, user1 -> user1.getLastName().getValue())
                                    .returns(birthDay, UserEvent::getBirthDay)
                                    .returns(email, UserEvent::getEmail)
                                    .returns(password, UserEvent::getPassword);
                        }));
    }

    @Test
    void loginUserTest() {

        UserData userData = UserData.builder()
                .password("jaures")
                .email(new Email("www.jaureskameni@gmail.com"))
                .build();
        UserEvent userEventToLogin = UserEvent.builder()
                .password(new Password("jaures"))
                .email(new Email("www.jaureskameni@gmail.com"))
                .build();

        when(repository.findByEmail(userData.email())).thenReturn(Optional.of(userEventToLogin));

        UserEvent resultUnderTest = objectUnderTest.loginUser(userData);

        assertThat(resultUnderTest.getEmail()).isEqualTo(userEventToLogin.getEmail());
        assertThat(resultUnderTest.getPassword()).isEqualTo(userEventToLogin.getPassword());
    }

}