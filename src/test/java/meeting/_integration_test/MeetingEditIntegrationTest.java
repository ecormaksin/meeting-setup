package meeting._integration_test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import meeting.MeetingIntegrationTest;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.MeetingNumberTestHelper;
import meeting.domain.model.meeting.RegistrationRequestDateTimeFake_19841231_1023;
import meeting.domain.model.organizer.OrganizerNumberFake_1;
import meeting.domain.model.schedule.construct.Duration;
import meeting.domain.model.schedule.construct.StartMinute;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import meeting.infrastructure.datasource.ScheduleNumberColumnFilter;
import meeting.presentation.TopPage;
import meeting.presentation.meeting.EditCompletedPage;
import meeting.presentation.meeting.EditFormPage;
import meeting.presentation.meeting.ShowPage;
import meeting.presentation.meeting.input.schedule.construct.ScheduleConstructInput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
public class MeetingEditIntegrationTest extends MeetingIntegrationTest {

    @Container
    private static final MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Autowired
    WebDriver driver;

    @Nested
    @DisplayName("編集・日程候補追加の場合")
    class MeetingEditAddSchedule extends MeetingIntegrationTest {

        @Test
        @DatabaseSetup("testdata/edit_meeting_base.xlsx")
        @ExpectedDatabase(value = "testdata/edit_meeting_expected_01_add_schedule.xlsx",
                assertionMode = DatabaseAssertionMode.NON_STRICT,
                columnFilters = { ScheduleNumberColumnFilter.class })
        void ミーティングを編集できる() {

            new OrganizerNumberFake_1();
            new RegistrationRequestDateTimeFake_19841231_1023();

            TopPage topPage = TopPage.to(driver);
            MeetingNumber meetingNumber = MeetingNumberTestHelper.fromInt(1);
            ShowPage showPage = ShowPage.to(driver, meetingNumber);
            EditFormPage formPage = showPage.moveToEditPage();

            formPage.inputSubject("件名テスト1-編集後1");
            formPage.inputMemo("メモテスト1-編集後1");

            formPage.inputScheduleAt(0,
                    ScheduleConstructInput.of(2022,4, 5, 10, StartMinute.HALF, Duration.HALF_AN_HOUR));
            formPage.inputScheduleAt(1,
                    ScheduleConstructInput.of(2022,4, 4, 11, StartMinute.JUST, Duration.ONE_HOUR));
            formPage.inputScheduleAt(2,
                    ScheduleConstructInput.of(2022,4, 3, 13, StartMinute.HALF, Duration.ONE_HOUR_AND_A_HALF));
            formPage.inputScheduleAt(3,
                    ScheduleConstructInput.of(2022,4, 2, 15, StartMinute.JUST, Duration.TWO_HOURS));

            EditCompletedPage completedPage = formPage.editMeeting(EditCompletedPage.class);

            assertNotNull(completedPage);
        }
    }

    @AfterEach
    void tearDown() {
        if (driver == null) return;
        driver.quit();
    }
}
