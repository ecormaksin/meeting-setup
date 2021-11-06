package example.presentation.meeting;

import example.application.senario.MeetingCreationScenario;
import example.domain.model.meeting.MeetingCreationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

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
        meetingCreationRequest.addEmptySchedule();
        return "meeting/form";
    }

    @PostMapping(params={"removeSchedule"})
    String removeSchedule(@ModelAttribute("meetingCreationRequest") MeetingCreationRequest meetingCreationRequest, BindingResult bindingResult, HttpServletRequest req) {
        final Integer index = Integer.valueOf(req.getParameter("removeSchedule"));
        meetingCreationRequest.removeSchedule(index.intValue());
        return "meeting/form";
    }

    @PostMapping
    String create(@Validated @ModelAttribute("meetingCreationRequest") MeetingCreationRequest meetingCreationRequest, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) return "meeting/form";

        return "redirect:/meeting/create/completed";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "subject.value",
                "memo.value",
                "schedules.list[*"
        );
    }
}
