package meeting.application.service.meeting;

import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.meeting.MeetingCreationRequest;
import org.springframework.stereotype.Service;

/**
 * ミーティングの記録
 */
@Service
public class MeetingRecordService {
    MeetingRepository meetingRepository;

    public MeetingRecordService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public MeetingId create(MeetingCreationRequest meetingCreationRequest) {
        return meetingRepository.create(meetingCreationRequest);
    }
}
