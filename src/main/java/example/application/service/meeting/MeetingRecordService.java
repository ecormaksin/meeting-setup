package example.application.service.meeting;

import example.domain.model.meeting.MeetingId;
import example.domain.model.meeting.MeetingSetupRequest;
import org.springframework.stereotype.Service;

/**
 * ミーティングサービス
 */
@Service
public class MeetingRecordService {
    MeetingRepository meetingRepository;

    public MeetingRecordService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public MeetingId setup(MeetingSetupRequest meetingSetupRequest) {
        return meetingRepository.setup(meetingSetupRequest);
    }
}
