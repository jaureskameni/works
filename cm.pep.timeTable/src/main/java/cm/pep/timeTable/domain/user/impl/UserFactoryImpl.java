package cm.pep.timeTable.domain.user.impl;

import cm.pep.timeTable.domain.user.*;
import cm.pep.timeTable.repository.UserSpringRepository;
import cm.pep.timeTable.util.data.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactoryImpl implements UserFactory {
    private final UserSpringRepository repository;

    @Override
    public UserEvent registerUser(UserData userData) {
        if (repository.findByEmail(userData.email()).isPresent()){
            throw new RuntimeException("User Already Exist");
        }
        return repository.save(
                UserEvent.builder()
                        .firstName(new FirstName(userData.firstName()))
                        .lastName(new LastName(userData.lastName()))
                        .birthDay(userData.birthDay())
                        .email(userData.email())
                        .password(new Password(userData.password()))
                        .build()
        );
    }

    @Override
    public UserEvent loginUser(UserData userData) {
        UserEvent userEvent = repository.findByEmail(userData.email())
                .orElseThrow(() -> new RuntimeException("User does not exist"));

        if (!userEvent.getPassword().equals(userData.password())) {
                throw new RuntimeException("Invalid Password");
            }
            return userEvent;
        }
}
