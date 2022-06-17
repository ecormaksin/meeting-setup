package meeting.presentation.meeting;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.LoadableComponent;

import static org.junit.jupiter.api.Assertions.assertTrue;
public class CreationPage extends LoadableComponent<CreationPage> {

    private final WebDriver driver;

    public CreationPage(final WebDriver driver) {
        this.driver = driver;
    }

    @Override
    protected void load() {
        driver.get("http://localhost:9990/meeting/create");
    }

    @Override
    protected void isLoaded() throws Error {
        final String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("/create/form"), "ミーティング作成ページではありません。: "  + url);
    }
}
