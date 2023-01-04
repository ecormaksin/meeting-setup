package meeting.presentation.meeting.page_objects;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.presentation.CustomPageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeleteCompletedPage extends CustomPageObject {

    static final String URL_RELATIVE_PATH_REGEX = ".*/meeting/" + MeetingNumber.REGEX_PATTERN + "/delete/completed";

    @FindBy(css = "main > article > p:nth-child(1)")
    private WebElement processFinishedMessage;

    public DeleteCompletedPage(WebDriver driver) {
        super(driver);
    }

    public static boolean isTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return url.matches(URL_RELATIVE_PATH_REGEX);
    }

    @Override
    protected boolean isNotTargetPage(WebDriver driver) {
        return !isTargetPage(driver);
    }

    @Override
    protected String pageName() {
        return "ミーティングの削除完了";
    }

    public String processFinishedMessage() {
        return processFinishedMessage.getText();
    }
}
