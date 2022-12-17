package meeting.presentation.meeting;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.MeetingEditScenario;
import meeting.application.senario.MeetingShowScenario;
import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceNumberTestHelper;
import meeting.domain.model.attendance.Attendances;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.meeting.*;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.organizer.OrganizerNumberFake_1;
import meeting.domain.model.organizer.OrganizerNumberTestHelper;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumberTestHelper;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.*;
import meeting.presentation.ErrorPage;
import meeting.presentation.TopPage;
import meeting.presentation.meeting.input.schedule.construct.ScheduleConstructInput;
import meeting.presentation.meeting.page_objects.DeleteCompletedPage;
import meeting.presentation.meeting.page_objects.EditCompletedPage;
import meeting.presentation.meeting.page_objects.EditFormPage;
import meeting.presentation.meeting.page_objects.ShowPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class MeetingEditControllerTest extends MeetingIntegrationTest {

    Logger logger = Logger.getLogger(MeetingEditControllerTest.class.getName());

    MeetingNumber meetingNumber;
    Meeting meeting;
    Meeting respondedMeeting;

    OrganizerNumber organizerNumber;

    private Meeting showBaseMeeting(MeetingNumber meetingNumber) {

        Subject subject = Subject.of("件名テスト1");
        Memo memo = Memo.blank();
        OrganizerNumber organizerNumber = OrganizerNumberTestHelper.fromInt(1);
        Schedules schedules = Schedules.of(Arrays.asList(
                Schedule.of(ScheduleNumber.of(1),
                        ScheduleConstruct.of(MeetingDate.of(2022, 4, 1), StartHour.of(10), StartMinute.JUST, Duration.OF_1_HOUR))
        ));

        return Meeting.of(
                meetingNumber,
                subject,
                memo,
                organizerNumber,
                schedules
        );

    }

    private Meeting respondedMeeting(Meeting meeting) {

        Conveniences conveniences = Conveniences.of(Arrays.asList(
                Convenience.of(ScheduleNumber.of(1), ConvenienceCategory.マル)
        ));

        Attendance attendance = Attendance.of(AttendanceNumberTestHelper.fromInt(1),
                ParticipantNumberTestHelper.fromInt(1),
                ParticipantName.of("参加者名テスト1"),
                conveniences);

        return Meeting.of(meeting.meetingNumber(),
                meeting.subject(),
                meeting.memo(),
                meeting.organizerNumber(),
                meeting.schedules(),
                Attendances.of(Arrays.asList(attendance)));
    }

    @BeforeEach
    void setup() throws Exception {
        meetingNumber = MeetingNumberTestHelper.fromInt(1);
        meeting = showBaseMeeting(meetingNumber);
        respondedMeeting = respondedMeeting(meeting);

        new OrganizerNumberFake_1();
        organizerNumber = OrganizerNumberFake_1.newNumber();
        new RegistrationRequestDateTimeFake_19841231_1023();
    }

    @Nested
    @DisplayName("表示時・編集時ともに出欠なしのミーティング")
    class CaseBaseMeetingWhenBothGetAndEdit extends MeetingIntegrationTest {

        @Autowired
        WebDriver driver;

        @MockBean
        MeetingShowScenario meetingShowScenario;

        @MockBean
        MeetingEditScenario meetingEditScenario;

        ShowPage showPage;
        EditFormPage formPage;

        @BeforeEach
        void setup() throws Exception {

            TopPage.to(driver);

            when(meetingShowScenario.meetingOf(any(MeetingNumber.class))).thenReturn(meeting);
            showPage = ShowPage.to(driver, meetingNumber);
            showPage.setOrganizerNumber(driver, organizerNumber);

            when(meetingEditScenario.meetingOf(any(MeetingNumber.class))).thenReturn(meeting);
            formPage = showPage.moveToEditPage(EditFormPage.class);
        }

        @Test
        @SuppressWarnings("unchecked")
        void ミーティングを編集できる() throws Exception {

            formPage = (EditFormPage) formPage.addNewEmptyScheduleInput(EditFormPage.class);
            formPage = (EditFormPage) formPage.removeScheduleAt(0, EditFormPage.class);

            formPage.inputSubject("件名テスト1-編集後1");
            formPage.inputMemo("メモテスト1-編集後1");

            formPage.inputScheduleAt(0,
                    ScheduleConstructInput.of(2022, 4, 2, 13, StartMinute.HALF, Duration.OF_30_MINUTES));

            doNothing().when(meetingEditScenario).edit(any(Meeting.class), any(MeetingRegistrationRequest.class));
            EditCompletedPage completedPage = formPage.editMeeting(EditCompletedPage.class);

            assertEquals("ミーティングの編集が完了しました。", completedPage.processFinishedMessage());
            assertEquals("URLに変更はありませんが、必要な場合は参加者へ次のURLを連絡してください。:", completedPage.guidanceToContactToParticipants());
            assertTrue(completedPage.urlToShowMeeting().endsWith("/meeting/mt000000000000000000000001/show"));
            assertEquals("ミーティングの編集・削除を行う場合は、上記URLへアクセスするか、「ミーティングの詳細」をクリックしてください。", completedPage.guidanceToEditMeeting());
        }

        @Test
        void 編集時に参加者が出欠回答済みの場合はエラーになる() throws Exception {

            when(meetingEditScenario.meetingOf(any(MeetingNumber.class))).thenReturn(respondedMeeting);
            ErrorPage errorPage = formPage.editMeeting(ErrorPage.class);

            assertEquals("回答されているので編集・削除できません。", errorPage.errorMessage());
        }

        @Test
        void 日程候補が0件の場合はエラー() throws Exception {

            formPage.clearScheduleAt(0);
            EditFormPage errorFormPage = formPage.editMeeting(EditFormPage.class);

            assertEquals("日程を1件以上5件以下で指定してください。", errorFormPage.scheduleInputsErrorMessage());
        }

        @Test
        @SuppressWarnings("unchecked")
        void 追加する日程が過去の場合はエラー() throws Exception {

            ScheduleConstructInput constructInput =
                    ScheduleConstructInput.of(1984, 12, 31, 10, StartMinute.JUST, Duration.OF_1_HOUR);

            EditFormPage addedFormPage = (EditFormPage) formPage.addNewEmptyScheduleInput(EditFormPage.class);
            addedFormPage.inputScheduleAt(1, constructInput);
            EditFormPage errorFormPage = addedFormPage.editMeeting(EditFormPage.class);

            List<String> globalErrorMessages = errorFormPage.globalErrorMessages();

            assertEquals(1, globalErrorMessages.size());
            assertEquals("日程を追加する時は未来の日時を指定してください。", globalErrorMessages.get(0));
        }

        @Test
        void ミーティングを削除できる() throws Exception {

            doNothing().when(meetingEditScenario).delete(any(MeetingNumber.class));
            DeleteCompletedPage completedPage = formPage.deleteMeeting(DeleteCompletedPage.class);

            assertEquals("ミーティングの削除が完了しました。", completedPage.processFinishedMessage());
        }

        @AfterEach
        void tearDown() {
            if (driver == null) return;
            driver.quit();
        }
    }

    @Nested
    @DisplayName("表示時に出欠回答済みのミーティングが返ってくる")
    class CaseAttendedMeetingWhenGet extends MeetingIntegrationTest {

        @Autowired
        WebDriver driver;

        @MockBean
        MeetingShowScenario meetingShowScenario;

        @MockBean
        MeetingEditScenario meetingEditScenario;

        ShowPage showPage;

        @BeforeEach
        void setup() throws Exception {

            TopPage.to(driver);

            when(meetingShowScenario.meetingOf(any(MeetingNumber.class))).thenReturn(meeting);
            showPage = ShowPage.to(driver, meetingNumber);
            showPage.setOrganizerNumber(driver, organizerNumber);

            when(meetingEditScenario.meetingOf(any(MeetingNumber.class))).thenReturn(respondedMeeting);
        }

        @Test
        void 表示時に参加者が出欠回答済みの場合はエラーになる() throws Exception {

            ErrorPage errorPage = showPage.moveToEditPage(ErrorPage.class);

            assertEquals("回答されているので編集・削除できません。", errorPage.errorMessage());
        }

        @AfterEach
        void tearDown() {
            if (driver == null) return;
            driver.quit();
        }
    }
}
