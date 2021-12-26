package meeting.presentation.meeting;

import meeting.application.senario.MeetingCreationScenario;
import meeting.domain.model.meeting.MeetingCreationRequest;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.meeting.organizer.OrganizerTerminal;
import meeting.presentation.ClientInfoGetter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

/**
 * ミーティング作成画面
 */
@Controller
@RequestMapping("meeting/create")
public class MeetingCreationController {
    MeetingCreationScenario meetingCreationScenario;

    ClientInfoGetter clientInfoGetter;

    public MeetingCreationController(MeetingCreationScenario meetingCreationScenario,
                                     ClientInfoGetter clientInfoGetter) {
        this.meetingCreationScenario = meetingCreationScenario;
        this.clientInfoGetter = clientInfoGetter;
    }

    @GetMapping
    String init(Model model, HttpServletRequest req) {
        OrganizerTerminal organizerTerminal = clientInfoGetter.organizerTerminal(req);
        model.addAttribute("meetingCreationRequest", MeetingCreationRequest.empty(organizerTerminal));
        return "meeting/form";
    }

    @PostMapping(params={"addSchedule"})
    String addSchedule(@ModelAttribute("meetingCreationRequest") MeetingCreationRequest meetingCreationRequest,
                       BindingResult bindingResult) {
        meetingCreationRequest.addEmptySchedule();
        return "meeting/form";
    }

    @PostMapping(params={"removeSchedule"})
    String removeSchedule(@ModelAttribute("meetingCreationRequest") MeetingCreationRequest meetingCreationRequest,
                          BindingResult bindingResult,
                          HttpServletRequest req) {
        final Integer index = Integer.valueOf(req.getParameter("removeSchedule"));
        meetingCreationRequest.removeSchedule(index.intValue());
        return "meeting/form";
    }

    @PostMapping
    String create(@Validated @ModelAttribute("meetingCreationRequest") MeetingCreationRequest meetingCreationRequest,
                  BindingResult bindingResult, RedirectAttributes attributes,
                  UriComponentsBuilder builder) {
        if (bindingResult.hasErrors()) return "meeting/form";

        MeetingId meetingId = meetingCreationScenario.create(meetingCreationRequest);

        attributes.addFlashAttribute("meetingId", meetingId.value());

        URI redirectLocation = builder.path("/meeting/create/completed").build().toUri();
        return "redirect:" + redirectLocation.toString();
    }

    @GetMapping("completed")
    String completed(@ModelAttribute("meetingId") String meetingId, Model model, UriComponentsBuilder builder) {
        URI attendanceReplyLocation = builder.path("/meeting/" + meetingId).build().toUri();
        model.addAttribute("attendanceReplyLocation", attendanceReplyLocation.toString());
        return "meeting/completed";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "subject.value",
                "memo.value",
                "schedules.list[*",
                "organizerTerminal.ipAddress.value",
                "organizerTerminal.osFamily.value",
                "organizerTerminal.userAgentFamily.value"
        );
    }
}
