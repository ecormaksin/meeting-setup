package meeting._integration_test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import meeting.domain.model.meeting.MeetingNumberFake_1;
import meeting.domain.model.meeting.RegistrationRequestDateTimeFake_19841231_1023;
import meeting.domain.model.organizer.OrganizerNumberFake_1;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import meeting.infrastructure.datasource.ScheduleNumberColumnFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MeetingEditIntegrationTest {

    @Container
    private final MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Autowired
    WebDriver driver;

    @Nested
    @DisplayName("編集・日程候補追加の場合")
    class MeetingEditAddSchedule {

        @Test
        @DatabaseSetup("testdata/edit_meeting_base.xlsx")
        @ExpectedDatabase(value = "testdata/edit_meeting_expected_01_add_schedule.xlsx",
                assertionMode = DatabaseAssertionMode.NON_STRICT,
                columnFilters = { ScheduleNumberColumnFilter.class })
        void ミーティングを編集できる() {

            new OrganizerNumberFake_1();
            new RegistrationRequestDateTimeFake_19841231_1023();
        }
    }

    @AfterEach
    void tearDown() {
        if (driver == null) return;
        driver.quit();
    }
}
