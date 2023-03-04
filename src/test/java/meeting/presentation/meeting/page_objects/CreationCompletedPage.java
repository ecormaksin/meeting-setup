package meeting.presentation.meeting.page_objects;

import meeting.presentation.CustomPageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreationCompletedPage extends CustomPageObject {

    static final String URL_RELATIVE_PATH = "/meeting/create/completed";

    @FindBy(css = "main > article > p:nth-child(1)")
    private WebElement processFinishedMessage;

    @FindBy(css = "main > article > p:nth-child(2)")
    private WebElement guidanceToContactToParticipants;

    @FindBy(id = "url")
    private WebElement urlToShowMeeting;

    @FindBy(css = "main > article > p:nth-child(4)")
    private WebElement guidanceToEditMeeting;

    public CreationCompletedPage(WebDriver driver) {
        super(driver);
    }

    public static boolean isTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return url.endsWith(URL_RELATIVE_PATH);
    }

    protected boolean isNotTargetPage(WebDriver driver) {
        return !isTargetPage(driver);
    }

    protected String pageName() {
        return "ミーティングの作成完了";
    }

    public String processFinishedMessage() {
        return processFinishedMessage.getText();
    }

    public String guidanceToContactToParticipants() {
        return guidanceToContactToParticipants.getText();
    }

    public String urlToShowMeeting() {
        return urlToShowMeeting.getAttribute("value");
    }

    public String guidanceToEditMeeting() {
        return guidanceToEditMeeting.getText();
    }
}