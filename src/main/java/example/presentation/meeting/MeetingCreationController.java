package example.presentation.meeting;

import example.application.senario.MeetingCreationScenario;
import example.domain.model.meeting.MeetingCreationRequest;
import example.domain.model.schedule.Schedule;
import example.domain.model.schedule.Schedules;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("meeting/create")
public class MeetingCreationController {
    MeetingCreationScenario coordinator;

    public MeetingCreationController(MeetingCreationScenario coordinator) {
        this.coordinator = coordinator;
    }

    @GetMapping
    String init(Model model) {
        model.addAttribute("meetingCreationRequest", MeetingCreationRequest.empty());
        return "meeting/form";
    }

    @PostMapping(params={"addSchedule"})
    String addSchedule(@ModelAttribute("meetingCreationRequest") MeetingCreationRequest meetingCreationRequest, BindingResult bindingResult) {
        Schedules schedules = meetingCreationRequest.schedules();
        List<Schedule> scheduleList = schedules.asList();
        scheduleList.add(new Schedule());
        return "meeting/form";
    }
}
