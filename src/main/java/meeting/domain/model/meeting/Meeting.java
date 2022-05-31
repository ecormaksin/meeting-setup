package meeting.domain.model.meeting;

import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceNumber;
import meeting.domain.model.attendance.Attendances;
import meeting.domain.model.attendance.TotalNumberOfParticipant;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;
import java.util.List;

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
        List<Attendance> attendanceList = attendances.asList();
        for (Attendance attendance : attendanceList) {
            for (Convenience convenience : attendance.conveniences().asList()) {
                if (!convenience.scheduleNumber().equals(scheduleNumber)) {
                    continue;
                }
                if (convenience.convenienceCategory() != convenienceCategory) {
                    continue;
                }
                number++;
            }
        }

        return new TotalNumberOfParticipant(number);
    }

    public ConvenienceCategory convenienceCategoryOf(AttendanceNumber attendanceNumber, ScheduleNumber scheduleNumber) {

        for (Attendance attendance : attendances.asList()) {
            if (!attendance.attendanceNumber().equals(attendanceNumber)) {
                continue;
            }
            for (Convenience convenience : attendance.conveniences().asList()) {
                if (!convenience.scheduleNumber().equals(scheduleNumber)) {
                    continue;
                }
                return convenience.convenienceCategory();
            }
        }
        return ConvenienceCategory.未回答;
    }

    public MeetingEditability editability(final OrganizerNumber organizerNumber) {
        if (this.organizerNumber.isSame(organizerNumber)) {
            return MeetingEditability.編集可;
        }
        return MeetingEditability.編集不可;
    }
}
