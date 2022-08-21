package meeting.presentation.meeting;

import meeting.application.senario.MeetingCreationScenario;
import meeting.domain.model.meeting.RegistrationRequestDateTimeFake_19841231_1023;
import meeting.presentation.meeting.input.schedule.construct.ScheduleConstructInput;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@WebMvcTest(MeetingCreationController.class)
public class MeetingCreationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebDriver driver;

    @MockBean
    MeetingCreationScenario meetingCreationScenario;

    CreationFormPage formPage;

    @BeforeEach
    void init() {
        formPage = CreationFormPage.to(driver);
    }

    @Nested
    @DisplayName("初期表示の場合")
    class InitialView {

        @Test
        void 件名が未入力である() {
            assertEquals("", formPage.subjectValue());
        }

        @Test
        void メモが未入力である() {
            assertEquals("", formPage.memoValue());
        }

        @Test
        void 日程候補の入力欄が3件である() {
            assertEquals(3, formPage.countOfScheduleInputs());
        }

        @Test
        void 日程候補の削除ボタンが2個である() {
            assertEquals(2, formPage.countOfRemoveScheduleButtons());
        }
    }

    @Nested
    @DisplayName("追加ボタンの操作確認")
    class CheckAddScheduleButton {

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
    @DisplayName("削除ボタンの操作確認")
    class CheckRemoveScheduleButton {

        @Test
        void 日程候補の削除ボタンをすべてクリックした場合_日程候補の入力1件_削除ボタン0個である() {

            for (int count = 2; count > 0; count--) {
                formPage = formPage.removeScheduleAt(count - 1);
            }

            assertEquals(1, formPage.countOfScheduleInputs());
            assertEquals(0, formPage.countOfRemoveScheduleButtons());
        }
    }

    @Nested
    @DisplayName("件名のエラー確認")
    class CheckSubjectError {

        @Test
        void 件名未入力時はエラー() {

            final ScheduleConstructInput constructInput =
                    ScheduleConstructInput.of(1985,1, 1, 10, 0, 60);

            formPage.inputScheduleAt(0, constructInput);

            CreationFormPage errorFormPage = formPage.createMeeting(CreationFormPage.class);

            assertEquals("件名は30文字以内で必ず入力してください。", errorFormPage.subjectErrorMessage());
        }

        @Test
        void 件名の文字数が上限を超過している場合はエラー() {

            formPage.inputSubject("あ".repeat(31));
            MeetingCreationControllerTest.this.inputValidSampleSchedule();

            CreationFormPage errorFormPage = formPage.createMeeting(CreationFormPage.class);

            assertEquals("件名は30文字以内で必ず入力してください。", errorFormPage.subjectErrorMessage());
        }
    }

    @Nested
    @DisplayName("メモのエラー確認")
    class CheckMemoError {

        @Test
        void メモの文字数が上限を超過している場合はエラー() {

            MeetingCreationControllerTest.this.inputValidSampleSubject();
            formPage.inputMemo("あ".repeat(101));
            MeetingCreationControllerTest.this.inputValidSampleSchedule();

            CreationFormPage errorFormPage = formPage.createMeeting(CreationFormPage.class);

            assertEquals("メモは100文字以内で入力してください。", errorFormPage.memoErrorMessage());
        }
    }

    @Nested
    @DisplayName("日程候補のエラー確認")
    class CheckScheduleError {

        @Test
        void 日程候補が0件の場合はエラー() {

            MeetingCreationControllerTest.this.inputValidSampleSubject();

            CreationFormPage errorFormPage = formPage.createMeeting(CreationFormPage.class);

            assertEquals("日程を1件以上5件以下で指定してください。", errorFormPage.scheduleInputsErrorMessage());
        }

        @Test
        void 日程候補が重複している場合はエラー() {

            new RegistrationRequestDateTimeFake_19841231_1023();

            MeetingCreationControllerTest.this.inputValidSampleSubject();

            final ScheduleConstructInput constructInput =
                    ScheduleConstructInput.of(1985,1, 1, 10, 0, 60);

            for (int index = 0; index <= 1; index++) {
                formPage.inputScheduleAt(index, constructInput);
            }

            CreationFormPage errorFormPage = formPage.createMeeting(CreationFormPage.class);

            assertEquals("日程は重複のないように指定してください。", errorFormPage.scheduleInputsErrorMessage());
        }
    }

    void inputValidSampleSubject() {
        formPage.inputSubject("件名テスト");
    }

    void inputValidSampleSchedule() {

        final ScheduleConstructInput constructInput =
                ScheduleConstructInput.of(1985,1, 1, 10, 0, 60);

        formPage.inputScheduleAt(0, constructInput);
    }

    @AfterEach
    void tearDown() {
        if (driver == null) return;
        driver.quit();
    }
}
