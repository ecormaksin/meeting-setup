package example.domain.model.meeting;

import example.domain.model.meeting.organizer.OrganizerTerminal;
import example.domain.model.schedule.Schedules;

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
    OrganizerTerminal organizerTerminal;

    public MeetingCreationRequest(@Valid Subject subject,
                                  @Valid Memo memo,
                                  @Valid Schedules schedules,
                                  @Valid OrganizerTerminal organizerTerminal) {
        this.subject = subject;
        this.memo = memo;
        this.schedules = schedules;
        this.organizerTerminal = organizerTerminal;
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

    public OrganizerTerminal organizerTerminal() {
        return organizerTerminal;
    }

    public void addEmptySchedule() {
        schedules = schedules.addNew();
    }

    public void removeSchedule(int index) {
        schedules = schedules.remove(index);
    }

    public static MeetingCreationRequest empty(@Valid OrganizerTerminal organizerTerminal) {
        MeetingCreationRequest meetingCreationRequest = new MeetingCreationRequest();
        meetingCreationRequest.schedules = Schedules.initialEmptyList();
        meetingCreationRequest.organizerTerminal = organizerTerminal;
        return meetingCreationRequest;
    }
}
