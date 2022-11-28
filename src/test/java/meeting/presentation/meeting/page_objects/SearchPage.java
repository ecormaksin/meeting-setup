package meeting.presentation.meeting.page_objects;

import meeting.presentation.CustomPageObject;
import org.openqa.selenium.WebDriver;

public class SearchPage extends CustomPageObject {

    static final String URL_RELATIVE_PATH = "/meeting/search";

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    protected boolean isNotTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return !url.endsWith(URL_RELATIVE_PATH);
    }

    protected String pageName() {
        return "ミーティングの検索";
    }
}
