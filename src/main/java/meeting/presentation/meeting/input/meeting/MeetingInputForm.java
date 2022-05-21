package meeting.presentation.meeting.input.meeting;

import meeting.domain.model.meeting.*;
import meeting.domain.model.schedule.Schedules;
import meeting.presentation.meeting.input.schedule.ScheduleInputs;

import javax.validation.Valid;

public class MeetingInputForm {

    @Valid
    MeetingNumber meetingNumber;

    @Valid
    Subject subject;

    @Valid
    Memo memo;

    @Valid
    OrganizerNumber organizerNumber;

    @Valid
    ScheduleInputs scheduleInputs;

    @Deprecated
    MeetingInputForm() {}

    public MeetingInputForm(@Valid MeetingNumber meetingNumber,
                            @Valid Subject subject,
                            @Valid Memo memo,
                            @Valid OrganizerNumber organizerNumber,
                            @Valid ScheduleInputs scheduleInputs) {
        this.meetingNumber = meetingNumber;
        this.subject = subject;
        this.memo = memo;
        this.organizerNumber = organizerNumber;
        this.scheduleInputs = scheduleInputs;
    }

    public static MeetingInputForm empty() {
        return new MeetingInputForm(
                MeetingNumber.newNumber(),
                Subject.empty(),
                Memo.empty(),
                OrganizerNumber.newNumber(),
                ScheduleInputs.initialEmptyList());
    }

    public MeetingInputForm addEmptySchedule() {
        return new MeetingInputForm(
                meetingNumber,
                subject,
                memo,
                organizerNumber,
                scheduleInputs.addNew());
    }

    public MeetingInputForm removeSchedule(final int index) {
        return new MeetingInputForm(
                meetingNumber,
                subject,
                memo,
                organizerNumber,
                scheduleInputs.remove(index));
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

    public ScheduleInputs scheduleInputs() {
        return scheduleInputs;
    }

    public MeetingCreationRequest toCreationRequest() {
        final Schedules schedules = scheduleInputs.toDomainObject();
        return new MeetingCreationRequest(meetingNumber, subject, memo, organizerNumber, schedules);
    }
}
