package meeting.application.service.meeting;

import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.MeetingRegistrationRequest;
import meeting.domain.model.meeting.MeetingSearchRequest;
import meeting.domain.model.meeting.list.MeetingList;

/**
 * ミーティング リポジトリ
 */
public interface MeetingRepository {
    void create(MeetingRegistrationRequest meetingCreationRequest);

    Meeting get(MeetingNumber meetingNumber);

    void edit(Meeting meeting, MeetingRegistrationRequest meetingEditRequest);

    void delete(MeetingNumber meetingNumber);

    MeetingList search(MeetingSearchRequest meetingSearchRequest);
}
