package meeting.presentation.attendance;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.AttendanceEditScenario;
import meeting.application.senario.MeetingShowScenario;
import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.attendance.convenience.Conveniences;
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
import meeting.presentation.attendance.page_objects.EditCompletedPage;
import meeting.presentation.attendance.page_objects.EditFormPage;
import meeting.presentation.attendance.page_objects.DeleteCompletedPage;
import meeting.presentation.meeting.page_objects.ShowPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

public class AttendanceEditControllerTest extends MeetingIntegrationTest {

    Logger logger = Logger.getLogger(AttendanceEditControllerTest.class.getName());

    @Autowired
    WebDriver driver;

    @MockBean
    MeetingShowScenario meetingShowScenario;

    @MockBean
    AttendanceEditScenario attendanceEditScenario;

    ShowPage showPage;

    EditFormPage formPage;

    MeetingNumber meetingNumber;
    Meeting meeting;
    Schedules schedules = Schedules.of(Arrays.asList(
            Schedule.of(ScheduleNumber.of(1),
                    ScheduleConstruct.of(MeetingDate.of(2022, 4, 1), StartHour.of(10), StartMinute.JUST, Duration.OF_1_HOUR)),
            Schedule.of(ScheduleNumber.of(2),
                    ScheduleConstruct.of(MeetingDate.of(2022, 4, 2), StartHour.of(11), StartMinute.HALF, Duration.OF_1_HOUR_30_MINUTES))
    ));

    Attendance attendance = Attendance.of(
            AttendanceNumberTestHelper.fromInt(1),
            ParticipantNumberTestHelper.fromInt(1),
            ParticipantName.of("参加者名テスト1"),
            Conveniences.of(Arrays.asList(
                    Convenience.of(ScheduleNumber.of(1), ConvenienceCategory.GOOD),
                    Convenience.of(ScheduleNumber.of(2), ConvenienceCategory.MODEST)
            )),
            AttendanceComment.of("出欠コメントテスト"));

    Attendances attendances = Attendances.of(Arrays.asList(attendance));

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
                schedules,
                attendances
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

        TopPage.to(driver);

        when(meetingShowScenario.meetingOf(any(MeetingNumber.class))).thenReturn(meeting);
        showPage = ShowPage.to(driver, meetingNumber);
        showPage.setOrganizerNumber(driver, organizerNumber);
        showPage.setParticipantNumber(driver, participantNumber);

        when(attendanceEditScenario.schedules(any(MeetingNumber.class))).thenReturn(schedules);
        when(attendanceEditScenario.attendanceOf(any(AttendanceNumber.class))).thenReturn(attendance);

        formPage = showPage.moveToAttendanceEditPage(1, EditFormPage.class);
    }

    @Test
    void 対象ページのオブジェクトが返ってくる() throws Exception {
        assertNotNull(formPage);
    }

    @Test
    void 参加者名が出欠入力時の状態() throws Exception {
        assertEquals("参加者名テスト1", formPage.participantNameValue());
    }

    @Test
    void 日程1件目の都合がマル() throws Exception {
        assertEquals("○", formPage.convenienceCategoryText(1));
    }

    @Test
    void 日程2件目の都合がサンカク() throws Exception {
        assertEquals("△", formPage.convenienceCategoryText(2));
    }

    @Test
    void 出欠コメントが出欠入力時の状態() throws Exception {
        assertEquals("出欠コメントテスト", formPage.attendanceCommentValue());
    }

    @Test
    void 出欠を編集できる() throws Exception {
        formPage.inputParticipantName("参加者名編集後");
        formPage.inputConvenienceCategory(1, ConvenienceCategory.MODEST);
        formPage.inputConvenienceCategory(2, ConvenienceCategory.GOOD);
        formPage.inputAttendanceComment("出欠コメントテスト1−編集後");

        when(attendanceEditScenario.participantNameDuplicationStatus(
                any(MeetingNumber.class),
                any(AttendanceNumber.class),
                any(ParticipantName.class)))
                .thenReturn(ParticipantNameDuplicationStatus.なし);
        doNothing().when(attendanceEditScenario).edit(any(MeetingNumber.class), any(AttendanceRegistrationRequest.class));

        EditCompletedPage completedPage = formPage.editAttendance(EditCompletedPage.class);

        assertEquals("出欠の編集が完了しました。内容を編集するには、上部の「ミーティングの詳細」をクリックしてください。", completedPage.processFinishedMessage());
    }

    @Test
    void 参加者名が最大文字数を超えている場合はエラーになる() {

        formPage.inputParticipantName("０１２３４５６７８９０");
        EditFormPage errorFormPage = formPage.editAttendance(EditFormPage.class);

        assertEquals("参加者名は10文字以内で必ず入力してください。", errorFormPage.participantNameErrorMessage());
    }

    @Test
    void 参加者名が重複している場合はエラーになる() {

        when(attendanceEditScenario.participantNameDuplicationStatus(
                any(MeetingNumber.class),
                any(AttendanceNumber.class),
                any(ParticipantName.class)))
                .thenReturn(ParticipantNameDuplicationStatus.あり);

        EditFormPage errorFormPage = formPage.editAttendance(EditFormPage.class);

        assertEquals("参加者名は重複しないように入力してください。", errorFormPage.participantNameErrorMessage());
    }

    @Test
    void 出欠を削除できる() {

        doNothing().when(attendanceEditScenario).delete(any(AttendanceNumber.class));
        DeleteCompletedPage completedPage = formPage.deleteAttendance(DeleteCompletedPage.class);

        assertEquals("出欠の削除が完了しました。", completedPage.processFinishedMessage());
    }

    @AfterEach
    void tearDown() {
        if (driver == null) return;
        driver.quit();
    }
}
