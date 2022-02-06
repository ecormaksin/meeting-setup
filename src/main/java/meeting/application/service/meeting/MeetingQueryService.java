package meeting.application.service.meeting;

import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.meeting.OrganizerMeetingList;
import meeting.domain.model.meeting.organizer.ClientBrowser;
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

    public OrganizerMeetingList organizerMeetingList(ClientBrowser organizerTerminal) {
        return meetingRepository.organizerMeetingList(organizerTerminal);
    }
}
