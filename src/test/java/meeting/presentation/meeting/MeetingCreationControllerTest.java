package meeting.presentation.meeting;

import meeting.application.senario.MeetingCreationScenario;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.htmlunit.webdriver.LocalHostWebConnectionHtmlUnitDriver;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest(MeetingCreationController.class)
public class MeetingCreationControllerTest {

    @Autowired
    WebDriver driver;

    @MockBean
    MeetingCreationScenario meetingCreationScenario;

    CreationPage creationPage;

    @Nested
    @DisplayName("初期表示の場合")
    class InitialRequest {

        @BeforeEach
        void init() {
            creationPage = new CreationPage(driver).get();
        }

        @Test
        void _状態の確認() throws Exception {
            _ミーティング番号が設定されている();
            _件名が未入力である();
            _メモが未入力である();
            _主催者番号が設定されている();
        }

        private void _ミーティング番号が設定されている() {
            assertNotNull(creationPage.meetingNumberValue());
        }

        private void _件名が未入力である() {
            assertEquals("", creationPage.subjectValue());
        }

        private void _メモが未入力である() {
            assertEquals("", creationPage.memoValue());
        }

        private void _主催者番号が設定されている() {
            assertNotNull(creationPage.organizerNumberValue());
        }
    }

    @AfterEach
    void tearDown() {
        if (driver == null) return;
        driver.quit();
    }
}
