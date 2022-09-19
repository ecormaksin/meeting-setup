package meeting.presentation.meeting;

import meeting.application.senario.MeetingShowScenario;
import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingEditability;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.presentation.organizer.OrganizerNumberProxy;
import meeting.presentation.participant.ParticipantNumberProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * ミーティング表示画面
 */
@Controller
@RequestMapping("meeting/{meetingNumber}/show")
public class MeetingShowController {
    private final MeetingShowScenario meetingShowScenario;
    private final OrganizerNumberProxy organizerNumberProxy;
    private final ParticipantNumberProxy participantNumberProxy;


    public MeetingShowController(MeetingShowScenario meetingShowScenario,
                                 OrganizerNumberProxy organizerNumberProxy,
                                 ParticipantNumberProxy participantNumberProxy) {
        this.meetingShowScenario = meetingShowScenario;
        this.organizerNumberProxy = organizerNumberProxy;
        this.participantNumberProxy = participantNumberProxy;
    }

    @GetMapping
    String show(Model model,
                @PathVariable("meetingNumber") final MeetingNumber meetingNumber,
                @CookieValue(value = "organizerNumber", required = false) final String organizerNumberValue,
                @CookieValue(value = "participantNumber", required = false) final String participantNumberValue,
                HttpServletRequest request) {

        final OrganizerNumber organizerNumber = organizerNumberProxy.organizerNumber(organizerNumberValue);
        final ParticipantNumber participantNumber = participantNumberProxy.participantNumber(participantNumberValue);

        Meeting meeting = meetingShowScenario.meetingOf(meetingNumber);
        final MeetingEditability meetingEditability = meeting.editabilityFor(organizerNumber);

        model.addAttribute("meeting", meeting);
        model.addAttribute("meetingEditability", meetingEditability);
        model.addAttribute("participantNumber", participantNumber);

        return "meeting/show";
    }
}
