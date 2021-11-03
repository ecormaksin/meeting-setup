package example.application.senario;

import example.application.service.meeting.MeetingRecordService;
import example.domain.model.meeting.MeetingId;
import example.domain.model.meeting.MeetingCreationRequest;
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

    public MeetingId create(MeetingCreationRequest meetingCreationRequest) {
        MeetingId meetingId = meetingRecordService.create(meetingCreationRequest);
        return meetingId;
    }
}
