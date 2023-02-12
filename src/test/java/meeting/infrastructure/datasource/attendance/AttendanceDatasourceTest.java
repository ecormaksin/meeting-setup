package meeting.infrastructure.datasource.attendance;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.MeetingNumberTestHelper;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.domain.model.participant.ParticipantNumberTestHelper;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.infrastructure.datasource.ConvenienceNumberColumnFilter;
import meeting.infrastructure.datasource.DBUnitWithoutWebTest;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import meeting.infrastructure.datasource.meeting.MeetingNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.junit.jupiter.Container;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AttendanceDatasourceTest extends DBUnitWithoutWebTest {

    @Container
    private static final MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Autowired
    private AttendanceDatasource attendanceDatasource;

    AttendanceRegistrationRequest registrationRequestForInput = new AttendanceRegistrationRequest(
            AttendanceNumberTestHelper.fromInt(1),
            ParticipantNumberTestHelper.fromInt(1),
            ParticipantName.of("参加者名テスト1"),
            Conveniences.of(Arrays.asList(
                    Convenience.of(ScheduleNumber.of(1), ConvenienceCategory.GOOD)
            )),
            AttendanceComment.of("出欠コメント_テスト1")
    );

    @ParameterizedTest
    @DatabaseSetup("testdata/attendance_for_check_input_duplication_status.xlsx")
    @MethodSource("attendanceInputDuplicationStatusProvider")
    void _出欠入力の重複状況を取得できる(int participantNumberValue, AttendanceInputDuplicationStatus expected) {

        MeetingNumber meetingNumber = MeetingNumberTestHelper.fromInt(1);
        ParticipantNumber participantNumber = ParticipantNumberTestHelper.fromInt(participantNumberValue);

        AttendanceInputDuplicationStatus actual = attendanceDatasource.attendanceInputDuplicationStatus(meetingNumber, participantNumber);
        assertTrue(actual == expected);
    }

    static Stream<Arguments> attendanceInputDuplicationStatusProvider() {
        return Stream.of(
                arguments(1, AttendanceInputDuplicationStatus.あり),
                arguments(2, AttendanceInputDuplicationStatus.なし)
        );
    }

    @ParameterizedTest
    @DatabaseSetup("testdata/attendance_for_check_input_duplication_status.xlsx")
    @MethodSource("participantNameDuplicationStatusProvider")
    void _参加者名の重複状況を取得できる(AttendanceNumber attendanceNumber, ParticipantName participantName, ParticipantNameDuplicationStatus expected) {

        MeetingNumber meetingNumber = MeetingNumberTestHelper.fromInt(1);

        ParticipantNameDuplicationStatus actual;

        if (Objects.isNull(attendanceNumber)) {
            actual = attendanceDatasource.participantNameDuplicationStatus(meetingNumber, participantName);
        } else {
            actual = attendanceDatasource.participantNameDuplicationStatus(meetingNumber, attendanceNumber, participantName);
        }
        assertTrue(actual == expected);
    }

    static Stream<Arguments> participantNameDuplicationStatusProvider() {
        return Stream.of(
                arguments(null, ParticipantName.of("参加者名テスト2"), ParticipantNameDuplicationStatus.なし),
                arguments(AttendanceNumberTestHelper.fromInt(2), ParticipantName.of("参加者名テスト2"), ParticipantNameDuplicationStatus.なし),
                arguments(null, ParticipantName.of("参加者名テスト1"), ParticipantNameDuplicationStatus.あり),
                arguments(AttendanceNumberTestHelper.fromInt(2), ParticipantName.of("参加者名テスト1"), ParticipantNameDuplicationStatus.あり)
        );
    }

    @Test
    void _出欠入力対象のミーティングが見つからない場合はエラーになること() {

        MeetingNumber meetingNumber = MeetingNumberTestHelper.fromInt(1);

        assertThrows(MeetingNotFoundException.class, () -> attendanceDatasource.input(meetingNumber, registrationRequestForInput));
    }

    @Test
    @DatabaseSetup("testdata/attendance_for_input_participant_not_exists_before.xlsx")
    @ExpectedDatabase(value = "testdata/attendance_for_input_after.xlsx",
            assertionMode = DatabaseAssertionMode.NON_STRICT,
            columnFilters = {ConvenienceNumberColumnFilter.class})
    void _参加者の出欠入力が初めての場合にデータが期待通りに登録される() {
        MeetingNumber meetingNumber = MeetingNumberTestHelper.fromInt(1);
        assertDoesNotThrow(() -> attendanceDatasource.input(meetingNumber, registrationRequestForInput));
    }

    @Test
    @DatabaseSetup("testdata/attendance_for_input_participant_exists_before.xlsx")
    @ExpectedDatabase(value = "testdata/attendance_for_input_after.xlsx",
            assertionMode = DatabaseAssertionMode.NON_STRICT,
            columnFilters = {ConvenienceNumberColumnFilter.class})
    void _参加者の出欠入力が他のミーティングで過去に行われている場合にデータが期待通りに登録される() {
        MeetingNumber meetingNumber = MeetingNumberTestHelper.fromInt(1);
        assertDoesNotThrow(() -> attendanceDatasource.input(meetingNumber, registrationRequestForInput));

        Attendance actual = attendanceDatasource.attendanceOf(AttendanceNumberTestHelper.fromInt(1));
        assertNotNull(actual);
        assertTrue(actual.participantNumber().isSame(ParticipantNumberTestHelper.fromInt(1)));
    }
}
