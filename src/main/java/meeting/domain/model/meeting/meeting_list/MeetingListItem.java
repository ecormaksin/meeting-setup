package meeting.domain.model.meeting.meeting_list;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.Subject;

/**
 * ミーティング一覧の項目
 */
public class MeetingListItem {

    MeetingNumber meetingNumber;
    Subject subject;
    EarliestStartDateTime earliestStartDateTime;
    LatestEndDateTime latestEndDateTime;

    public MeetingListItem(MeetingNumber meetingNumber,
                           Subject subject,
                           EarliestStartDateTime earliestStartDateTime,
                           LatestEndDateTime latestEndDateTime) {
        this.meetingNumber = meetingNumber;
        this.subject = subject;
        this.earliestStartDateTime = earliestStartDateTime;
        this.latestEndDateTime = latestEndDateTime;
    }

    @Deprecated
    MeetingListItem() {
    }

    public MeetingNumber meetingNumber() {
        return meetingNumber;
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
