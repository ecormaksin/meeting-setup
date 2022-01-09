package meeting.domain.model.meeting;

import meeting.domain.model.meeting.organizer.ClientBrowser;
import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;

/**
 * ミーティング作成依頼
 */
public class MeetingCreationRequest {

    @Valid
    Subject subject;

    @Valid
    Memo memo;

    @Valid
    Schedules schedules;

    @Valid
    ClientBrowser organizerTerminal;

    public MeetingCreationRequest(@Valid Subject subject,
                                  @Valid Memo memo,
                                  @Valid Schedules schedules,
                                  @Valid ClientBrowser clientBrowser) {
        this.subject = subject;
        this.memo = memo;
        this.schedules = schedules;
        this.organizerTerminal = clientBrowser;
    }

    @Deprecated
    MeetingCreationRequest() {
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

    public static MeetingCreationRequest empty(ClientBrowser clientBrowser) {
        MeetingCreationRequest meetingCreationRequest = new MeetingCreationRequest();
        meetingCreationRequest.schedules = Schedules.initialEmptyList();
        meetingCreationRequest.organizerTerminal = clientBrowser;
        return meetingCreationRequest;
    }
}
