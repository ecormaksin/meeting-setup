package meeting.application.senario;

import meeting.application.service.meeting.MeetingQueryService;
import org.springframework.stereotype.Service;

/**
 * ミーティング検索シナリオ
 */
@Service
public class MeetingSearchScenario {
    MeetingQueryService meetingQueryService;

    public MeetingSearchScenario(MeetingQueryService meetingQueryService) {
        this.meetingQueryService = meetingQueryService;
    }
}
