package meeting.presentation.meeting;

import meeting.application.senario.MeetingShowScenario;
import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ミーティング表示画面
 */
@Controller
@RequestMapping("meeting")
public class MeetingShowController {

    MeetingShowScenario coordinator;

    public MeetingShowController(MeetingShowScenario coordinator) {
        this.coordinator = coordinator;
    }

    @GetMapping("{meetingId}")
    String show(Model model, @PathVariable("meetingId") MeetingId meetingId) {
        Meeting meeting = coordinator.meetingOf(meetingId);
        model.addAttribute("meeting", meeting);
        model.addAttribute("meetingId", meeting.meetingId().value());
        return "meeting/show";
    }
}
