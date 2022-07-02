package meeting.infrastructure.datasource.meeting;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import meeting.infrastructure.datasource.fw.MeetingDBTestContainer;
import meeting.infrastructure.datasource.fw.MeetingDBTestWithoutWeb;
import meeting.domain.model.meeting.*;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.*;
import meeting.infrastructure.datasource.fw.XlsDataSetLoader;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@MeetingDBTestWithoutWeb
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
public class MeetingDatasourceTestWithDBUnit {

    @ClassRule
    static MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Autowired
    MeetingDatasource meetingDatasource;

    void createMeeting(
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

    @Test
    @ExpectedDatabase(value = "testdata/create_meeting_with_memo_one_schedule.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
    void _メモあり_日程候補1件の場合() throws Exception {

        final List<Schedule> scheduleList = Arrays.asList(
                schedule(LocalDate.of(2022, 4, 1),10, 0,60)
        );
        final String meetingNumberValue = "mt123456789012345678900001";
        createMeeting(
                meetingNumberValue,
                "件名テスト１",
                "メモテスト１",
                "og123456789012345678900001",
                scheduleList,
                LocalDateTime.of(2022, 3, 2, 9, 30, 0));

//        Meeting meeting = meetingDatasource.get(new MeetingNumber(meetingNumberValue));
//        assertNotNull(meeting);
    }
}
