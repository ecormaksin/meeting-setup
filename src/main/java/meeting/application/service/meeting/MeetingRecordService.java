package meeting.application.service.meeting;

import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.MeetingRegistrationRequest;
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

    public void create(MeetingRegistrationRequest meetingCreationRequest) {
        meetingRepository.create(meetingCreationRequest);
    }

    public void edit(Meeting meeting, MeetingRegistrationRequest meetingEditRequest) {
        meetingRepository.edit(meeting, meetingEditRequest);
    }

    public void delete(MeetingNumber meetingNumber) {
        meetingRepository.delete(meetingNumber);
    }
}
