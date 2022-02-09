package meeting.presentation.meeting;

import meeting.application.senario.MeetingShowScenario;
import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingEditability;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.meeting.organizer.ClientBrowser;
import meeting.presentation.ClientInfoGetter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * ミーティング検索画面
 */
@Controller
@RequestMapping("meeting/search")
public class MeetingSearchController {
    MeetingShowScenario meetingShowScenario;

    ClientInfoGetter clientInfoGetter;

    public MeetingSearchController(MeetingShowScenario meetingShowScenario,
                                   ClientInfoGetter clientInfoGetter) {
        this.meetingShowScenario = meetingShowScenario;
        this.clientInfoGetter = clientInfoGetter;
    }

    @GetMapping("{meetingId}")
    String show(Model model, @PathVariable("meetingId") MeetingId meetingId, HttpServletRequest req) {
        ClientBrowser clientBrowser = clientInfoGetter.clientBrowser(req);
        Meeting meeting = meetingShowScenario.meetingOf(meetingId);

        boolean isEditable = (meeting.editability(clientBrowser) == MeetingEditability.編集可);

        model.addAttribute("meeting", meeting);
        model.addAttribute("isEditable", isEditable);

        return "meeting/show";
    }
}
