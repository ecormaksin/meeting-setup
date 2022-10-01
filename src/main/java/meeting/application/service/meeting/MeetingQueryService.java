package meeting.application.service.meeting;

import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.MeetingSearchRequest;
import meeting.domain.model.meeting.list.MeetingList;
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

    public Meeting meetingOf(MeetingNumber meetingNumber) {
        return meetingRepository.get(meetingNumber);
    }

    public MeetingList search(MeetingSearchRequest meetingSearchRequest) {
        return meetingRepository.search(meetingSearchRequest);
    }
}
