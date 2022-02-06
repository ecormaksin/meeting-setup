package meeting.application.senario;

import meeting.application.service.meeting.MeetingQueryService;
import meeting.domain.model.meeting.OrganizerMeetingList;
import meeting.domain.model.meeting.organizer.ClientBrowser;
import org.springframework.stereotype.Service;

/**
 * 主催者のミーティング一覧取得シナリオ
 */
@Service
public class OrganizerMeetingListGetScenario {
    MeetingQueryService meetingQueryService;

    public OrganizerMeetingListGetScenario(MeetingQueryService meetingQueryService) {
        this.meetingQueryService = meetingQueryService;
    }

    public OrganizerMeetingList organizerMeetingList(ClientBrowser organizerTerminal) {
        return meetingQueryService.organizerMeetingList(organizerTerminal);
    }
}
