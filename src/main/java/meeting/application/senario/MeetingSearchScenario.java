package meeting.application.senario;

import meeting.application.service.meeting.MeetingQueryService;
import meeting.domain.model.meeting.MeetingSearchRequest;
import meeting.domain.model.meeting.meeting_list.MeetingList;
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

    public MeetingList search(MeetingSearchRequest meetingSearchRequest) {
        return meetingQueryService.search(meetingSearchRequest);
    }
}
