package meeting.presentation.meeting;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.presentation.CustomPageObject;
import org.openqa.selenium.WebDriver;

public class EditCompletedPage extends CustomPageObject {

    static final String URL_RELATIVE_PATH_REGEX = "/meeting/edit/" + MeetingNumber.REGEXP + "/completed";

    public EditCompletedPage(WebDriver driver) {
        super(driver);
    }

    protected boolean isNotTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return !url.matches(URL_RELATIVE_PATH_REGEX);
    }

    protected String pageName() {
        return "ミーティングの編集完了";
    }
}
