package cm.pep.timeTable.domain.event;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
public record EventData(
        String title,
        String description,
        LocalDateTime start_time,
        LocalDateTime end_time,
        String location,
        String civility,
        List<UUID> participants
        ) {}
