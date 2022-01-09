package meeting.domain.model.meeting;

import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.Attendances;
import meeting.domain.model.attendance.TotalNumberOfParticipant;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.meeting.organizer.ClientBrowser;
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
    ClientBrowser organizerTerminal;

    public Meeting(@Valid MeetingId meetingId,
                   @Valid Subject subject,
                   @Valid Memo memo,
                   @Valid Schedules schedules,
                   @Valid Attendances attendances,
                   @Valid ClientBrowser clientBrowser) {
        this.meetingId = meetingId;
        this.subject = subject;
        this.memo = memo;
        this.schedules = schedules;
        this.attendances = attendances;
        this.organizerTerminal = clientBrowser;
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

    public Attendances attendances() {
        return attendances;
    }

    public ClientBrowser organizerTerminal() {
        return organizerTerminal;
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

    public ConvenienceCategory convenienceCategoryOf(AttendanceId attendanceId, ScheduleNumber scheduleNumber) {

        for (Attendance attendance : attendances.asList()) {
            if (!attendance.attendanceId().equals(attendanceId)) {
                continue;
            }
            for (Convenience convenience : attendance.conveniences().asList()) {
                if (!convenience.scheduleNumber().equals(scheduleNumber)) {
                    continue;
                }
                return convenience.convenienceCategory();
            }
        }
        throw new IllegalArgumentException("該当する出欠の都合が見つかりませんでした。");
    }

    public MeetingEditability editability(ClientBrowser clientBrowser) {
        if (clientBrowser.equals(organizerTerminal)) {
            return MeetingEditability.編集可;
        }
        return MeetingEditability.編集不可;
    }
}
