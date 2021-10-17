package example.datasource.meeting;

import example.application.service.meeting.MeetingRepository;
import example.domain.model.meeting.MeetingNumber;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Repository
public class MeetingDatasource implements MeetingRepository {
    @Override
    public MeetingNumber 新規作成準備() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        return new MeetingNumber(uuidStr.replace("-", ""));
    }
}
