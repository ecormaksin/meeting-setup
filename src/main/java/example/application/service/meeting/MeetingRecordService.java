package example.application.service.meeting;

import example.domain.model.meeting.MeetingId;
import example.domain.model.meeting.MeetingCreationRequest;
import org.springframework.stereotype.Service;

/**
 * ミーティングの記録
 */
@Service
public class MeetingRecordService {
    MeetingRepository meetingRepository;

    public MeetingRecordService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public MeetingId create(MeetingCreationRequest meetingCreationRequest) {
        return meetingRepository.create(meetingCreationRequest);
    }
}
