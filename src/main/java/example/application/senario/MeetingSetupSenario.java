package example.application.senario;

import example.application.service.meeting.MeetingRecordService;
import example.domain.model.meeting.MeetingId;
import example.domain.model.meeting.MeetingSetupRequest;
import org.springframework.stereotype.Service;

/**
 * ミーティング設定シナリオ
 */
@Service
public class MeetingSetupSenario {
    MeetingRecordService meetingRecordService;

    public MeetingSetupSenario(MeetingRecordService meetingRecordService) {
        this.meetingRecordService = meetingRecordService;
    }

    public MeetingId setup(MeetingSetupRequest meetingSetupRequest) {
        MeetingId meetingId = meetingRecordService.setup(meetingSetupRequest);
        return meetingId;
    }
}
