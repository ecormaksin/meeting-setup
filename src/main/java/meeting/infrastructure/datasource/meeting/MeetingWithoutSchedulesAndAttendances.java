package meeting.infrastructure.datasource.meeting;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.Memo;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.meeting.Subject;

public class MeetingWithoutSchedulesAndAttendances {

    MeetingNumber meetingNumber;

    Subject subject;

    Memo memo;

    OrganizerNumber organizerNumber;

    @Deprecated
    MeetingWithoutSchedulesAndAttendances() {}

    public MeetingWithoutSchedulesAndAttendances(
            final MeetingNumber meetingNumber,
            final Subject subject,
            final Memo memo,
            final OrganizerNumber organizerNumber) {

        this.meetingNumber = meetingNumber;
        this.subject = subject;
        this.memo = memo;
        this.organizerNumber = organizerNumber;
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

    public OrganizerNumber organizerNumber() {
        return organizerNumber;
    }
}
