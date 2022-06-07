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
import org.junit.jupiter.api.Test;
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
}
