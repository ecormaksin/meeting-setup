package example.datasource.meeting;

import example.application.service.meeting.MeetingRepository;
import example.domain.model.meeting.MeetingId;
import example.domain.model.meeting.MeetingSetupRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public class MeetingDatasource implements MeetingRepository {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingId setup(MeetingSetupRequest meetingSetupRequest) {
        MeetingId meetingId = MeetingId.newId();
        return meetingId;
    }
}
