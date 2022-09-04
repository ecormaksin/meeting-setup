package meeting.domain.model.meeting;

import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.ScheduleConstruct;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeetingTest {

    @ParameterizedTest(name = "ケース番号 兼 日程番号: {0}, " +
            "参加者1の都合: ''{1}'', " +
            "参加者2の都合: ''{2}'', " +
            "参加者3の都合: ''{3}'', " +
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
    void 指定した日程の都合種別ごとの人数を取得できる(int patternNumber,
                    String participant1Convenience,
                    String participant2Convenience,
                    String participant3Convenience,
                    int numberOfParticipantMaru,
                    int numberOfParticipantSankaku,
                    int numberOfParticipantsBatsu) {

        final ScheduleNumber scheduleNumber = new ScheduleNumber(patternNumber);
        final ScheduleConstruct scheduleConstruct =
                    ScheduleConstruct.of(2021, 12, patternNumber, 10, 0, 60);

        final List<Schedule> scheduleList = Arrays.asList(
                new Schedule(scheduleNumber, scheduleConstruct)
        );

        Attendances attendances = createAttendances(patternNumber,
                                        Arrays.asList(participant1Convenience,
                                                        participant2Convenience,
                                                        participant3Convenience));

        Meeting meeting = new Meeting(MeetingNumber.newNumber(),
                new Subject("件名テスト"),
                new Memo("メモテスト"),
                OrganizerNumber.newNumber(),
                Schedules.of(scheduleList),
                attendances);

        assertEquals(numberOfParticipantMaru,
                meeting.totalNumberOfParticipantAt(scheduleNumber, ConvenienceCategory.マル).value(),
                String.format("◯と回答した人数は%d", numberOfParticipantMaru));
        assertEquals(numberOfParticipantSankaku,
                meeting.totalNumberOfParticipantAt(scheduleNumber, ConvenienceCategory.サンカク).value(),
                String.format("△と回答した人数は%d", numberOfParticipantSankaku));
        assertEquals(numberOfParticipantsBatsu,
                meeting.totalNumberOfParticipantAt(scheduleNumber, ConvenienceCategory.バツ).value(),
                String.format("×と回答した人数は%d", numberOfParticipantsBatsu));
    }

    private Attendances createAttendances(int primitiveScheduleNumber, List<String> convenienceSymbols) {

        List<Attendance> attendanceList = new ArrayList<>();

        for (int i = 0; i < convenienceSymbols.size(); i++) {
            String convenienceSymbol = convenienceSymbols.get(i);

            List<Convenience> convenienceList = Arrays.asList(
                    new Convenience(new ScheduleNumber(primitiveScheduleNumber),
                            ConvenienceCategory.fromSymbol(convenienceSymbol))
            );
            Conveniences conveniences = new Conveniences(convenienceList);

            String participantNameText = "参加者テスト" + (i + 1);
            Attendance attendance = new Attendance(
                    AttendanceNumber.newNumber(),
                    new ParticipantName(participantNameText),
                    conveniences,
                    new AttendanceComment(participantNameText + "_出欠コメント")
            );

            attendanceList.add(attendance);
        }

        return new Attendances(attendanceList);
    }
}
