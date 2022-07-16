package meeting.presentation.meeting;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreationCompletedPage extends LoadableComponent<CreationCompletedPage> {

    private static final String URL_RELATIVE_PATH = "/meeting/create/completed";

    private final WebDriver driver;

    public CreationCompletedPage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get("http://localhost:9990" + URL_RELATIVE_PATH);
    }

    @Override
    protected void isLoaded() throws Error {
        final String url = driver.getCurrentUrl();
        assertTrue(url.endsWith(URL_RELATIVE_PATH), "ミーティングの作成完了ページではありません。: "  + url);
    }
}
