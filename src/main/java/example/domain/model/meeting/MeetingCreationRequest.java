package example.domain.model.meeting;

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

    public MeetingCreationRequest(@Valid Subject subject, @Valid Memo memo, @Valid Schedules schedules) {
        this.subject = subject;
        this.memo = memo;
        this.schedules = schedules;
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

    public void addEmptySchedule() {
        schedules = schedules.addNew();
    }

    public void removeSchedule(int index) {
        schedules = schedules.remove(index);
    }

    public static MeetingCreationRequest empty() {
        MeetingCreationRequest meetingCreationRequest = new MeetingCreationRequest();
        meetingCreationRequest.schedules = Schedules.initialEmptyList();
        return meetingCreationRequest;
    }
}
