package meeting.presentation.meeting;

import meeting.application.senario.MeetingCreationScenario;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest(MeetingCreationController.class)
public class MeetingCreationControllerTest {

    @Autowired
    WebDriver driver;

    @MockBean
    MeetingCreationScenario meetingCreationScenario;

    @Nested
    @DisplayName("初期表示の場合")
    class InitialRequest {

        CreationPage creationPage;

        @BeforeEach
        void init() {
            creationPage = new CreationPage(driver).get();
        }

        @Test
        void _ミーティング番号が設定されている() throws Exception {
            final String meetingNumberValue = creationPage.meetingNumberValue();
            assertNotNull(meetingNumberValue);
        }

        @Test
        void _主催者番号が設定されている() throws Exception {
            final String organizerNumberValue = creationPage.organizerNumberValue();
            assertNotNull(organizerNumberValue);
        }

        @AfterEach
        void tearDown() {
            if (driver == null) return;
            driver.close();
        }
    }
}
