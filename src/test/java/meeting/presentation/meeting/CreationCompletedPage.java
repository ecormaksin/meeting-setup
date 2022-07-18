package meeting.presentation.meeting;

import meeting.presentation.CustomPageObject;
import org.openqa.selenium.WebDriver;

public class CreationCompletedPage extends CustomPageObject {

    private static final String URL_RELATIVE_PATH = "/meeting/create/completed";

    public CreationCompletedPage(final WebDriver driver) {
        super(driver);

        final String url = driver.getCurrentUrl();
        if (!url.endsWith(URL_RELATIVE_PATH)) {
            throw new IllegalStateException("ミーティングの作成完了ページではありません。: "  + url);
        }
    }
}
