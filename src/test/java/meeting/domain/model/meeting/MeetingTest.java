package meeting.domain.model.meeting;

import meeting.domain.model.meeting.organizer.OrganizerTerminal;
import meeting.domain.model.participant.AttendanceComment;
import meeting.domain.model.participant.Participant;
import meeting.domain.model.participant.ParticipantId;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.attendance.Attendance;
import meeting.domain.model.participant.attendance.Attendances;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.Schedules;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class MeetingTest {

    @Test
    void 指定した日程候補の参加可能人数を取得できる() throws Exception {

        MeetingId meetingId = MeetingId.newId();
        Subject subject = new Subject("件名テスト");
        Memo memo = new Memo("メモテスト");
        OrganizerTerminal organizerTerminal = OrganizerTerminal.of(
                "127.0.0.1",
                "Windows",
                "Chrome");

        List<Schedule> scheduleList = Arrays.asList(
            Schedule.of(1,
                LocalDate.of(2021, 12, 1),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0))
        );
        Schedules schedules = Schedules.of(scheduleList);

        List<Attendance> attendanceList = Arrays.asList(
            Attendance.of(1, "○")
        );
        Attendances attendances = Attendances.of(attendanceList);

        Participant participant1 = new Participant(
                ParticipantId.newId(),
                new ParticipantName("参加者テスト1"),
                attendances,
                new AttendanceComment("出欠コメントテスト1")
        );
    }

    @ParameterizedTest(name = "[{index}] {arguments}")
    @CsvSource({
            "1, '○', '○', '○', 3, 0, 0",
            "2, '○', '○', '△', 2, 1, 0",
            "3, '○', '○', '×', 2, 0, 1",
            "4, '○', '△', '○', 2, 1, 0",
            "5, '○', '△', '△', 1, 2, 0",
            "6, '○', '△', '×', 1, 1, 1",
            "7, '○', '×', '○', 2, 0, 1",
            "8, '○', '×', '△', 1, 1, 1",
            "9, '○', '×', '×', 1, 0, 2",
            "10, '△', '○', '○', 2, 1, 0",
            "11, '△', '○', '△', 1, 2, 0",
            "12, '△', '○', '×', 1, 1, 1",
            "13, '△', '△', '○', 1, 2, 0",
            "14, '△', '△', '△', 0, 3, 0",
            "15, '△', '△', '×', 0, 2, 1",
            "16, '△', '×', '○', 1, 1, 1",
            "17, '△', '×', '△', 0, 2, 1",
            "18, '△', '×', '×', 0, 1, 2",
            "19, '×', '○', '○', 2, 0, 1",
            "20, '×', '○', '△', 1, 1, 1",
            "21, '×', '○', '×', 1, 0, 2",
            "22, '×', '△', '○', 1, 1, 1",
            "23, '×', '△', '△', 0, 2, 1",
            "24, '×', '△', '×', 0, 1, 2",
            "25, '×', '×', '○', 1, 0, 2",
            "26, '×', '×', '△', 0, 1, 2",
            "27, '×', '×', '×', 0, 0, 3",
    })
    void paramsTest(int primitiveScheduleNumber,
                    String participant1Attendance,
                    String participant2Attendance,
                    String participant3Attendance,
                    int numberOfParticipantMaru,
                    int numberOfParticipantSankaku,
                    int numberOfParticipantsBatsu) {

        System.out.print(
                String.format("primitiveScheduleNumber: %d, " +
                        "participant1Attendance: %s, " +
                        "participant2Attendance: %s, " +
                        "participant3Attendance: %s, " +
                        "numberOfParticipantMaru: %d, " +
                        "numberOfParticipantSankaku: %d, " +
                        "numberOfParticipantsBatsu: %d",
                        primitiveScheduleNumber,
                        participant1Attendance,
                        participant2Attendance,
                        participant3Attendance,
                        numberOfParticipantMaru,
                        numberOfParticipantSankaku,
                        numberOfParticipantsBatsu));
    }
}
