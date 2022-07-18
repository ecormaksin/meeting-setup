package meeting.presentation.meeting;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.MeetingCreationScenario;
import meeting.domain.model.meeting.*;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import meeting.presentation.WebDriverFactory;
import meeting.presentation.meeting.input.schedule.construct.*;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MeetingCreationControllerIntegrationTest extends MeetingIntegrationTest {

    @ClassRule
    private static final MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebDriverFactory webDriverFactory;

    WebDriver driver;

    @Autowired
    MeetingCreationScenario meetingCreationScenario;

    CreationFormPage formPage;

    @BeforeEach
    void init() {
        driver = webDriverFactory.webDriver();
        formPage = CreationFormPage.to(driver);
    }

    @Test
    void メモなし_日程1件の場合() throws Exception {

        CreationCompletedPage completedPage;

        try (MockedStatic<RegistrationRequestDateTime> requestDateTimeMockedStatic = Mockito.mockStatic(RegistrationRequestDateTime.class)) {

            requestDateTimeMockedStatic.when(RegistrationRequestDateTime::now).thenReturn(
                    new RegistrationRequestDateTime(LocalDateTime.of(2022, 6, 30, 10, 0, 0))
            );

            formPage.inputSubject("件名テスト１");

            final ScheduleConstructInput constructInput = new ScheduleConstructInput(
                    new MeetingDateInput("2020-07-01"),
                    new StartHourInput("10"),
                    new StartMinuteInput("0"),
                    new DurationInput("60"));
            formPage.inputScheduleAt(0, constructInput);

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
