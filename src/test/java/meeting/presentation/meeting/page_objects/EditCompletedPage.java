package meeting.presentation.meeting.page_objects;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.presentation.CustomPageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditCompletedPage extends CustomPageObject {

    static final String URL_RELATIVE_PATH_REGEX = ".*/meeting/" + MeetingNumber.REGEXP + "/edit/completed";

    @FindBy(css = "main > article > p:nth-child(1)")
    private WebElement processFinishedMessage;

    @FindBy(css = "main > article > p:nth-child(2)")
    private WebElement guidanceToContactToParticipants;

    @FindBy(id = "url")
    private WebElement urlToShowMeeting;

    @FindBy(css = "main > article > p:nth-child(4)")
    private WebElement guidanceToEditMeeting;

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
