package meeting.presentation.meeting;

import meeting.application.senario.MeetingShowScenario;
import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingEditability;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.organizer.OrganizerNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * ミーティング表示画面
 */
@Controller
@RequestMapping("meeting/{meetingNumber}/show")
public class MeetingShowController {
    MeetingShowScenario meetingShowScenario;

    public MeetingShowController(MeetingShowScenario meetingShowScenario) {
        this.meetingShowScenario = meetingShowScenario;
    }

    @GetMapping
    String show(Model model,
                @PathVariable("meetingNumber") final MeetingNumber meetingNumber,
                @CookieValue("organizerNumber") final String organizerNumberValue,
                HttpServletRequest request) {

        Meeting meeting = meetingShowScenario.meetingOf(meetingNumber);

        final OrganizerNumber organizerNumber = OrganizerNumber.of(organizerNumberValue);
        boolean isEditable = (meeting.editability(organizerNumber) == MeetingEditability.編集可);

        model.addAttribute("meeting", meeting);
        model.addAttribute("isEditable", isEditable);

        return "meeting/show";
    }
}
