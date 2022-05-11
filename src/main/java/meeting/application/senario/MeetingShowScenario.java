package meeting.application.senario;

import meeting.application.service.meeting.MeetingQueryService;
import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingNumber;
import org.springframework.stereotype.Service;

/**
 * ミーティング表示シナリオ
 */
@Service
public class MeetingShowScenario {
    MeetingQueryService meetingQueryService;

    public MeetingShowScenario(MeetingQueryService meetingQueryService) {
        this.meetingQueryService = meetingQueryService;
    }

    public Meeting meetingOf(MeetingNumber meetingNumber) {
        return meetingQueryService.meetingOf(meetingNumber);
    }
}
