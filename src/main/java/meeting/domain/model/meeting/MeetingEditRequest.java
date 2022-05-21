package meeting.domain.model.meeting;

import meeting.domain.model.meeting.organizer.ClientBrowser;
import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;

/**
 * ミーティング編集依頼
 */
public class MeetingEditRequest {

    @Valid
    MeetingNumber meetingNumber;

    @Valid
    Subject subject;

    @Valid
    Memo memo;

    @Valid
    Schedules schedules;

    @Valid
    ClientBrowser organizerTerminal;

    public MeetingEditRequest(@Valid MeetingNumber meetingNumber,
                              @Valid Subject subject,
                              @Valid Memo memo,
                              @Valid Schedules schedules,
                              @Valid ClientBrowser clientBrowser) {
        this.meetingNumber = meetingNumber;
        this.subject = subject;
        this.memo = memo;
        this.schedules = schedules;
        this.organizerTerminal = clientBrowser;
    }

    @Deprecated
    MeetingEditRequest() {
    }

    public MeetingNumber meetingNumber() {
        return meetingNumber;
    }

    public Subject subject() {
        return subject;
    }

    public Memo memo() {
        return memo;
    }

    public Schedules schedules() {
        return schedules;
    }

    public ClientBrowser organizerTerminal() {
        return organizerTerminal;
    }

    public void addEmptySchedule() {
        schedules = schedules.addNew();
    }

    public void removeSchedule(int index) {
        schedules = schedules.remove(index);
    }
}
