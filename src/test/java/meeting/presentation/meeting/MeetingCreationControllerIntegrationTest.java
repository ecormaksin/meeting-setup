package meeting.presentation.meeting;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.MeetingCreationScenario;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import meeting.presentation.meeting.input.schedule.construct.*;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MeetingCreationControllerIntegrationTest extends MeetingIntegrationTest {

    @ClassRule
    private static final MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebDriver driver;

    @Autowired
    MeetingCreationScenario meetingCreationScenario;

    CreationFormPage formPage;

    @BeforeEach
    void init() {
//        driver = webDriverFactory.webDriver();
        formPage = CreationFormPage.to(driver);
    }

    @Test
    void メモなし_日程1件の場合_1行目に入力() throws Exception {

        CreationCompletedPage completedPage = createMeetingWithoutMemoAndOneSchedule(0);

        assertNotNull(completedPage);
    }

    @Test
    void メモなし_日程1件の場合_2行目に入力() throws Exception {

        CreationCompletedPage completedPage = createMeetingWithoutMemoAndOneSchedule(1);

        assertNotNull(completedPage);
    }

    private CreationCompletedPage createMeetingWithoutMemoAndOneSchedule(final int index) {

        formPage.inputSubject("件名テスト" + (index + 1));

        LocalDate meetingDateValue = LocalDate.now().plusDays(1);

        final ScheduleConstructInput constructInput = new ScheduleConstructInput(
                MeetingDateInput.of(meetingDateValue),
                StartHourInput.of(10),
                StartMinuteInput.of(0),
                DurationInput.ofMinute(60));
        formPage.inputScheduleAt(index, constructInput);

        CreationCompletedPage completedPage = formPage.createMeeting(CreationCompletedPage.class);

        return completedPage;
    }

    @AfterEach
    void tearDown() {
        if (driver == null) return;
        driver.quit();
    }
}
