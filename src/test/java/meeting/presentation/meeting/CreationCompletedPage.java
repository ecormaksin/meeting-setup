package meeting.presentation.meeting;

import meeting.presentation.CustomPageObject;
import org.openqa.selenium.WebDriver;

public class CreationCompletedPage extends CustomPageObject {

    private static final String URL_RELATIVE_PATH = "/meeting/create/completed";

    public CreationCompletedPage(final WebDriver driver) {
        super(driver);
    }

    protected boolean isNotTargetPage(WebDriver driver) {
        final String url = driver.getCurrentUrl();
        return !url.endsWith(URL_RELATIVE_PATH);
    }

    protected String pageName() {
        return "ミーティングの作成完了";
    }
}
