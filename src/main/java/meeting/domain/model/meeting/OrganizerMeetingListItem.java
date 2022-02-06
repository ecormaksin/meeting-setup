package meeting.domain.model.meeting;

/**
 * 主催者のミーティング一覧の項目
 */
public class OrganizerMeetingListItem {
    MeetingId meetingId;

    Subject subject;

    public OrganizerMeetingListItem(MeetingId meetingId,
                                    Subject subject) {
        this.meetingId = meetingId;
        this.subject = subject;
    }

    @Deprecated
    OrganizerMeetingListItem() {
    }

    public MeetingId meetingId() {
        return meetingId;
    }

    public Subject subject() {
        return subject;
    }
}
