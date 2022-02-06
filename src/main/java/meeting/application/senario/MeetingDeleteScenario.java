package meeting.application.senario;

import meeting.application.service.meeting.MeetingQueryService;
import meeting.application.service.meeting.MeetingRecordService;
import meeting.domain.model.meeting.*;
import org.springframework.stereotype.Service;

/**
 * ミーティング削除シナリオ
 */
@Service
public class MeetingDeleteScenario {
    MeetingRecordService meetingRecordService;
    MeetingQueryService meetingQueryService;

    public MeetingDeleteScenario(MeetingRecordService meetingRecordService,
                                 MeetingQueryService meetingQueryService) {
        this.meetingRecordService = meetingRecordService;
        this.meetingQueryService = meetingQueryService;
    }

    public Meeting meetingOf(MeetingId meetingId) {
        return meetingQueryService.meetingOf(meetingId);
    }

    public void delete(MeetingId meetingId) {
        meetingRecordService.delete(meetingId);
    }
}
