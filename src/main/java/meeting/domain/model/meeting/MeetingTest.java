package meeting.domain.model.meeting;

import meeting.domain.model.meeting.organizer.OrganizerTerminal;

import javax.validation.Valid;

/**
 * ミーティング
 */
public class MeetingTest {
    @Valid
    MeetingId meetingId;

    @Valid
    Subject subject;

    @Valid
    Memo memo;

    @Valid
    OrganizerTerminal organizerTerminal;

    public MeetingTest(@Valid MeetingId meetingId,
                       @Valid Subject subject,
                       @Valid Memo memo,
                       @Valid OrganizerTerminal organizerTerminal) {
        this.meetingId = meetingId;
        this.subject = subject;
        this.memo = memo;
        this.organizerTerminal = organizerTerminal;
    }

    @Deprecated
    MeetingTest() {
    }

    public MeetingId meetingId() {
        return meetingId;
    }

    public Subject subject() {
        return subject;
    }

    public Memo memo() {
        return memo;
    }

    public OrganizerTerminal organizerTerminal() {
        return organizerTerminal;
    }
}
