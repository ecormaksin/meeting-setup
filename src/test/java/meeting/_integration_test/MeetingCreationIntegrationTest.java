package meeting._integration_test;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.MeetingCreationScenario;
import meeting.domain.model.meeting.MeetingNumberFake_1;
import meeting.domain.model.organizer.OrganizerNumberFake_1;
import meeting.domain.model.meeting.RegistrationRequestDateTimeFake_19841231_1023;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import meeting.presentation.meeting.CreationCompletedPage;
import meeting.presentation.meeting.CreationFormPage;
import meeting.presentation.meeting.input.schedule.construct.ScheduleConstructInput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
public class MeetingCreationIntegrationTest extends MeetingIntegrationTest {

    @Container
    private final MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Autowired
    WebDriver driver;

    @RepeatedTest(value = 5, name = "メモなし・日程1件（{currentRepetition}行目に入力）の場合")
    @DirtiesContext
    void testWithoutMemoAndOneSchedule(RepetitionInfo repetitionInfo) {

        int currentRepetition =  repetitionInfo.getCurrentRepetition();
        int index = currentRepetition - 1;

        new OrganizerNumberFake_1();
        new MeetingNumberFake_1();
        new RegistrationRequestDateTimeFake_19841231_1023();

        CreationFormPage formPage = CreationFormPage.to(driver);

        int scheduleAddClickTotalCount = currentRepetition - 3;
        for (int scheduleAddClick = 0; scheduleAddClick < scheduleAddClickTotalCount; scheduleAddClick++) {
            formPage = (CreationFormPage) formPage.addNewEmptyScheduleInput(CreationFormPage.class);
        }

        formPage.inputSubject("件名テスト" + currentRepetition);

        ScheduleConstructInput constructInput =
                ScheduleConstructInput.of(1985,1, 1, 10, 0, 60);

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
