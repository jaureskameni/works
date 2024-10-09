package cm.pep.timeTable.mapper;

import cm.pep.timeTable.domain.user.impl.Email;
import cm.pep.timeTable.domain.user.UserEvent;
import cm.pep.timeTable.dto.LoginUserDto;
import cm.pep.timeTable.dto.RegisterUserDto;
import cm.pep.timeTable.dto.UserDto;
import cm.pep.timeTable.util.data.UserData;
import org.apache.commons.validator.routines.EmailValidator;
import org.mapstruct.*;


@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "firstName", source = "firstName.value")
    @Mapping(target = "lastName", source = "lastName.value")
    @Mapping(target = "email", source = "email", qualifiedByName = "emailToString")
    @Mapping(target = "birthDay", source = "birthDay")
    RegisterUserDto fromEntityToDto(UserEvent userEvent);

    @Named("emailToString")
    default String emailToString(Email email){
        return email != null ? email.getEmail() : null;
    }

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "birthDay", target = "birthDay")
    @Mapping(source = "email", target = "email", qualifiedByName = "StringToEmail")
    @Mapping(source = "password", target = "password")
    UserData fromDtoToData(RegisterUserDto registerUserDto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "firstName.value", target = "firstName")
    @Mapping(source = "lastName.value", target = "lastName")
    UserDto fromEntityToDto2(UserEvent userEvent);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "email", target = "email", qualifiedByName = "StringToEmail")
    @Mapping(source = "password", target = "password")
    UserData fromDtoToData(LoginUserDto loginUserDto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email", qualifiedByName = "StringToEmail")
    UserData fieldToExtract1(LoginUserDto loginUserDto);

    @Named("StringToEmail")
    default Email StringToEmail(String email){
        if (email == null || !EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        return new Email(email);
    }


}
