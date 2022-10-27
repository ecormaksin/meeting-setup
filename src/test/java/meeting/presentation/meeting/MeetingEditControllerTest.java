package meeting.presentation.meeting;

import meeting.application.senario.MeetingEditScenario;
import meeting.application.senario.MeetingShowScenario;
import meeting.domain.model.meeting.*;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.organizer.OrganizerNumberFake_1;
import meeting.domain.model.organizer.OrganizerNumberTestHelper;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.Duration;
import meeting.domain.model.schedule.construct.ScheduleConstruct;
import meeting.domain.model.schedule.construct.StartMinute;
import meeting.presentation.TestWebConfig;
import meeting.presentation.TopController;
import meeting.presentation.TopPage;
import meeting.presentation.WebConfig;
import meeting.presentation.meeting.input.schedule.construct.ScheduleConstructInput;
import meeting.presentation.meeting.page_objects.EditCompletedPage;
import meeting.presentation.meeting.page_objects.EditFormPage;
import meeting.presentation.meeting.page_objects.ShowPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest({
        MeetingEditController.class,
        TopController.class,
        MeetingShowController.class})
@Import({WebConfig.class, TestWebConfig.class})
public class MeetingEditControllerTest {

    Logger logger = Logger.getLogger(MeetingEditControllerTest.class.getName());

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebDriver driver;

    @MockBean
    MeetingShowScenario meetingShowScenario;

    @MockBean
    MeetingEditScenario meetingEditScenario;

    @Test
    void ミーティングを編集できる() {

        MeetingNumber meetingNumber = MeetingNumberTestHelper.fromInt(1);
        Subject subject = Subject.of("件名テスト1");
        Memo memo = Memo.blank();
        OrganizerNumber organizerNumber = OrganizerNumberTestHelper.fromInt(1);
        Schedules schedules = Schedules.of(Arrays.asList(
                Schedule.of(ScheduleNumber.of(1),
                        ScheduleConstruct.of(2022, 4, 1, 10, StartMinute.JUST, Duration.OF_1_HOUR))
        ));
        Meeting meeting = Meeting.of(
                meetingNumber,
                subject,
                memo,
                organizerNumber,
                schedules
        );

        when(meetingShowScenario.meetingOf(any(MeetingNumber.class))).thenReturn(meeting);
        when(meetingEditScenario.meetingOf(any(MeetingNumber.class))).thenReturn(meeting);
        doNothing().when(meetingEditScenario).edit(any(Meeting.class), any(MeetingRegistrationRequest.class));

        new OrganizerNumberFake_1();
        new RegistrationRequestDateTimeFake_19841231_1023();

        TopPage.to(driver);
        ShowPage showPage = ShowPage.to(driver, meetingNumber);
        EditFormPage formPage = showPage.moveToEditPage();

        formPage.inputSubject("件名テスト1-編集後1");
        formPage.inputMemo("メモテスト1-編集後1");

        formPage.inputScheduleAt(0,
                ScheduleConstructInput.of(2022,4, 2, 13, StartMinute.HALF, Duration.OF_30_MINUTES));

        EditCompletedPage completedPage = formPage.editMeeting(EditCompletedPage.class);

        logger.info(driver.getCurrentUrl());
        logger.info(driver.getPageSource());

        assertNotNull(completedPage);
        assertEquals("ミーティングの編集が完了しました。", completedPage.processFinishedMessage());
        assertEquals("URLに変更はありませんが、必要な場合は参加者へ次のURLを連絡してください。:", completedPage.guidanceToContactToParticipants());
        assertTrue(completedPage.urlToShowMeeting().endsWith("/meeting/mt000000000000000000000001/show"));
        assertEquals("ミーティングの編集・削除を行う場合は、上記URLへアクセスするか、「ミーティングの詳細」をクリックしてください。", completedPage.guidanceToEditMeeting());
    }
}
