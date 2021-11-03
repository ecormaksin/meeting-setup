package example.presentation.meeting;

import example.application.senario.MeetingCreationScenario;
import example.domain.model.meeting.MeetingCreationRequest;
import example.domain.model.schedule.Schedules;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("meeting/create")
public class MeetingCreationController {
    MeetingCreationScenario coordinator;

    public MeetingCreationController(MeetingCreationScenario coordinator) {
        this.coordinator = coordinator;
    }

    @GetMapping
    String init(Model model) {
        MeetingCreationRequest meetingCreationRequest = MeetingCreationRequest.empty();
//        Schedules schedules = meetingCreationRequest.schedules();
        model.addAttribute("meetingCreationRequest", meetingCreationRequest);
//        model.addAttribute("scheduleList", schedules.asList());
        return "meeting/form";
    }
}
