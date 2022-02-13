package meeting.application.service.meeting;

import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.meeting.MeetingSearchRequest;
import meeting.domain.model.meeting.meeting_list.MeetingList;
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

    public MeetingList search(MeetingSearchRequest meetingSearchRequest) {
        return meetingRepository.search(meetingSearchRequest);
    }
}
