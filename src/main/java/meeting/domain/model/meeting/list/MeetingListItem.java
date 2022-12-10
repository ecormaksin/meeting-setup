package meeting.domain.model.meeting.list;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.Subject;
import meeting.domain.model.schedule.Schedules;

/**
 * ミーティング一覧の項目
 */
public class MeetingListItem {

    MeetingNumber meetingNumber;
    Subject subject;
    Schedules schedules;

    @Deprecated
    MeetingListItem() {
    }

    public MeetingListItem(MeetingNumber meetingNumber,
                           Subject subject,
                           Schedules schedules) {
        this.meetingNumber = meetingNumber;
        this.subject = subject;
        this.schedules = schedules;
    }

    public static MeetingListItem of(MeetingNumber meetingNumber,
                                     Subject subject,
                                     Schedules schedules) {
        return new MeetingListItem(meetingNumber, subject, schedules);
    }

    public MeetingNumber meetingNumber() {
        return meetingNumber;
    }

    public Subject subject() {
        return subject;
    }

    public Schedules schedules() {
        return schedules;
    }
}
