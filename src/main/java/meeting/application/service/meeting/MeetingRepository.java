package meeting.application.service.meeting;

import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingCreationRequest;
import meeting.domain.model.meeting.MeetingEditRequest;
import meeting.domain.model.meeting.MeetingId;

/**
 * ミーティング リポジトリ
 */
public interface MeetingRepository {
    MeetingId create(MeetingCreationRequest meetingCreationRequest);
    Meeting meetingOf(MeetingId meetingId);
    void edit(Meeting meeting, MeetingEditRequest meetingEditRequest);
    void delete(MeetingId meetingId);
}
