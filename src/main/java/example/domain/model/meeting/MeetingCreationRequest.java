package example.domain.model.meeting;

import example.domain.model.schedule.Schedule;
import example.domain.model.schedule.Schedules;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
    List<Schedule> scheduleList;

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

    public List<Schedule> scheduleList() {
        return scheduleList;
    }

    public static MeetingCreationRequest empty() {
        MeetingCreationRequest meetingCreationRequest = new MeetingCreationRequest();
        meetingCreationRequest.subject = new Subject("hogehoge");
        meetingCreationRequest.schedules = Schedules.initialEmptyList();
        meetingCreationRequest.scheduleList = Schedules.initialEmptyList().asList();
        return meetingCreationRequest;
    }
}
