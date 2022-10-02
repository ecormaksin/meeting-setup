package meeting.presentation.meeting.input.meeting;

import meeting.domain.model.meeting.*;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.presentation.meeting.input.schedule.ScheduleInputAppendable;
import meeting.presentation.meeting.input.schedule.ScheduleInputs;
import meeting.presentation.meeting.input.schedule.construct.DurationOptions;
import meeting.presentation.meeting.input.schedule.construct.StartMinuteOptions;

import javax.validation.Valid;

public class MeetingInputForm {

    @Valid
    Subject subject;

    @Valid
    Memo memo;

    @Valid
    ScheduleInputs scheduleInputs;

    StartMinuteOptions startMinuteOptions;
    DurationOptions durationOptions;

    @Deprecated
    MeetingInputForm() {}

    public MeetingInputForm(Subject subject,
                            Memo memo,
                            ScheduleInputs scheduleInputs) {
        this.subject = subject;
        this.memo = memo;
        this.scheduleInputs = scheduleInputs;

        this.startMinuteOptions = StartMinuteOptions.initialize();
        this.durationOptions = DurationOptions.initialize();
    }

    public static MeetingInputForm newForm() {
        return new MeetingInputForm(
                Subject.blank(),
                Memo.blank(),
                ScheduleInputs.initialEmptyList());
    }

    public ScheduleInputAppendable scheduleAppendable() {
        return scheduleInputs.appendable();
    }

    public MeetingInputForm addEmptySchedule() {
        return new MeetingInputForm(
                subject,
                memo,
                scheduleInputs.addNew());
    }

    public MeetingInputForm removeSchedule(int index) {
        return new MeetingInputForm(
                subject,
                memo,
                scheduleInputs.remove(index));
    }

    public MeetingRegistrationRequest toRegistrationRequest(MeetingNumber meetingNumber,
                                                            OrganizerNumber organizerNumber) {
        Schedules schedules = scheduleInputs.toSchedules();
        return new MeetingRegistrationRequest(meetingNumber,
                subject,
                memo,
                schedules,
                organizerNumber,
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
