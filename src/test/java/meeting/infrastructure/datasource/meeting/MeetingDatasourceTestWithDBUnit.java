package meeting.infrastructure.datasource.meeting;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import meeting.infrastructure.datasource.MeetingDBTestWithoutWeb;
import meeting.domain.model.meeting.*;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.*;
import meeting.infrastructure.datasource.XlsDataSetLoader;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@MeetingDBTestWithoutWeb
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
public class MeetingDatasourceTestWithDBUnit {

    @ClassRule
    private static MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionTemplate transactionTemplate;

    @Autowired
    private MeetingDatasource meetingDatasource;

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

//    @Nested
//    @DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
//    @DisplayName("メモあり・日程1件の場合")
//    class MeetingWithMemoAnd1Schedule {
//
//        final MeetingNumber meetingNumber = MeetingNumberTestHelper.fromInt(1);
//        final OrganizerNumber organizerNumber = OrganizerNumberTestHelper.fromInt(1);
//
//        @Test
//        @ExpectedDatabase(value = "testdata/create_meeting_with_memo_one_schedule.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
//        void _作成リクエストの実行が成功する() {
//
//            final List<Schedule> scheduleList = Arrays.asList(
//                    schedule(LocalDate.of(2022, 4, 1),10, 0,60)
//            );
//            createMeeting(
//                    1,
//                    "件名テスト１",
//                    "メモテスト１",
//                    1,
//                    scheduleList,
//                    LocalDateTime.of(2022, 3, 2, 9, 30, 0));
//        }
//
//    }

    @BeforeEach
    void setUp() {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Test
    @ExpectedDatabase(value = "testdata/create_meeting_with_memo_one_schedule.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
    void _メモあり_日程候補1件の場合() throws Exception {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

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
        });
    }
}
