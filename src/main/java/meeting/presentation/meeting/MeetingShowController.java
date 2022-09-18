package meeting.presentation.meeting;

import meeting.application.senario.MeetingShowScenario;
import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingEditability;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.presentation.organizer.OrganizerNumberProxy;
import meeting.presentation.participant.ParticipantNumberProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
                HttpServletRequest request) {

        Meeting meeting = meetingShowScenario.meetingOf(meetingNumber);

        final OrganizerNumber organizerNumber = (Objects.isNull(organizerNumberValue) || organizerNumberValue.isBlank())
                        ? organizerNumberProxy.organizerNumber()
                        : OrganizerNumber.of(organizerNumberValue);
        final MeetingEditability meetingEditability = meeting.editabilityFor(organizerNumber);

        model.addAttribute("meeting", meeting);
        model.addAttribute("meetingEditability", meetingEditability);
        model.addAttribute("participantNumber", participantNumberProxy.participantNumber());

        return "meeting/show";
    }
}
