package meeting.presentation.meeting;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.MeetingCreationScenario;
import meeting.domain.model.meeting.MeetingNumberFake_1;
import meeting.domain.model.meeting.OrganizerNumberFake_1;
import meeting.domain.model.meeting.RegistrationRequestDateTimeFake_19841231_1023;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import meeting.presentation.meeting.input.schedule.construct.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
public class MeetingCreationControllerIntegrationTest extends MeetingIntegrationTest {

    @Container
    private static final MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebDriver driver;

    @Autowired
    MeetingCreationScenario meetingCreationScenario;

    @RepeatedTest(value = 5, name = "メモなし・日程1件（{currentRepetition}行目に入力）の場合")
    @DirtiesContext
    void testWithoutMemoAndOneSchedule(RepetitionInfo repetitionInfo) {

        final int currentRepetition =  repetitionInfo.getCurrentRepetition();
        final int index = currentRepetition - 1;

        new OrganizerNumberFake_1();
        new MeetingNumberFake_1();
        new RegistrationRequestDateTimeFake_19841231_1023();

        CreationFormPage formPage = CreationFormPage.to(driver);

        final int scheduleAddClickTotalCount = currentRepetition - 3;
        for (int scheduleAddClick = 0; scheduleAddClick < scheduleAddClickTotalCount; scheduleAddClick++) {
            formPage = formPage.addNewEmptyScheduleInput();
        }

        formPage.inputSubject("件名テスト" + currentRepetition);

        LocalDate meetingDateValue = LocalDate.of(1985,1, 1);

        final ScheduleConstructInput constructInput = new ScheduleConstructInput(
                MeetingDateInput.of(meetingDateValue),
                StartHourInput.of(10),
                StartMinuteInput.of(0),
                DurationInput.ofMinute(60));

        formPage.inputScheduleAt(index, constructInput);

        CreationCompletedPage completedPage = formPage.createMeeting(CreationCompletedPage.class);

        assertNotNull(completedPage);
    }

    @AfterEach
    void tearDown() {
        if (driver == null) return;
        driver.quit();
    }
}
