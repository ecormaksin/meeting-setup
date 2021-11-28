package meeting.application.senario;

import meeting.application.service.meeting.MeetingQueryService;
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
}
