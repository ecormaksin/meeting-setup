package meeting.application.service.meeting;

import meeting.domain.model.meeting.*;
import meeting.domain.model.meeting.meeting_list.MeetingList;

/**
 * ミーティング リポジトリ
 */
public interface MeetingRepository {
    void create(MeetingCreationRequest meetingCreationRequest);

    Meeting meetingOf(MeetingNumber meetingNumber);
    Meeting findBy(MeetingNumber meetingNumber);

    void edit(Meeting meeting, MeetingEditRequest meetingEditRequest);

    void delete(MeetingNumber meetingNumber);

    MeetingList search(MeetingSearchRequest meetingSearchRequest);
}
