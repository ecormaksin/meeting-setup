package meeting.presentation.meeting;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.MeetingSearchScenario;
import meeting.domain.model.meeting.MeetingSearchRequest;
import meeting.domain.model.meeting.list.MeetingList;
import meeting.domain.model.meeting.search_condition.MeetingSearchConditionFake_20120123;
import meeting.domain.model.organizer.OrganizerNumberFake_1;
import meeting.presentation.TopPage;
import meeting.presentation.meeting.page_objects.SearchPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

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
}
