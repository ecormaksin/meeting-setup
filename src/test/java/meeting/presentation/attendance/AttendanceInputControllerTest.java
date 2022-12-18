package meeting.presentation.attendance;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.AttendanceInputScenario;
import meeting.application.senario.MeetingShowScenario;
import meeting.domain.model.attendance.AttendanceRegistrationRequest;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.meeting.*;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.organizer.OrganizerNumberFake_2;
import meeting.domain.model.organizer.OrganizerNumberTestHelper;
import meeting.domain.model.participant.*;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.*;
import meeting.presentation.TopPage;
import meeting.presentation.attendance.page_objects.InputCompletedPage;
import meeting.presentation.attendance.page_objects.InputFormPage;
import meeting.presentation.meeting.page_objects.ShowPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class AttendanceInputControllerTest extends MeetingIntegrationTest {

    Logger logger = Logger.getLogger(AttendanceInputControllerTest.class.getName());

    MeetingNumber meetingNumber;
    Meeting meeting;
    Schedules schedules = Schedules.of(Arrays.asList(
            Schedule.of(ScheduleNumber.of(1),
                    ScheduleConstruct.of(MeetingDate.of(2022, 4, 1), StartHour.of(10), StartMinute.JUST, Duration.OF_1_HOUR)),
            Schedule.of(ScheduleNumber.of(2),
                    ScheduleConstruct.of(MeetingDate.of(2022, 4, 2), StartHour.of(11), StartMinute.HALF, Duration.OF_1_HOUR_30_MINUTES))
    ));

    OrganizerNumber organizerNumber;
    ParticipantNumber participantNumber;

    private Meeting showBaseMeeting(MeetingNumber meetingNumber) {

        Subject subject = Subject.of("件名テスト1");
        Memo memo = Memo.blank();
        OrganizerNumber organizerNumber = OrganizerNumberTestHelper.fromInt(1);

        return Meeting.of(
                meetingNumber,
                subject,
                memo,
                organizerNumber,
                schedules
        );
    }

    @BeforeEach
    void setup() throws Exception {
        meetingNumber = MeetingNumberTestHelper.fromInt(1);
        meeting = showBaseMeeting(meetingNumber);

        new OrganizerNumberFake_2();
        organizerNumber = OrganizerNumberFake_2.newNumber();

        new ParticipantNumberFake_1();
        participantNumber = ParticipantNumberFake_1.newNumber();
    }

    @Nested
    @DisplayName("出欠初回入力の場合")
    class CaseFirstAttendanceInputAsParticipant extends MeetingIntegrationTest {

        @Autowired
        WebDriver driver;

        @MockBean
        MeetingShowScenario meetingShowScenario;

        @MockBean
        AttendanceInputScenario attendanceInputScenario;

        ShowPage showPage;
        InputFormPage formPage;

        @BeforeEach
        void setup() throws Exception {

            TopPage.to(driver);

            when(meetingShowScenario.meetingOf(any(MeetingNumber.class))).thenReturn(meeting);
            showPage = ShowPage.to(driver, meetingNumber);
            showPage.setOrganizerNumber(driver, organizerNumber);
            showPage.setParticipantNumber(driver, participantNumber);

            when(attendanceInputScenario.latestParticipantName(any(ParticipantNumber.class))).thenReturn(ParticipantName.blank());
            when(attendanceInputScenario.schedules(any(MeetingNumber.class))).thenReturn(schedules);
            formPage = showPage.moveToAttendanceInputPage(InputFormPage.class);
        }

        @Test
        void 対象ページのオブジェクトが返ってくる() throws Exception {
            assertNotNull(formPage);
        }

        @Test
        void 参加者名が空欄() throws Exception {
            assertEquals("", formPage.participantNameValue());
        }

        @Test
        void 日程1件目の都合がバツ() throws Exception {
            assertEquals("×", formPage.convenienceCategoryValue(1));
        }

        @Test
        void 日程2件目の都合がバツ() throws Exception {
            assertEquals("×", formPage.convenienceCategoryValue(2));
        }

        @Test
        void 出欠コメントが空欄() throws Exception {
            assertEquals("", formPage.attendanceCommentValue());
        }

        @Test
        void 出欠を入力できる() throws Exception {
            formPage.inputParticipantName("参加者名テスト1");
            formPage.inputConvenienceCategory(1, ConvenienceCategory.マル);
            formPage.inputConvenienceCategory(2, ConvenienceCategory.サンカク);
            formPage.inputAttendanceComment("出欠コメントテスト1");

            when(attendanceInputScenario.participantNumberDuplicationStatus(
                    any(MeetingNumber.class),
                    any(ParticipantNumber.class)))
                    .thenReturn(ParticipantNumberDuplicationStatus.なし);
            when(attendanceInputScenario.participantNameDuplicationStatus(
                    any(MeetingNumber.class),
                    any(ParticipantName.class)))
                    .thenReturn(ParticipantNameDuplicationStatus.なし);
            doNothing().when(attendanceInputScenario).input(any(MeetingNumber.class), any(AttendanceRegistrationRequest.class));

            InputCompletedPage completedPage = formPage.inputAttendance(InputCompletedPage.class);

            assertEquals("出欠の入力が完了しました。内容を編集するには、上部の「ミーティングの詳細」をクリックしてください。", completedPage.processFinishedMessage());
        }

        @AfterEach
        void tearDown() {
            if (driver == null) return;
            driver.quit();
        }
    }
}
