package meeting.infrastructure.datasource.meeting;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import meeting.domain.model.meeting.*;
import meeting.domain.model.schedule.*;
import meeting.domain.model.schedule.construct.*;
import meeting.infrastructure.datasource.DBUnitWithoutWebTest;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import meeting.infrastructure.datasource.ScheduleNumberColumnFilter;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class MeetingDatasourceTest extends DBUnitWithoutWebTest {

    @ClassRule
    private static final MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionTemplate transactionTemplate;

    @Autowired
    private MeetingDatasource meetingDatasource;

    @BeforeEach
    void init() {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    void createMeeting(
            final int meetingNumberBaseInt,
            final String subjectValue,
            final String memoValue,
            final int organizerNumberBaseInt,
            final List<Schedule> scheduleList,
            final LocalDateTime registrationRequestDateTimeValue
    ) {

        final MeetingNumber meetingNumber = MeetingNumberTestHelper.fromInt(meetingNumberBaseInt);
        final Subject subject = new Subject(subjectValue);
        final Memo memo = new Memo(memoValue);
        final OrganizerNumber organizerNumber = OrganizerNumberTestHelper.fromInt(organizerNumberBaseInt);
        final Schedules schedules = new Schedules(scheduleList);
        final RegistrationRequestDateTime registrationRequestDateTime
                = new RegistrationRequestDateTime(registrationRequestDateTimeValue);

        final MeetingCreationRequest meetingCreationRequest = new MeetingCreationRequest(
                meetingNumber,
                subject,
                memo,
                organizerNumber,
                schedules,
                registrationRequestDateTime);

        meetingDatasource.create(meetingCreationRequest);
    }

    private Schedule schedule(
            final LocalDate meetingDateValue,
            final int startHourValue,
            final int startMinuteValue,
            final int durationValue) {

        final ScheduleConstruct scheduleConstruct = scheduleConstruct(
                meetingDateValue,
                startHourValue,
                startMinuteValue,
                durationValue);

        return new Schedule(ScheduleNumber.initialized(), scheduleConstruct);
    }
    private ScheduleConstruct scheduleConstruct(
            final LocalDate meetingDateValue,
            final int startHourValue,
            final int startMinuteValue,
            final int durationValue) {

        final MeetingDate meetingDate = new MeetingDate(meetingDateValue);
        final StartHour startHour = new StartHour(startHourValue);
        final StartMinute startMinute = StartMinute.of(startMinuteValue);
        final Duration duration = Duration.of(durationValue);

        return new ScheduleConstruct(meetingDate, startHour, startMinute, duration);
    }

    @Nested
    @DisplayName("メモあり・日程1件の場合")
    class MeetingWithMemoAnd1Schedule extends DBUnitWithoutWebTest {

        @Test
        @ExpectedDatabase(value = "testdata/create_meeting_with_memo_1_schedule.xlsx",
                assertionMode = DatabaseAssertionMode.NON_STRICT,
                columnFilters = { ScheduleNumberColumnFilter.class })
        void 作成リクエストの実行が成功する() {

            final List<Schedule> scheduleList = Arrays.asList(
                    schedule(LocalDate.of(2022, 4, 1),10, 0,60)
            );
            createMeeting(
                    1,
                    "件名テスト１",
                    "メモテスト１",
                    1,
                    scheduleList,
                    LocalDateTime.of(2022, 3, 2, 9, 30, 0));
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @DatabaseSetup("testdata/create_meeting_with_memo_1_schedule.xlsx")
        @DisplayName("作成リクエストと同じ値を取得できる")
        class MeetingGet extends DBUnitWithoutWebTest {

            final MeetingNumber expectedMeetingNumber = MeetingNumberTestHelper.fromInt(1);

            Meeting meeting;

            @BeforeEach
            void init() {
                meeting = meetingDatasource.get(expectedMeetingNumber);
            }

            @Test
            void ミーティングを取得できること() {
                assertNotNull(meeting);
            }

            @Test
            void ミーティング番号が設定した通りであること() {
                final MeetingNumber actual = meeting.meetingNumber();
                assertEquals(expectedMeetingNumber.value(), actual.value());
            }

            @Test
            void 件名が設定した通りであること() {
                final Subject subject = meeting.subject();
                assertEquals("件名テスト１", subject.value());
            }

            @Test
            void メモが設定した通りであること() {
                final Memo memo = meeting.memo();
                assertEquals("メモテスト１", memo.value());
            }

            @Test
            void 主催者番号が設定した通りであること() {
                final OrganizerNumber expected = OrganizerNumberTestHelper.fromInt(1);
                final OrganizerNumber actual = meeting.organizerNumber();
                assertEquals(expected.value(), actual.value());
            }

            private List<Schedule> scheduleList() {
                final Schedules schedules = meeting.schedules();
                return  schedules.asList();
            }

            @Test
            void 日程候補が1件であること() {
                final List<Schedule> scheduleList =  scheduleList();
                assertEquals(1, scheduleList.size());
            }

            private Schedule schedule() {
                final List<Schedule> scheduleList =  scheduleList();
                return scheduleList.get(0);
            }

            @Test
            void 日程番号が発番されていること() {
                final Schedule schedule = schedule();
                final ScheduleNumber scheduleNumber = schedule.scheduleNumber();
                assertTrue(scheduleNumber.value() > 0);
            }

            @Test
            void 開始予定時刻を設定内容に基づき取得できること() {
                final StartTime startTime = schedule().startTime();
                assertEquals(LocalTime.of(10, 0), startTime.value());
            }

            @Test
            void 終了予定時刻を設定内容に基づき取得できること() {
                final EndTime endTime = schedule().endTime();
                assertEquals(LocalTime.of(11, 0), endTime.value());
            }

            private ScheduleConstruct scheduleConstruct() {
                final Schedule schedule = schedule();
                return schedule.scheduleConstruct();
            }

            @Test
            void 開催日が設定した通りであること() {
                final ScheduleConstruct scheduleConstruct = scheduleConstruct();
                final MeetingDate meetingDate = scheduleConstruct.meetingDate();
                assertEquals(LocalDate.of(2022, 4, 1), meetingDate.value());
            }

            @Test
            void 開始時刻が設定した通りであること() {
                final ScheduleConstruct scheduleConstruct = scheduleConstruct();
                final StartHour startHour = scheduleConstruct.startHour();
                assertEquals(10, startHour.value());
            }

            @Test
            void 開始分が設定した通りであること() {
                final ScheduleConstruct scheduleConstruct = scheduleConstruct();
                final StartMinute startMinute = scheduleConstruct.startMinute();
                assertEquals(0, startMinute.value());
            }

            @Test
            void 会議時間が設定した通りであること() {
                final ScheduleConstruct scheduleConstruct = scheduleConstruct();
                final Duration duration = scheduleConstruct.duration();
                assertEquals(60, duration.value());
            }
        }
    }

    record MeetingDateTimeSpan(LocalDate meetingDateValue, LocalTime startTimeValue, LocalTime endTimeValue) {}

    @Nested
    @DisplayName("メモなし・日程3件(降順)の場合")
    class MeetingWithoutMemoAnd3Schedules extends DBUnitWithoutWebTest {

        @Test
        @ExpectedDatabase(value = "testdata/create_meeting_without_memo_3_schedules_reversed_order.xlsx",
                assertionMode = DatabaseAssertionMode.NON_STRICT,
                columnFilters = { ScheduleNumberColumnFilter.class })
        void 作成リクエストの実行が成功する() {

            final List<Schedule> scheduleList = Arrays.asList(
                    schedule(LocalDate.of(2022, 4, 3),13, 30,90),
                    schedule(LocalDate.of(2022, 4, 2),11, 0,30),
                    schedule(LocalDate.of(2022, 4, 2),10, 30,60)
            );
            createMeeting(
                    2,
                    "件名テスト２",
                    "",
                    2,
                    scheduleList,
                    LocalDateTime.of(2022, 4, 1, 15, 12, 34));
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @DatabaseSetup("testdata/create_meeting_without_memo_3_schedules_reversed_order.xlsx")
        @DisplayName("作成リクエストと同じ値を取得できる")
        class MeetingGet extends DBUnitWithoutWebTest {

            final MeetingNumber expectedMeetingNumber = MeetingNumberTestHelper.fromInt(2);

            Meeting meeting;

            @BeforeEach
            void init() {
                meeting = meetingDatasource.get(expectedMeetingNumber);
            }

            @Test
            void メモが未設定であること() {
                final Memo memo = meeting.memo();
                assertEquals("", memo.value());
            }

            @Test
            void 日程候補を開催日時の昇順で取得していること() {
                final List<MeetingDateTimeSpan> expected = meetingDateTimeSpansExpected();
                final List<MeetingDateTimeSpan> actual = meetingDateTimeSpansActual();
                assertEquals(expected, actual);
            }

            private List<MeetingDateTimeSpan> meetingDateTimeSpansExpected() {
                return Arrays.asList(
                        new MeetingDateTimeSpan(LocalDate.of(2022, 4, 2), LocalTime.of(10, 30),
                                LocalTime.of(11, 30)),
                        new MeetingDateTimeSpan(LocalDate.of(2022, 4, 2), LocalTime.of(11, 0),
                                LocalTime.of(11, 30)),
                        new MeetingDateTimeSpan(LocalDate.of(2022, 4, 3), LocalTime.of( 13, 30),
                                LocalTime.of(15, 0))
                );
            }

            private List<MeetingDateTimeSpan> meetingDateTimeSpansActual() {
                return meeting.schedules().asList().stream()
                        .map(s -> {
                            final MeetingDate meetingDate = s.meetingDate();
                            final StartTime startTime = s.startTime();
                            final EndTime endTime = s.endTime();
                            return new MeetingDateTimeSpan( meetingDate.value(), startTime.value(), endTime.value());
                        })
                        .collect(Collectors.toList());
            }
        }
    }
}
