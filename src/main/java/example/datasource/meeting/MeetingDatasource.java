package example.datasource.meeting;

import example.application.service.meeting.MeetingRepository;
import example.domain.model.meeting.MeetingId;
import example.domain.model.meeting.MeetingSetupRequest;
import example.domain.model.meeting.Memo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public class MeetingDatasource implements MeetingRepository {
    MeetingMapper meetingMapper;

    public MeetingDatasource(MeetingMapper meetingMapper) {
        this.meetingMapper = meetingMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeetingId setup(MeetingSetupRequest meetingSetupRequest) {
        MeetingId meetingId = MeetingId.newId();

        meetingMapper.insertMeeting(
                meetingId,
                meetingSetupRequest.subject());

        Memo memo = meetingSetupRequest.memo();
        if (memo.isNotBlank()) {
            meetingMapper.insertMeetingMemo(
                    meetingId,
                    meetingSetupRequest.memo());
        }

        return meetingId;
    }
}
