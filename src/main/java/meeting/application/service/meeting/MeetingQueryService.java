package meeting.application.service.meeting;

import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingId;
import org.springframework.stereotype.Service;

/**
 * ミーティングの参照
 */
@Service
public class MeetingQueryService {
    MeetingRepository meetingRepository;

    MeetingQueryService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public Meeting meetingOf(MeetingId meetingId) {
        return meetingRepository.meetingOf(meetingId);
    }
}
