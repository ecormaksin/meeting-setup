package meeting.presentation.meeting.input.meeting;

import meeting.domain.model.meeting.*;
import meeting.domain.model.schedule.Schedules;
import meeting.presentation.meeting.input.schedule.ScheduleInputs;
import meeting.presentation.meeting.input.schedule.construct.DurationOptions;
import meeting.presentation.meeting.input.schedule.construct.StartMinuteOptions;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class MeetingInputForm {

    @NotNull
    @Valid
    Subject subject;

    @NotNull
    @Valid
    Memo memo;

    @NotNull
    @Valid
    ScheduleInputs scheduleInputs;

    StartMinuteOptions startMinuteOptions;
    DurationOptions durationOptions;

    @Deprecated
    MeetingInputForm() {}

    public MeetingInputForm(@Valid Subject subject,
                            @Valid Memo memo,
                            @Valid ScheduleInputs scheduleInputs) {
        this.subject = subject;
        this.memo = memo;
        this.scheduleInputs = scheduleInputs;

        this.startMinuteOptions = StartMinuteOptions.initialize();
        this.durationOptions = DurationOptions.initialize();
    }

    public static MeetingInputForm empty() {
        return new MeetingInputForm(
                Subject.blank(),
                Memo.blank(),
                ScheduleInputs.initialEmptyList());
    }

    public MeetingInputForm addEmptySchedule() {
        return new MeetingInputForm(
                subject,
                memo,
                scheduleInputs.addNew());
    }

    public MeetingInputForm removeSchedule(final int index) {
        return new MeetingInputForm(
                subject,
                memo,
                scheduleInputs.remove(index));
    }

    public MeetingCreationRequest toCreationRequest(final MeetingNumber meetingNumber, final OrganizerNumber organizerNumber) {
        final Schedules schedules = scheduleInputs.toSchedules();
        return new MeetingCreationRequest(meetingNumber,
                                            subject,
                                            memo,
                                            organizerNumber,
                                            schedules,
                                            RegistrationRequestDateTime.now());
    }

    public MeetingEditRequest toEditRequest(final MeetingNumber meetingNumber, final OrganizerNumber organizerNumber) {
        final Schedules schedules = scheduleInputs.toSchedules();
        return new MeetingEditRequest(meetingNumber,
                                        subject,
                                        memo,
                                        organizerNumber,
                                        schedules,
                                        RegistrationRequestDateTime.now());
    }

    public Subject subject() {
        return subject;
    }

    public Memo memo() {
        return memo;
    }

    public ScheduleInputs scheduleInputs() {
        return scheduleInputs;
    }

    public StartMinuteOptions startMinuteOptions() {
        return startMinuteOptions;
    }

    public DurationOptions durationOptions() {
        return durationOptions;
    }
}
