package meeting.presentation;

import meeting.application.senario.OrganizerMeetingListGetScenario;
import meeting.domain.model.meeting.OrganizerMeetingList;
import meeting.domain.model.meeting.organizer.ClientBrowser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class TopController {
    OrganizerMeetingListGetScenario organizerMeetingListGetScenario;

    ClientInfoGetter clientInfoGetter;

    public TopController(OrganizerMeetingListGetScenario organizerMeetingListGetScenario,
                         ClientInfoGetter clientInfoGetter) {
        this.organizerMeetingListGetScenario = organizerMeetingListGetScenario;
        this.clientInfoGetter = clientInfoGetter;
    }

    @GetMapping()
    String top(Model model,
               HttpServletRequest request) {

        ClientBrowser clientBrowser = clientInfoGetter.clientBrowser(request);

        OrganizerMeetingList organizerMeetingList = organizerMeetingListGetScenario.organizerMeetingList(clientBrowser);
        model.addAttribute("organizerMeetingList", organizerMeetingList);

        return "top";
    }
}
