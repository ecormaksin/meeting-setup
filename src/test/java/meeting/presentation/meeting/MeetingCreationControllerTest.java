package meeting.presentation.meeting;

import meeting.application.senario.MeetingCreationScenario;
import meeting.presentation.WebDriverConfig;
import meeting.presentation.WebDriverFactory;
import meeting.presentation.meeting.input.schedule.construct.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    @BeforeAll
    void initAll() {
        driver = webDriverFactory.webDriver();
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("初期表示の場合")
    class InitialView {

        CreationFormPage creationFormPage;

        @BeforeAll
        void initAll() {
            creationFormPage = CreationFormPage.to(driver);
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
        void 日程候補の入力欄が3件である() {
            assertEquals(3, creationFormPage.countOfScheduleInputs());
        }

        @Test
        void 日程候補の削除ボタンが2個である() {
            assertEquals(2, creationFormPage.countOfRemoveScheduleButtons());
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("追加ボタンの操作確認")
    class CheckAddScheduleButton {

        CreationFormPage creationFormPage;

        @BeforeAll
        void initAll() {
            creationFormPage = CreationFormPage.to(driver);
        }

        @Test
        void 日程候補の入力欄を上限まで追加した場合_追加ボタンが押下不可になる() {

            CreationFormPage addedFormPage = CreationFormPage.to(driver);

            for (int count = 0; count < 2; count++) {
                addedFormPage = addedFormPage.addNewEmptyScheduleInput();
            }

            assertFalse(addedFormPage.isAddScheduleButtonEnabled());
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("削除ボタンの操作確認")
    class CheckRemoveScheduleButton {

        CreationFormPage creationFormPage;

        @BeforeAll
        void initAll() {
            creationFormPage = CreationFormPage.to(driver);
        }

        @Test
        void 日程候補の削除ボタンをすべてクリックした場合_日程候補の入力1件_削除ボタン0個である() {

            for (int count = 2; count > 0; count--) {
                creationFormPage = creationFormPage.removeScheduleAt(count - 1);
            }

            assertEquals(1, creationFormPage.countOfScheduleInputs());
            assertEquals(0, creationFormPage.countOfRemoveScheduleButtons());
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("件名エラー確認")
    class CheckSubjectError {

        @Test
        void 件名未入力時はエラーになる() {
            CreationFormPage formPage = CreationFormPage.to(driver);

            LocalDate meetingDateValue = LocalDate.of(1985,1, 1);

            final ScheduleConstructInput constructInput = new ScheduleConstructInput(
                    MeetingDateInput.of(meetingDateValue),
                    StartHourInput.of(10),
                    StartMinuteInput.of(0),
                    DurationInput.ofMinute(60));

            formPage.inputScheduleAt(0, constructInput);

            CreationFormPage errorFormPage = formPage.createMeeting(CreationFormPage.class);

            assertEquals("件名は30文字以内で必ず入力してください。", errorFormPage.subjectErrorMessage());
        }
    }

    @AfterAll
    void tearDownAll() {
        if (driver == null) return;
        driver.quit();
    }
}
