package meeting.domain.model.meeting;

import meeting.domain.model.meeting.organizer.OrganizerTerminal;
import meeting.domain.model.attendance.Attendance;
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
    MeetingId meetingId;

    @Valid
    Subject subject;

    @Valid
    Memo memo;

    @Valid
    Schedules schedules;

    @Valid
    Attendances attendances;

    @Valid
    OrganizerTerminal organizerTerminal;

    public Meeting(@Valid MeetingId meetingId,
                   @Valid Subject subject,
                   @Valid Memo memo,
                   @Valid Schedules schedules,
                   @Valid Attendances attendances,
                   @Valid OrganizerTerminal organizerTerminal) {
        this.meetingId = meetingId;
        this.subject = subject;
        this.memo = memo;
        this.schedules = schedules;
        this.attendances = attendances;
        this.organizerTerminal = organizerTerminal;
    }

    @Deprecated
    Meeting() {
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

    public OrganizerTerminal organizerTerminal() {
        return organizerTerminal;
    }

    public TotalNumberOfParticipant totalNumberOfParticipantAt(ScheduleNumber scheduleNumber, ConvenienceCategory convenienceCategory) {
        int number = 0;
        List<Attendance> attendanceList = attendances.asList();
        for (Attendance attendance : attendanceList) {
            for (Convenience convenience : attendance.attendances().asList()) {
                if (!convenience.scheduleNumber().equals(scheduleNumber)) {
                    continue;
                }
                if (convenience.attendanceCategory() != convenienceCategory) {
                    continue;
                }
                number++;
            }
        }

        return new TotalNumberOfParticipant(number);
    }
}
