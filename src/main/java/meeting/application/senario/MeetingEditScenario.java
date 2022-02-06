package meeting.application.senario;

import meeting.application.service.meeting.MeetingQueryService;
import meeting.application.service.meeting.MeetingRecordService;
import meeting.domain.model.meeting.*;
import org.springframework.stereotype.Service;

/**
 * ミーティング編集シナリオ
 */
@Service
public class MeetingEditScenario {
    MeetingRecordService meetingRecordService;
    MeetingQueryService meetingQueryService;

    public MeetingEditScenario(MeetingRecordService meetingRecordService,
                               MeetingQueryService meetingQueryService) {
        this.meetingRecordService = meetingRecordService;
        this.meetingQueryService = meetingQueryService;
    }

    public Meeting meetingOf(MeetingId meetingId) {
        return meetingQueryService.meetingOf(meetingId);
    }

    public void edit(Meeting meeting, MeetingEditRequest meetingEditRequest) {
        meetingRecordService.edit(meeting, meetingEditRequest);
    }
}
