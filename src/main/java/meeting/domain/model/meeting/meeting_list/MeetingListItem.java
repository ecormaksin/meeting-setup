package meeting.domain.model.meeting.meeting_list;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.Subject;
import meeting.domain.model.schedule.SearchedSchedules;

/**
 * ミーティング一覧の項目
 */
public class MeetingListItem {

    MeetingNumber meetingNumber;
    Subject subject;
    SearchedSchedules searchedSchedules;

    @Deprecated
    MeetingListItem() {}

    public MeetingListItem(MeetingNumber meetingNumber,
                           Subject subject,
                           SearchedSchedules searchedSchedules) {
        this.meetingNumber = meetingNumber;
        this.subject = subject;
        this.searchedSchedules = searchedSchedules;
    }

    public MeetingNumber meetingNumber() {
        return meetingNumber;
    }

    public Subject subject() {
        return subject;
    }

    public SearchedSchedules searchedSchedules() {
        return searchedSchedules;
    }
}
