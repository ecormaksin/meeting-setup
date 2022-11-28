package meeting.presentation.meeting;

import meeting.application.senario.MeetingSearchScenario;
import meeting.domain.model.meeting.MeetingSearchRequest;
import meeting.domain.model.meeting.list.MeetingList;
import meeting.domain.model.organizer.OrganizerNumberFake_1;
import meeting.presentation.TestWebConfig;
import meeting.presentation.TopController;
import meeting.presentation.TopPage;
import meeting.presentation.WebConfig;
import meeting.presentation.meeting.page_objects.SearchPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest({
        MeetingSearchController.class,
        TopController.class
})
@Import({WebConfig.class, TestWebConfig.class})
public class MeetingSearchControllerTest {

    @Autowired
    WebDriver driver;

    @MockBean
    MeetingSearchScenario meetingSearchScenario;

    TopPage topPage;

    @BeforeEach
    void init() {
        new OrganizerNumberFake_1();
        topPage = TopPage.to(driver);
    }

    @Test
    void test() {
        when(meetingSearchScenario.search(any(MeetingSearchRequest.class))).thenReturn(MeetingList.empty());

        SearchPage searchPage = topPage.moveToSearchPage();

        assertNotNull(searchPage);
    }
}
