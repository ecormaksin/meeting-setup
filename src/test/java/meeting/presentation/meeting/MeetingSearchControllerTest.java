package meeting.presentation.meeting;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.MeetingSearchScenario;
import meeting.domain.model.meeting.MeetingNumberTestHelper;
import meeting.domain.model.meeting.MeetingSearchRequest;
import meeting.domain.model.meeting.Subject;
import meeting.domain.model.meeting.list.MeetingList;
import meeting.domain.model.meeting.list.MeetingListItem;
import meeting.domain.model.meeting.search_condition.MeetingSearchConditionFake_20120123;
import meeting.domain.model.organizer.OrganizerNumberFake_1;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.*;
import meeting.presentation.TopPage;
import meeting.presentation.meeting.page_objects.SearchPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MeetingSearchControllerTest extends MeetingIntegrationTest {

    Logger logger = Logger.getLogger(MeetingSearchControllerTest.class.getName());

    @BeforeEach
    void init() {
        new OrganizerNumberFake_1();
        new MeetingSearchConditionFake_20120123();
    }

    @Nested
    @DisplayName("初期表示の場合")
    class CaseInit extends MeetingIntegrationTest {

        @Autowired
        WebDriver driver;

        @MockBean
        MeetingSearchScenario meetingSearchScenario;

        TopPage topPage;
        SearchPage searchPage;

        @BeforeEach
        void init() {
            topPage = TopPage.to(driver);

            when(meetingSearchScenario.search(any(MeetingSearchRequest.class))).thenReturn(MeetingList.empty());
            searchPage = topPage.moveToSearchPage();
        }

        @Test
        void ミーティングの検索ページが表示される() {
            assertNotNull(searchPage);
        }

        @Test
        void キーワードが未指定である() {
            assertEquals("", searchPage.keywordValue());
        }

        @Test
        void 開始日に初期値が設定されている() {
            assertEquals("2012-01-23", searchPage.startDateValue());
        }

        @Test
        void 終了日に初期値が設定されている() {
            assertEquals("2012-01-30", searchPage.endDateValue());
        }

        @Test
        void 該当するミーティングがない() {
            assertEquals("条件に該当するミーティングが見つかりませんでした。", searchPage.noMatchFoundMessage());
        }

        @AfterEach
        void tearDown() {
            if (driver == null) return;
            driver.quit();
        }
    }

    @Nested
    @DisplayName("条件に該当するミーティング一覧が表示される場合")
    class CaseMatchedMeetingListDisplayed extends MeetingIntegrationTest {

        @Autowired
        WebDriver driver;

        @MockBean
        MeetingSearchScenario meetingSearchScenario;

        TopPage topPage;
        SearchPage searchPage;

        MeetingList meetingList;

        @BeforeEach
        void init() {
            topPage = TopPage.to(driver);

            when(meetingSearchScenario.search(any(MeetingSearchRequest.class))).thenReturn(MeetingList.empty());
            searchPage = topPage.moveToSearchPage();

            meetingList = MeetingList.of(Arrays.asList(
                    MeetingListItem.of(
                            MeetingNumberTestHelper.fromInt(1),
                            Subject.of("件名テスト1"),
                            Schedules.of(Arrays.asList(
                                    Schedule.of(ScheduleNumber.of(1),
                                            ScheduleConstruct.of(MeetingDate.of(1923, 4, 1), StartHour.of(9), StartMinute.JUST, Duration.OF_1_HOUR)),
                                    Schedule.of(ScheduleNumber.of(2),
                                            ScheduleConstruct.of(MeetingDate.of(1923, 4, 2), StartHour.of(10), StartMinute.JUST, Duration.OF_1_HOUR))
                            ))
                    ),
                    MeetingListItem.of(
                            MeetingNumberTestHelper.fromInt(2),
                            Subject.of("件名テスト2"),
                            Schedules.of(Arrays.asList(
                                    Schedule.of(ScheduleNumber.of(3),
                                            ScheduleConstruct.of(MeetingDate.of(1923, 4, 3), StartHour.of(9), StartMinute.JUST, Duration.OF_1_HOUR)),
                                    Schedule.of(ScheduleNumber.of(4),
                                            ScheduleConstruct.of(MeetingDate.of(1923, 4, 4), StartHour.of(10), StartMinute.JUST, Duration.OF_1_HOUR))
                            ))
                    )
            ));

            when(meetingSearchScenario.search(any(MeetingSearchRequest.class))).thenReturn(meetingList);
            searchPage.inputKeyword("テスト");
            searchPage.inputStartDate("1923-04-01");
            searchPage.inputEndDate("1923-04-10");
            searchPage = searchPage.search();
        }

        @Test
        void キーワードが検索実行前に指定したものであること() {
            assertEquals("テスト", searchPage.keywordValue());
        }

        @Test
        void 開始日が検索実行前に指定したものであること() {
            assertEquals("1923-04-01", searchPage.startDateValue());
        }

        @Test
        void 終了日が検索実行前に指定したものであること() {
            assertEquals("1923-04-10", searchPage.endDateValue());
        }

        @Test
        void 検索結果が2件であること() {
            assertEquals(2, searchPage.numberOfMeetingListItems());
        }

        @Test
        void _1件目の件名が期待値通りであること() {
            assertEquals("件名テスト1", searchPage.subjectValue(1));
        }

        @Test
        void _1件目の日程が期待値通りであること() {
            assertEquals(Arrays.asList("04/01(日) 09:00 ～ 10:00", "04/02(月) 10:00 ～ 11:00"), searchPage.schedulesValue(1));
        }

        @Test
        void _2件目の件名が期待値通りであること() {
            assertEquals("件名テスト2", searchPage.subjectValue(2));
        }

        @Test
        void _2件目の日程が期待値通りであること() {
            assertEquals(Arrays.asList("04/03(火) 09:00 ～ 10:00", "04/04(水) 10:00 ～ 11:00"), searchPage.schedulesValue(2));
        }
    }
}
