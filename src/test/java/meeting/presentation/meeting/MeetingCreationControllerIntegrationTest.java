package meeting.presentation.meeting;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.MeetingCreationScenario;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import meeting.presentation.meeting.input.schedule.construct.*;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
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
        formPage = CreationFormPage.to(driver);
    }

    @RepeatedTest(value = 3, name = "メモなし・日程1件（{currentRepetition}行目に入力）の場合")
    void testWithoutMemoAndOneSchedule(RepetitionInfo repetitionInfo) throws Exception {

        final int currentRepetition = repetitionInfo.getCurrentRepetition();
        formPage.inputSubject("件名テスト" + currentRepetition);

        LocalDate meetingDateValue = LocalDate.now().plusDays(1);

        final ScheduleConstructInput constructInput = new ScheduleConstructInput(
                MeetingDateInput.of(meetingDateValue),
                StartHourInput.of(10),
                StartMinuteInput.of(0),
                DurationInput.ofMinute(60));

        final int index = currentRepetition - 1;
        formPage.inputScheduleAt(index, constructInput);

        CreationCompletedPage completedPage = formPage.createMeeting(CreationCompletedPage.class);

        assertNotNull(completedPage);
    }
}
