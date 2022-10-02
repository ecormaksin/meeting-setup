package meeting.application.senario;

import meeting.application.service.meeting.MeetingQueryService;
import meeting.application.service.meeting.MeetingRecordService;
import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.MeetingRegistrationRequest;
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

    public Meeting meetingOf(MeetingNumber meetingNumber) {
        return meetingQueryService.meetingOf(meetingNumber);
    }

    public void edit(Meeting meeting, MeetingRegistrationRequest meetingEditRequest) {
        meetingRecordService.edit(meeting, meetingEditRequest);
    }

    public void delete(MeetingNumber meetingNumber) {
        meetingRecordService.delete(meetingNumber);
    }
}
