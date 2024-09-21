package cm.pep.timeTable.mapper;

import cm.pep.timeTable.domain.event.EventData;
import cm.pep.timeTable.domain.user.UserID;
import cm.pep.timeTable.dto.AddEventDto;
import cm.pep.timeTable.dto.ParticipantDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target= "title", source = "title")
    @Mapping(target= "description", source = "description")
    @Mapping(target= "start_time", source = "starTime")
    @Mapping(target= "end_time", source = "endTime")
    @Mapping(target= "location", source = "location")
    @Mapping(target= "civility", source = "civility")
    @Mapping(target = "participants", source = "participants")
        EventData FromDtoToData(AddEventDto eventDto);

    default List<UUID> map(List<ParticipantDto> participantDtos) {
        return participantDtos
                .stream().map(ParticipantDto::getId).toList();
    }

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "value", source = "id")
   UserID FromUuidToUserId(ParticipantDto participantDto);



}
