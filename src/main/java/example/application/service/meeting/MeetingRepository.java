package example.application.service.meeting;

import example.domain.model.meeting.MeetingId;
import example.domain.model.meeting.MeetingSetupRequest;

/**
 * ミーティング リポジトリ
 */
public interface MeetingRepository {

    MeetingId setup(MeetingSetupRequest meetingSetupRequest);
}
