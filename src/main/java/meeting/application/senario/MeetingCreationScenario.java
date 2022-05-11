package meeting.application.senario;

import meeting.application.service.meeting.MeetingRecordService;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.MeetingCreationRequest;
import org.springframework.stereotype.Service;

/**
 * ミーティング作成シナリオ
 */
@Service
public class MeetingCreationScenario {
    MeetingRecordService meetingRecordService;

    public MeetingCreationScenario(MeetingRecordService meetingRecordService) {
        this.meetingRecordService = meetingRecordService;
    }

    public MeetingNumber create(MeetingCreationRequest meetingCreationRequest) {
        return meetingRecordService.create(meetingCreationRequest);
    }
}
