package meeting.domain.model.meeting;

import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.*;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;
import java.util.AbstractMap;
import java.util.Map;

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
    Meeting() {
    }

    public Meeting(MeetingNumber meetingNumber,
                   Subject subject,
                   Memo memo,
                   OrganizerNumber organizerNumber,
                   Schedules schedules,
                   Attendances attendances) {
        this.meetingNumber = meetingNumber;
        this.subject = subject;
        this.memo = memo;
        this.organizerNumber = organizerNumber;
        this.schedules = schedules;
        this.attendances = attendances;
    }

    public static Meeting of(
            MeetingNumber meetingNumber,
            Subject subject,
            Memo memo,
            OrganizerNumber organizerNumber,
            Schedules schedules) {

        return new Meeting(
                meetingNumber,
                subject,
                memo,
                organizerNumber,
                schedules,
                Attendances.empty());
    }

    public static Meeting of(MeetingNumber meetingNumber,
                             Subject subject,
                             Memo memo,
                             OrganizerNumber organizerNumber,
                             Schedules schedules,
                             Attendances attendances) {

        return new Meeting(meetingNumber,
                subject,
                memo,
                organizerNumber,
                schedules,
                attendances);
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

    public CountOfConvenience countOfConvenienceAt(ScheduleNumber scheduleNumber, ConvenienceCategory convenienceCategory) {

        long count = attendances.asList().stream()
                .flatMap(attendance -> attendance.conveniences().asList().stream())
                .filter(convenience -> convenience.scheduleNumber().isSame(scheduleNumber)
                        && convenience.convenienceCategory() == convenienceCategory)
                .count();

        return CountOfConvenience.of(Long.valueOf(count).intValue());
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

    public ConvenienceStatus convenienceStatusAt(ScheduleNumber scheduleNumber) {

        CountOfConvenience targetCountGood = countOfConvenienceAt(scheduleNumber, ConvenienceCategory.GOOD);

        CountsOfConvenience targetCounts = CountsOfConvenience.of(
                Map.ofEntries(
                        new AbstractMap.SimpleEntry<>(ConvenienceCategory.GOOD, targetCountGood),
                        new AbstractMap.SimpleEntry<>(ConvenienceCategory.MODEST, countOfConvenienceAt(scheduleNumber, ConvenienceCategory.MODEST))
                ));

        CountsOfConvenience mostSuitableCounts = CountsOfConvenience.of(
                Map.ofEntries(
                        new AbstractMap.SimpleEntry<>(ConvenienceCategory.GOOD, CountOfConvenience.of(0)),
                        new AbstractMap.SimpleEntry<>(ConvenienceCategory.MODEST, CountOfConvenience.of(0))
                ));

        for (Schedule schedule : schedules.asList()) {

            CountsOfConvenience currentCounts = CountsOfConvenience.of(
                    Map.ofEntries(
                            new AbstractMap.SimpleEntry<>(ConvenienceCategory.GOOD, countOfConvenienceAt(schedule.scheduleNumber(), ConvenienceCategory.GOOD)),
                            new AbstractMap.SimpleEntry<>(ConvenienceCategory.MODEST, countOfConvenienceAt(schedule.scheduleNumber(), ConvenienceCategory.MODEST))
                    ));

            if (currentCounts.isMoreSuitableOrSame(mostSuitableCounts) == ConvenienceStatus.MOST_SUITABLE) {
                mostSuitableCounts = currentCounts;
            }
        }

        return targetCounts.isMoreSuitableOrSame(mostSuitableCounts);
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

    @Override
    public String toString() {
        return """
                {
                    "meetingNumber": "%s",
                    "subject": "%s",
                    "memo": "%s",
                    "organizerNumber": "%s",
                    "schedules": %s,
                    "attendances": %s
                }
                """.formatted(meetingNumber.toString(),
                subject.toString(),
                memo.toString(),
                organizerNumber.toString(),
                schedules.toString(),
                attendances.toString());
    }
}
