package meeting.domain.model.meeting;

import meeting.domain.model.meeting.organizer.ClientBrowser;
import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;

/**
 * ミーティング削除依頼
 */
public class MeetingDeleteRequest {

    @Valid
    MeetingId meetingId;

    @Valid
    Subject subject;

    @Valid
    Memo memo;

    @Valid
    Schedules schedules;

    @Valid
    ClientBrowser organizerTerminal;

    public MeetingDeleteRequest(@Valid MeetingId meetingId,
                                @Valid Subject subject,
                                @Valid Memo memo,
                                @Valid Schedules schedules,
                                @Valid ClientBrowser clientBrowser) {
        this.meetingId = meetingId;
        this.subject = subject;
        this.memo = memo;
        this.schedules = schedules;
        this.organizerTerminal = clientBrowser;
    }

    @Deprecated
    MeetingDeleteRequest() {
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

    public Schedules schedules() {
        return schedules;
    }

    public ClientBrowser organizerTerminal() {
        return organizerTerminal;
    }
}
