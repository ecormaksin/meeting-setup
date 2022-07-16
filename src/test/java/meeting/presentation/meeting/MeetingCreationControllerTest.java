package meeting.presentation.meeting;

import meeting.application.senario.MeetingCreationScenario;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.OrganizerNumber;
import meeting.presentation.WebDriverConfig;
import meeting.presentation.WebDriverFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(MeetingCreationController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import({WebDriverConfig.class})
public class MeetingCreationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebDriverFactory webDriverFactory;

    WebDriver driver;

    @MockBean
    MeetingCreationScenario meetingCreationScenario;

    CreationFormPage creationFormPage;

    @BeforeAll
    void initAll() {
        driver = webDriverFactory.webDriver();
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("初期表示の場合")
    class InitialRequest {

        @BeforeAll
        void initAll() {
            creationFormPage = new CreationFormPage(driver).get();
        }

        @Test
        void ミーティング番号が設定されている() {
            final String meetingNumberValue = creationFormPage.meetingNumberValue();
            assertTrue(meetingNumberValue.matches(MeetingNumber.REGEXP));
        }

        @Test
        void 件名が未入力である() {
            assertEquals("", creationFormPage.subjectValue());
        }

        @Test
        void メモが未入力である() {
            assertEquals("", creationFormPage.memoValue());
        }

        @Test
        void 主催者番号が設定されている() {
            final String organizerNumberValue = creationFormPage.organizerNumberValue();
            assertTrue(organizerNumberValue.matches(OrganizerNumber.REGEXP));
        }

        @Test
        void 日程候補の取得() {
            List<WebElement> schedules = creationFormPage.scheduleRows();
            System.out.println(schedules.toString());
        }
    }

    @AfterAll
    void tearDownAll() {
        if (driver == null) return;
        driver.quit();
    }
}
