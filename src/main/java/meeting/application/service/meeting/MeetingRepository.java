package meeting.application.service.meeting;

import meeting.domain.model.meeting.*;
import meeting.domain.model.meeting.organizer.ClientBrowser;

/**
 * ミーティング リポジトリ
 */
public interface MeetingRepository {
    MeetingId create(MeetingCreationRequest meetingCreationRequest);

    Meeting meetingOf(MeetingId meetingId);

    void edit(Meeting meeting, MeetingEditRequest meetingEditRequest);

    void delete(MeetingId meetingId);

    OrganizerMeetingList organizerMeetingList(ClientBrowser organizerTerminal);
}
