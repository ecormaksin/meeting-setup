package meeting.application.service.meeting;

import meeting.domain.model.meeting.*;
import meeting.domain.model.meeting.meeting_list.MeetingList;

/**
 * ミーティング リポジトリ
 */
public interface MeetingRepository {
    MeetingId create(MeetingCreationRequest meetingCreationRequest);

    Meeting meetingOf(MeetingId meetingId);

    void edit(Meeting meeting, MeetingEditRequest meetingEditRequest);

    void delete(MeetingId meetingId);

    MeetingList search(MeetingSearchRequest meetingSearchRequest);
}
