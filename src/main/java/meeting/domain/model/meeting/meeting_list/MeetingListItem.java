package meeting.domain.model.meeting.meeting_list;

import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.meeting.Subject;

/**
 * ミーティング一覧の項目
 */
public class MeetingListItem {

    MeetingId meetingId;
    Subject subject;
    EarliestStartDateTime earliestStartDateTime;
    LatestEndDateTime latestEndDateTime;

    public MeetingListItem(MeetingId meetingId,
                           Subject subject,
                           EarliestStartDateTime earliestStartDateTime,
                           LatestEndDateTime latestEndDateTime) {
        this.meetingId = meetingId;
        this.subject = subject;
        this.earliestStartDateTime = earliestStartDateTime;
        this.latestEndDateTime = latestEndDateTime;
    }

    @Deprecated
    MeetingListItem() {
    }

    public MeetingId meetingId() {
        return meetingId;
    }

    public Subject subject() {
        return subject;
    }

    public EarliestStartDateTime earliestStartDateTime() {
        return earliestStartDateTime;
    }

    public LatestEndDateTime latestEndDateTime() {
        return latestEndDateTime;
    }
}
