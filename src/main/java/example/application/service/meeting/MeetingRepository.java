package example.application.service.meeting;

import example.domain.model.meeting.MeetingId;
import example.domain.model.meeting.MeetingCreationRequest;

/**
 * ミーティング リポジトリ
 */
public interface MeetingRepository {

    MeetingId create(MeetingCreationRequest meetingCreationRequest);
}
