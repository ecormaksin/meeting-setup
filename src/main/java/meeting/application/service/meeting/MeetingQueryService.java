package meeting.application.service.meeting;

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
}
