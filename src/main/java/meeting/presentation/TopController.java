package meeting.presentation;

import meeting.presentation.organizer.OrganizerNumberProxy;
import meeting.presentation.participant.ParticipantNumberProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TopController {

    OrganizerNumberProxy organizerNumberProxy;
    ParticipantNumberProxy participantNumberProxy;

    public TopController(OrganizerNumberProxy organizerNumberProxy,
                         ParticipantNumberProxy participantNumberProxy) {

        this.organizerNumberProxy = organizerNumberProxy;
        this.participantNumberProxy = participantNumberProxy;
    }


    @GetMapping()
    String top(Model model) {
        model.addAttribute("organizerNumber", organizerNumberProxy.organizerNumber());
        model.addAttribute("participantNumber", participantNumberProxy.participantNumber());
        return "top";
    }
}
