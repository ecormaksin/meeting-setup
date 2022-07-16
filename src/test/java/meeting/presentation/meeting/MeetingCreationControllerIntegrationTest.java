package meeting.presentation.meeting;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.MeetingCreationScenario;
import meeting.domain.model.meeting.RegistrationRequestDateTime;
import meeting.presentation.WebDriverFactory;
import meeting.presentation.meeting.input.schedule.construct.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MeetingCreationControllerIntegrationTest extends MeetingIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebDriverFactory webDriverFactory;

    WebDriver driver;

    @MockBean
    RegistrationRequestDateTime registrationRequestDateTime;

    @Autowired
    MeetingCreationScenario meetingCreationScenario;

    CreationFormPage formPage;

    @BeforeEach
    void init() {
        driver = webDriverFactory.webDriver();
        formPage = new CreationFormPage(driver).get();
    }

    @Test
    void メモなし_日程1件の場合() throws Exception {

        CreationCompletedPage completedPage;

        formPage.inputSubject("件名テスト１");

        final ScheduleConstructInput constructInput = new ScheduleConstructInput(
                new MeetingDateInput("2020-07-01"),
                new StartHourInput("10"),
                new StartMinuteInput("0"),
                new DurationInput("60"));
        formPage.inputScheduleAt(0, constructInput);

        try (MockedStatic<RegistrationRequestDateTime> requestDateTimeMockedStatic = Mockito.mockStatic(RegistrationRequestDateTime.class)) {
            requestDateTimeMockedStatic.when(RegistrationRequestDateTime::now).thenReturn(
                    new RegistrationRequestDateTime(LocalDateTime.of(2022, 6, 30, 10, 0, 0))
            );
            completedPage = formPage.createMeeting(CreationCompletedPage.class);
        }

        assertNotNull(completedPage);
    }

    @AfterEach
    void tearDownAll() {
        if (driver == null) return;
        driver.quit();
    }
}
