package meeting.domain.model.meeting;

import meeting.domain.model.meeting.organizer.OrganizerTerminal;
import meeting.domain.model.participant.Participant;
import meeting.domain.model.participant.Participants;
import meeting.domain.model.participant.TotalNumberOfParticipant;
import meeting.domain.model.participant.attendance.Attendance;
import meeting.domain.model.participant.attendance.AttendanceCategory;
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
    Participants participants;

    @Valid
    OrganizerTerminal organizerTerminal;

    public Meeting(@Valid MeetingId meetingId,
                   @Valid Subject subject,
                   @Valid Memo memo,
                   @Valid Schedules schedules,
                   @Valid Participants participants,
                   @Valid OrganizerTerminal organizerTerminal) {
        this.meetingId = meetingId;
        this.subject = subject;
        this.memo = memo;
        this.schedules = schedules;
        this.participants = participants;
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

    public TotalNumberOfParticipant totalNumberOfParticipantAt(ScheduleNumber scheduleNumber, AttendanceCategory attendanceCategory) {
        int number = 0;
        List<Participant> participantList = participants.asList();
        for (Participant participant : participantList) {
            for (Attendance attendance : participant.attendances().asList()) {
                if (!attendance.scheduleNumber().equals(scheduleNumber)) {
                    continue;
                }
                if (attendance.attendanceCategory() != attendanceCategory) {
                    continue;
                }
                number++;
            }
        }

        return new TotalNumberOfParticipant(number);
    }
}
