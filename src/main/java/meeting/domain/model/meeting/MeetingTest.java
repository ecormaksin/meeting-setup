package meeting.domain.model.meeting;

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

    public MeetingTest(@Valid MeetingId meetingId,
                       @Valid Subject subject,
                       @Valid Memo memo) {
        this.meetingId = meetingId;
        this.subject = subject;
        this.memo = memo;
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
}
