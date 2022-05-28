package meeting.application.service.meeting;

import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingCreationRequest;
import meeting.domain.model.meeting.MeetingEditRequest;
import meeting.domain.model.meeting.MeetingNumber;
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

    public void create(MeetingCreationRequest meetingCreationRequest) {
        meetingRepository.create(meetingCreationRequest);
    }

    public void edit(Meeting meeting, MeetingEditRequest meetingEditRequest) {
        meetingRepository.edit(meeting, meetingEditRequest);
    }

    public void delete(MeetingNumber meetingNumber) {
        meetingRepository.delete(meetingNumber);
    }
}
