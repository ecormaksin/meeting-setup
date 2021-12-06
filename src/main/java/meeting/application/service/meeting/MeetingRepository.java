package meeting.application.service.meeting;

import meeting.domain.model.meeting.MeetingCreationRequest;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.meeting.MeetingTest;

/**
 * ミーティング リポジトリ
 */
public interface MeetingRepository {
    MeetingId create(MeetingCreationRequest meetingCreationRequest);

    MeetingTest findBy(MeetingId meetingId);
}
