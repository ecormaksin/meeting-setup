package meeting.infrastructure.datasource.meeting;

import meeting.MeetingDBTest;
import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.Attendances;
import meeting.domain.model.meeting.*;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.*;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
    @MeetingDBTest
    @DisplayName("メモあり・日程1件の場合")
    class MeetingWithMemoAndOneSchedule {

        Meeting meeting;

        @BeforeAll
        void initAll() throws Exception {

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
        void _ミーティングを取得できること() throws Exception {
            assertNotNull(meeting);
        }

        @Test
        void _ミーティング番号が設定した通りであること() throws Exception {
            final MeetingNumber meetingNumber = meeting.meetingNumber();
            assertEquals("mt123456789012345678900001", meetingNumber.value());
        }

        @Test
        void _件名が設定した通りであること() throws Exception {
            final Subject subject = meeting.subject();
            assertEquals("件名テスト１", subject.value());
        }

        @Test
        void _メモが設定した通りであること() throws Exception {
            final Memo memo = meeting.memo();
            assertEquals("メモテスト１", memo.value());
        }

        @Test
        void _主催者番号が設定した通りであること() throws Exception {
            final OrganizerNumber organizerNumber = meeting.organizerNumber();
            assertEquals("og123456789012345678900001", organizerNumber.value());
        }

        private List<Schedule> scheduleList() {
            final Schedules schedules = meeting.schedules();
            return  schedules.asList();
        }

        @Test
        void _日程候補が1件であること() throws Exception {
            final List<Schedule> scheduleList =  scheduleList();
            assertEquals(1, scheduleList.size());
        }

        private Schedule schedule() {
            final List<Schedule> scheduleList =  scheduleList();
            return scheduleList.get(0);
        }

        @Test
        void _日程番号が発番されていること() throws Exception {
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
        void _開始分が設定した通りであること() throws Exception {
            final ScheduleConstruct scheduleConstruct = scheduleConstruct();
            final StartMinute startMinute = scheduleConstruct.startMinute();
            assertEquals(0, startMinute.value());
        }

        @Test
        void _会議時間が設定した通りであること() throws Exception {
            final ScheduleConstruct scheduleConstruct = scheduleConstruct();
            final Duration duration = scheduleConstruct.duration();
            assertEquals(60, duration.value());
        }
    }

/*
    record ScheduleParameter(LocalDate meetingDateValue,
                             int startHourValue,
                             int startMinuteValue,
                             int durationValue) {}

    record RequestParameter(String meetingNumberValue,
                            String subjectValue,
                            String memoValue,
                            String organizerNumberValue,
                            LocalDateTime registrationRequestDateTimeValue,
                            List<ScheduleParameter> scheduleParameters) {}

    record ExpectedParameter(String meetingNumberValue,
                             String subjectValue,
                             String memoValue,
                             String organizerNumberValue,
                             List<ScheduleParameter> scheduleParameters) {}

    record TestParameter(RequestParameter requestParameter, ExpectedParameter expectedParameter) {}

    private String meetingNumberValue(final int number) {
        return "mt" + String.format("%024d", number);
    }

    private String organizerNumberValue(final int number) {
        return "og" + String.format("%024d", number);
    }

    @Test
    void _メモあり_日程1件の場合() throws Exception {

        final String meetingNumberValue = meetingNumberValue(1);
        final String subjectValue = "件名テスト1";
        final String memoValue = "メモ テスト1";
        final String organizerNumberValue = organizerNumberValue(1);
        final LocalDateTime registrationRequestDateTime = LocalDateTime.of(2022, 3, 1, 10, 0, 0);

        List<ScheduleParameter> scheduleParameters = Arrays.asList(
                new ScheduleParameter(
                        LocalDate.of(2022, 4, 1),10, 0,60
                )
        );

        RequestParameter requestParameter = new RequestParameter(
                meetingNumberValue,
                subjectValue,
                memoValue,
                organizerNumberValue,
                registrationRequestDateTime,
                scheduleParameters);

        ExpectedParameter expectedParameter = new ExpectedParameter(
                meetingNumberValue,
                subjectValue,
                memoValue,
                organizerNumberValue,
                scheduleParameters);

        TestParameter testParameter = new TestParameter(requestParameter, expectedParameter);

        test(testParameter);
    }

    @Test
    void _メモなし_日程2件降順の場合() throws Exception {

        final String meetingNumberValue = meetingNumberValue(2);
        final String subjectValue = "件名テスト2";
        final String memoValue = "";
        final String organizerNumberValue = organizerNumberValue(2);
        final LocalDateTime registrationRequestDateTime = LocalDateTime.of(2022, 3, 2, 11, 0, 0);

        final ScheduleParameter scheduleParameter1 = new ScheduleParameter(
                LocalDate.of(2022, 4, 3),15, 0,60
        );

        final ScheduleParameter scheduleParameter2 = new ScheduleParameter(
                LocalDate.of(2022, 4, 3),14, 30,30
        );

        final List<ScheduleParameter> scheduleRequestParameters = Arrays.asList(
                scheduleParameter1, scheduleParameter2
        );

        final List<ScheduleParameter> scheduleExpectedParameters = Arrays.asList(
                scheduleParameter2, scheduleParameter1
        );

        final RequestParameter requestParameter = new RequestParameter(
                meetingNumberValue,
                subjectValue,
                memoValue,
                organizerNumberValue,
                registrationRequestDateTime,
                scheduleRequestParameters);

        final ExpectedParameter expectedParameter = new ExpectedParameter(
                meetingNumberValue,
                subjectValue,
                memoValue,
                organizerNumberValue,
                scheduleExpectedParameters);

        final TestParameter testParameter = new TestParameter(requestParameter, expectedParameter);

        test(testParameter);
    }

    private void test(final TestParameter testParameter) throws Exception {

        // Arrange
        final MeetingCreationRequest request = createRequest(testParameter.requestParameter());

        // Act
        meetingDatasource.create(request);
        final MeetingNumber requestMeetingNumber = request.meetingNumber();
        final Meeting meeting = meetingDatasource.get(requestMeetingNumber);

        // Assert
        doAssertion(testParameter.expectedParameter(), meeting);
    }

    private void doAssertion(final ExpectedParameter expectedParameter, final Meeting meeting) {

        assertNotNull(meeting);

        final MeetingNumber actualMeetingNumber = meeting.meetingNumber();
        assertEquals(expectedParameter.meetingNumberValue(), actualMeetingNumber.value());

        final Subject subject = meeting.subject();
        assertEquals(expectedParameter.subjectValue(), subject.value());

        final Memo memo = meeting.memo();
        assertEquals(expectedParameter.memoValue(), memo.value());

        final OrganizerNumber organizerNumber = meeting.organizerNumber();
        assertEquals(expectedParameter.organizerNumberValue(), organizerNumber.value());

        List<ScheduleParameter> scheduleParameters = expectedParameter.scheduleParameters();
        final Schedules schedules = meeting.schedules();

        final List<Schedule> scheduleList = schedules.asList();
        assertEquals(scheduleParameters.size(), scheduleList.size());

        for (int i = 0; i < scheduleParameters.size(); i++) {
            ScheduleParameter scheduleParameter = scheduleParameters.get(i);
            final Schedule schedule = scheduleList.get(i);

            final ScheduleNumber scheduleNumber = schedule.scheduleNumber();
            assertTrue(scheduleNumber.value() > 0);

            final ScheduleConstruct scheduleConstruct = schedule.scheduleConstruct();

            final MeetingDate meetingDate = scheduleConstruct.meetingDate();
            assertEquals(scheduleParameter.meetingDateValue(), meetingDate.value());

            final StartHour startHour = scheduleConstruct.startHour();
            assertEquals(scheduleParameter.startHourValue(), startHour.value());

            final StartMinute startMinute = scheduleConstruct.startMinute();
            assertEquals(scheduleParameter.startMinuteValue(), startMinute.value());

            final Duration duration = scheduleConstruct.duration();
            assertEquals(scheduleParameter.durationValue(), duration.value());
        }

        final Attendances attendances = meeting.attendances();
        final List<Attendance> attendanceList = attendances.asList();
        assertEquals(0, attendanceList.size());
    }

    private MeetingCreationRequest createRequest(RequestParameter requestParameter) {

        final MeetingNumber meetingNumber = new MeetingNumber(requestParameter.meetingNumberValue());
        final Subject subject = new Subject(requestParameter.subjectValue());
        final Memo memo = new Memo(requestParameter.memoValue());
        final OrganizerNumber organizerNumber = new OrganizerNumber(requestParameter.organizerNumberValue());
        final Schedules schedules = schedules(requestParameter.scheduleParameters());
        final RegistrationRequestDateTime requestDateTime
                = new RegistrationRequestDateTime(
                requestParameter.registrationRequestDateTimeValue());

        return new MeetingCreationRequest(
                meetingNumber,
                subject,
                memo,
                organizerNumber,
                schedules,
                requestDateTime);
    }

    private Schedules schedules(List<ScheduleParameter> scheduleParameters) {
        List<Schedule> list = scheduleParameters.stream().map(scheduleParameter -> {
            return schedule(scheduleParameter);
        }).toList();

        return Schedules.of(list);
    }

    private Schedule schedule(ScheduleParameter scheduleParameter) {
        return new Schedule(ScheduleNumber.initialize(), scheduleConstruct(scheduleParameter));
    }
    private ScheduleConstruct scheduleConstruct(ScheduleParameter scheduleParameter) {

        return new ScheduleConstruct(
                new MeetingDate(scheduleParameter.meetingDateValue()),
                new StartHour(scheduleParameter.startHourValue()),
                StartMinute.of(scheduleParameter.startMinuteValue()),
                Duration.of(scheduleParameter.durationValue()));
    }
*/

}
