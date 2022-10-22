package meeting.presentation.meeting;

import meeting.application.senario.MeetingCreationScenario;
import meeting.domain.model.meeting.RegistrationRequestDateTimeFake_19841231_1023;
import meeting.domain.model.schedule.construct.Duration;
import meeting.domain.model.schedule.construct.StartMinute;
import meeting.presentation.TestWebConfig;
import meeting.presentation.WebConfig;
import meeting.presentation.meeting.input.schedule.construct.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@WebMvcTest(MeetingCreationController.class)
@Import({WebConfig.class, TestWebConfig.class})
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
        @SuppressWarnings("unchecked")
        void 日程候補の入力欄を上限まで追加した場合_追加ボタンが押下不可になる() {

            CreationFormPage addedFormPage = CreationFormPage.to(driver);

            for (int count = 0; count < 2; count++) {
                addedFormPage = (CreationFormPage) addedFormPage.addNewEmptyScheduleInput(CreationFormPage.class);
            }

            assertFalse(addedFormPage.isAddScheduleButtonEnabled());
        }
    }

    @Nested
    @DisplayName("削除ボタンの操作確認")
    class CheckRemoveScheduleButton {

        @Test
        @SuppressWarnings("unchecked")
        void 日程候補の削除ボタンをすべてクリックした場合_日程候補の入力1件_削除ボタン0個である() {

            for (int count = 2; count > 0; count--) {
                formPage = (CreationFormPage) formPage.removeScheduleAt(count - 1, CreationFormPage.class);
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

            ScheduleConstructInput constructInput =
                    ScheduleConstructInput.of(1985,1, 1, 10, StartMinute.JUST, Duration.ONE_HOUR);

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

            ScheduleConstructInput constructInput =
                    ScheduleConstructInput.of(1985,1, 1, 10, StartMinute.JUST, Duration.ONE_HOUR);

            for (int index = 0; index <= 1; index++) {
                formPage.inputScheduleAt(index, constructInput);
            }

            CreationFormPage errorFormPage = formPage.createMeeting(CreationFormPage.class);

            assertEquals("日程は重複のないように指定してください。", errorFormPage.scheduleInputsErrorMessage());
        }

        @Test
        void 開催日が未入力の場合はエラー() {

            MeetingCreationControllerTest.this.inputValidSampleSubject();

            int index = 0;
            formPage.inputStartHourAt(index, StartHourInput.of(10));
            formPage.inputStartMinuteAt(index, StartMinuteInput.of(0));
            formPage.inputDurationAt(index, DurationInput.of(60));

            CreationFormPage errorFormPage = formPage.createMeeting(CreationFormPage.class);

            assertEquals("", errorFormPage.scheduleInputsErrorMessage());
            assertEquals("開催日を入力してください。", errorFormPage.meetingDateEmptyErrorMessageAt(index));
        }

        @Test
        void 開始時間が未入力の場合はエラー() {

            MeetingCreationControllerTest.this.inputValidSampleSubject();

            int index = 1;
            formPage.inputMeetingDateAt(index, MeetingDateInput.of(2013, 4, 5));
            formPage.inputStartMinuteAt(index, StartMinuteInput.of(0));
            formPage.inputDurationAt(index, DurationInput.of(60));

            CreationFormPage errorFormPage = formPage.createMeeting(CreationFormPage.class);

            assertEquals("", errorFormPage.scheduleInputsErrorMessage());
            assertEquals("開始時間を入力してください。", errorFormPage.startHourEmptyErrorMessageAt(index));
        }

        @ParameterizedTest(name = "{0}時の場合")
        @ValueSource(ints = {8, 18})
        void 開始時間が指定範囲外の場合はエラー(int hour) {

            MeetingCreationControllerTest.this.inputValidSampleSubject();

            int index = 1;
            formPage.inputMeetingDateAt(index, MeetingDateInput.of(2013, 4, 5));
            formPage.inputStartHourAt(index, StartHourInput.of(hour));
            formPage.inputStartMinuteAt(index, StartMinuteInput.of(0));
            formPage.inputDurationAt(index, DurationInput.of(60));

            CreationFormPage errorFormPage = formPage.createMeeting(CreationFormPage.class);

            assertEquals("", errorFormPage.scheduleInputsErrorMessage());
            assertEquals("開始時間は9時から17時の間で指定してください。", errorFormPage.startHourInvalidErrorMessageAt(index));
        }

        @Test
        void 開始分が未入力の場合はエラー() {

            MeetingCreationControllerTest.this.inputValidSampleSubject();

            int index = 2;
            formPage.inputMeetingDateAt(index, MeetingDateInput.of(2013, 5, 6));
            formPage.inputStartHourAt(index, StartHourInput.of(11));
            formPage.inputDurationAt(index, DurationInput.of(90));

            CreationFormPage errorFormPage = formPage.createMeeting(CreationFormPage.class);

            assertEquals("", errorFormPage.scheduleInputsErrorMessage());
            assertEquals("開始分を入力してください。", errorFormPage.startMinuteEmptyErrorMessageAt(index));
        }

        @Test
        void 会議時間が未入力の場合はエラー() {

            MeetingCreationControllerTest.this.inputValidSampleSubject();

            int index = 0;
            formPage.inputMeetingDateAt(index, MeetingDateInput.of(2013, 6, 7));
            formPage.inputStartHourAt(index, StartHourInput.of(13));
            formPage.inputStartMinuteAt(index, StartMinuteInput.of(30));

            CreationFormPage errorFormPage = formPage.createMeeting(CreationFormPage.class);

            assertEquals("", errorFormPage.scheduleInputsErrorMessage());
            assertEquals("会議時間を入力してください。", errorFormPage.durationEmptyErrorMessageAt(index));
        }

        @Test
        @SuppressWarnings("unchecked")
        void 追加する日程が過去の場合はエラー() {

            new RegistrationRequestDateTimeFake_19841231_1023();

            MeetingCreationControllerTest.this.inputValidSampleSubject();

            ScheduleConstructInput constructInput =
                    ScheduleConstructInput.of(1984,12, 31, 10, StartMinute.JUST, Duration.ONE_HOUR);

            formPage.inputScheduleAt(0, constructInput);

            CreationFormPage errorFormPage = formPage.createMeeting(CreationFormPage.class);

            List<String> globalErrorMessages = errorFormPage.globalErrorMessages();

            assertEquals(1, globalErrorMessages.size());
            assertEquals("日程を追加する時は未来の日時を指定してください。", globalErrorMessages.get(0));
        }

    }

    void inputValidSampleSubject() {
        formPage.inputSubject("件名テスト");
    }

    void inputValidSampleSchedule() {

        ScheduleConstructInput constructInput =
                ScheduleConstructInput.of(1985,1, 1, 10, StartMinute.JUST, Duration.ONE_HOUR);

        formPage.inputScheduleAt(0, constructInput);
    }

    @AfterEach
    void tearDown() {
        if (driver == null) return;
        driver.quit();
    }
}
