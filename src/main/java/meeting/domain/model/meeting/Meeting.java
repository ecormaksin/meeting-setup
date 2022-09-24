package meeting.domain.model.meeting;

import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleMatchStatus;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;

/**
 * ミーティング
 */
public class Meeting {
    @Valid
    MeetingNumber meetingNumber;

    @Valid
    Subject subject;

    @Valid
    Memo memo;

    @Valid
    OrganizerNumber organizerNumber;

    @Valid
    Schedules schedules;

    @Valid
    Attendances attendances;

    @Deprecated
    Meeting() {}

    public Meeting(@Valid MeetingNumber meetingNumber,
                   @Valid Subject subject,
                   @Valid Memo memo,
                   @Valid OrganizerNumber organizerNumber,
                   @Valid Schedules schedules,
                   @Valid Attendances attendances) {
        this.meetingNumber = meetingNumber;
        this.subject = subject;
        this.memo = memo;
        this.organizerNumber = organizerNumber;
        this.schedules = schedules;
        this.attendances = attendances;
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

    public Schedules schedules() {
        return schedules;
    }

    public OrganizerNumber organizerNumber() {
        return organizerNumber;
    }

    public Attendances attendances() {
        return attendances;
    }

    public TotalNumberOfParticipant totalNumberOfParticipantAt(ScheduleNumber scheduleNumber, ConvenienceCategory convenienceCategory) {
        int number = 0;
        for (Attendance attendance : attendances.asList()) {
            for (Convenience convenience : attendance.conveniences().asList()) {
                if (!convenience.scheduleNumber().isSame(scheduleNumber)) {
                    continue;
                }
                if (convenience.convenienceCategory() != convenienceCategory) {
                    continue;
                }
                number++;
            }
        }

        return TotalNumberOfParticipant.of(number);
    }

    public ConvenienceCategory convenienceCategoryOf(AttendanceNumber attendanceNumber, ScheduleNumber scheduleNumber) {

        for (Attendance attendance : attendances.asList()) {
            if (!attendance.attendanceNumber().isSame(attendanceNumber)) {
                continue;
            }
            for (Convenience convenience : attendance.conveniences().asList()) {
                if (!convenience.scheduleNumber().isSame(scheduleNumber)) {
                    continue;
                }
                return convenience.convenienceCategory();
            }
        }
        throw new IllegalStateException("出欠に対する都合が存在しません。");
    }

    public ScheduleMatchStatus scheduleMatchStatusAt(ScheduleNumber scheduleNumber) {
        TotalNumberOfParticipant targetTotalNumber = totalNumberOfParticipantAt(scheduleNumber, ConvenienceCategory.マル);

        TotalNumberOfParticipant maxTotalNumber = TotalNumberOfParticipant.of(0);

        for (Schedule schedule : schedules.asList()) {
            TotalNumberOfParticipant currentTotalNumber  = totalNumberOfParticipantAt(schedule.scheduleNumber(), ConvenienceCategory.マル);
            if (currentTotalNumber.isGreaterThan(maxTotalNumber)) {
                maxTotalNumber = currentTotalNumber;
            }
        }

        if (targetTotalNumber.isGreaterThanOne() && targetTotalNumber.isSame(maxTotalNumber)) {
            return ScheduleMatchStatus.BEST_MATCHED;
        }
        return ScheduleMatchStatus.NOT_MATCHED;
    }

    public AttendanceEditability attendanceEditabilityFor(ParticipantNumber participantNumber, Attendance attendance) {

        if (attendance.participantNumber().isSame(participantNumber)) {
            return AttendanceEditability.編集可能;
        }
        return AttendanceEditability.編集不可;
    }

    public MeetingEditability editabilityFor(OrganizerNumber organizerNumber) {
        if (isNotOrganizedBy(organizerNumber)) return MeetingEditability.編集不可_非主催者;
        if (isAlreadyResponded()) return MeetingEditability.編集不可_回答済;
        return MeetingEditability.編集可能;
    }

    private boolean isNotOrganizedBy(OrganizerNumber organizerNumber) {
        return !this.organizerNumber.isSame(organizerNumber);
    }

    private boolean isAlreadyResponded() {
        return attendances.responseStatus() == AttendancesResponseStatus.回答発生済;
    }
}
