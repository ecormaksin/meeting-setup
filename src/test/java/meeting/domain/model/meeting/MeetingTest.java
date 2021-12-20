package meeting.domain.model.meeting;

import meeting.domain.model.meeting.organizer.OrganizerTerminal;
import meeting.domain.model.participant.*;
import meeting.domain.model.participant.attendance.Attendance;
import meeting.domain.model.participant.attendance.AttendanceCategory;
import meeting.domain.model.participant.attendance.Attendances;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeetingTest {

    @ParameterizedTest(name = "ケース番号 兼 日程候補番号: {0}, " +
            "参加者1の出欠: ''{1}'', " +
            "参加者2の出欠: ''{2}'', " +
            "参加者3の出欠: ''{3}'', " +
            "◯の数の期待値: {4}, " +
            "△の数の期待値: {5}, " +
            "×の数の期待値: {6}")
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
    void 指定した日程候補の出欠種別ごとの人数を取得できる(int patternNumber,
                    String participant1Attendance,
                    String participant2Attendance,
                    String participant3Attendance,
                    int numberOfParticipantMaru,
                    int numberOfParticipantSankaku,
                    int numberOfParticipantsBatsu) {

        List<Schedule> scheduleList = Arrays.asList(
                Schedule.of(patternNumber,
                        LocalDate.of(2021, 12, patternNumber),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0))
        );

        Participants participants = createParticipants(patternNumber,
                                        Arrays.asList(participant1Attendance,
                                                        participant2Attendance,
                                                        participant3Attendance));
        OrganizerTerminal organizerTerminal = OrganizerTerminal.of(
                "127.0.0.1",
                "Windows",
                "Chrome");

        Meeting meeting = new Meeting(MeetingId.newId(),
                new Subject("件名テスト"),
                new Memo("メモテスト"),
                Schedules.of(scheduleList),
                participants,
                organizerTerminal);

        ScheduleNumber scheduleNumber = new ScheduleNumber(patternNumber);
        assertEquals(numberOfParticipantMaru,
                meeting.totalNumberOfParticipantAt(scheduleNumber, AttendanceCategory.マル).value(),
                String.format("◯と回答した人数は%d", numberOfParticipantMaru));
        assertEquals(numberOfParticipantSankaku,
                meeting.totalNumberOfParticipantAt(scheduleNumber, AttendanceCategory.サンカク).value(),
                String.format("△と回答した人数は%d", numberOfParticipantSankaku));
        assertEquals(numberOfParticipantsBatsu,
                meeting.totalNumberOfParticipantAt(scheduleNumber, AttendanceCategory.バツ).value(),
                String.format("×と回答した人数は%d", numberOfParticipantsBatsu));
    }

    private Participants createParticipants(int primitiveScheduleNumber, List<String> attendanceTextList) {

        List<Participant> participantList = new ArrayList<>();

        for (int i = 0; i < attendanceTextList.size(); i++) {
            String attendanceText = attendanceTextList.get(i);

            List<Attendance> attendanceList = Arrays.asList(
                    Attendance.of(primitiveScheduleNumber, attendanceText)
            );
            Attendances attendances = Attendances.of(attendanceList);

            String participantNameText = "参加者テスト" + (i + 1);
            Participant participant = new Participant(
                    ParticipantId.newId(),
                    new ParticipantName(participantNameText),
                    attendances,
                    new AttendanceComment(participantNameText + "_出欠コメント")
            );

            participantList.add(participant);
        }

        return Participants.of(participantList);
    }
}
