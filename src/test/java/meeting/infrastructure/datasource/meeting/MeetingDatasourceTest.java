package meeting.infrastructure.datasource.meeting;

import meeting.MeetingDBTest;
import meeting.domain.model.meeting.*;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.*;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@MeetingDBTest
class MeetingDatasourceTest {

    @ClassRule
    static MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Autowired
    MeetingDatasource meetingDatasource;
    Meeting createMeeting(
                final String meetingNumberValue,
                final String subjectValue,
                final String memoValue,
                final String organizerNumberValue,
                final List<Schedule> scheduleList,
                final LocalDateTime registrationRequestDateTimeValue
    ) {

        final MeetingNumber meetingNumber = new MeetingNumber(meetingNumberValue);
        final Subject subject = new Subject(subjectValue);
        final Memo memo = new Memo(memoValue);
        final OrganizerNumber organizerNumber = new OrganizerNumber(organizerNumberValue);
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
        return meetingDatasource.get(meetingNumber);
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

        return new Schedule(ScheduleNumber.initialize(), scheduleConstruct);
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
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("メモあり・日程1件の場合")
    class MeetingWithMemoAnd1Schedule {

        Meeting meeting;

        @BeforeAll
        void initAll() {

            final List<Schedule> scheduleList = Arrays.asList(
                    MeetingDatasourceTest.this.schedule(LocalDate.of(2022, 4, 1),10, 0,60)
            );
            meeting = MeetingDatasourceTest.this.createMeeting(
                    "mt123456789012345678900001",
                    "件名テスト１",
                    "メモテスト１",
                    "og123456789012345678900001",
                    scheduleList,
                    LocalDateTime.of(2022, 3, 2, 9, 30, 0));
        }

        @Test
        void _ミーティングを取得できること() {
            assertNotNull(meeting);
        }

        @Test
        void _ミーティング番号が設定した通りであること() {
            final MeetingNumber meetingNumber = meeting.meetingNumber();
            assertEquals("mt123456789012345678900001", meetingNumber.value());
        }

        @Test
        void _件名が設定した通りであること() {
            final Subject subject = meeting.subject();
            assertEquals("件名テスト１", subject.value());
        }

        @Test
        void _メモが設定した通りであること() {
            final Memo memo = meeting.memo();
            assertEquals("メモテスト１", memo.value());
        }

        @Test
        void _主催者番号が設定した通りであること() {
            final OrganizerNumber organizerNumber = meeting.organizerNumber();
            assertEquals("og123456789012345678900001", organizerNumber.value());
        }

        private List<Schedule> scheduleList() {
            final Schedules schedules = meeting.schedules();
            return  schedules.asList();
        }

        @Test
        void _日程候補が1件であること() {
            final List<Schedule> scheduleList =  scheduleList();
            assertEquals(1, scheduleList.size());
        }

        private Schedule schedule() {
            final List<Schedule> scheduleList =  scheduleList();
            return scheduleList.get(0);
        }

        @Test
        void _日程番号が発番されていること() {
            final Schedule schedule = schedule();
            final ScheduleNumber scheduleNumber = schedule.scheduleNumber();
            assertTrue(scheduleNumber.value() > 0);
        }

        private ScheduleConstruct scheduleConstruct() {
            final Schedule schedule = schedule();
            return schedule.scheduleConstruct();
        }

        @Test
        void _開催日が設定した通りであること() {
            final ScheduleConstruct scheduleConstruct = scheduleConstruct();
            final MeetingDate meetingDate = scheduleConstruct.meetingDate();
            assertEquals(LocalDate.of(2022, 4, 1), meetingDate.value());
        }

        @Test
        void _開始時刻が設定した通りであること() {
            final ScheduleConstruct scheduleConstruct = scheduleConstruct();
            final StartHour startHour = scheduleConstruct.startHour();
            assertEquals(10, startHour.value());
        }

        @Test
        void _開始分が設定した通りであること() {
            final ScheduleConstruct scheduleConstruct = scheduleConstruct();
            final StartMinute startMinute = scheduleConstruct.startMinute();
            assertEquals(0, startMinute.value());
        }

        @Test
        void _会議時間が設定した通りであること() {
            final ScheduleConstruct scheduleConstruct = scheduleConstruct();
            final Duration duration = scheduleConstruct.duration();
            assertEquals(60, duration.value());
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("メモなし・日程候補2件(日付の設定順序が逆)の場合")
    class MeetingWithoutMemoAndReverseOrdered2Schedules {

        Meeting meeting;

        Schedule firstSchedule;
        Schedule secondSchedule;

        @BeforeAll
        void initAll() {

            firstSchedule = MeetingDatasourceTest.this.schedule(LocalDate.of(2022, 4, 3),12, 0,90);
            secondSchedule = MeetingDatasourceTest.this.schedule(LocalDate.of(2022, 4, 2),11, 30,30);
            final List<Schedule> scheduleList = Arrays.asList(
                    firstSchedule, secondSchedule
            );
            meeting = MeetingDatasourceTest.this.createMeeting(
                    "mt123456789012345678900002",
                    "件名テスト２",
                    "",
                    "og123456789012345678900002",
                    scheduleList,
                    LocalDateTime.of(2022, 4, 2, 11, 00, 0));
        }

        @Test
        void _メモは未設定であること() {
            final Memo memo = meeting.memo();
            assertEquals("", memo.value());
        }

        private List<Schedule> scheduleList() {
            final Schedules schedules = meeting.schedules();
            return  schedules.asList();
        }

        @Test
        void _日程候補が2件であること() {
            final List<Schedule> scheduleList =  scheduleList();
            assertEquals(2, scheduleList.size());
        }

        @Test
        void _日程候補が日時の昇順であること() {
            final List<ScheduleConstruct> expected = Arrays.asList(
                    secondSchedule.scheduleConstruct(),
                    firstSchedule.scheduleConstruct()
            );
            final List<Schedule> scheduleList =  scheduleList();
            final List<ScheduleConstruct> actual = scheduleList.stream()
                    .map(c -> c.scheduleConstruct())
                    .collect(Collectors.toList());
            assertEquals(expected, actual);
        }

    }
}
