package meeting.presentation.meeting;

import meeting.application.senario.MeetingEditScenario;
import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingEditRequest;
import meeting.domain.model.meeting.MeetingId;
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
 * ミーティング編集画面
 */
@Controller
@RequestMapping("meeting/{meetingId}/edit")
public class MeetingEditController {
    MeetingEditScenario meetingEditScenario;

    public MeetingEditController(MeetingEditScenario meetingEditScenario) {
        this.meetingEditScenario = meetingEditScenario;
    }

    @GetMapping
    String init(Model model, @PathVariable("meetingId") MeetingId meetingId) {
        Meeting meeting = meetingEditScenario.meetingOf(meetingId);

        model.addAttribute("meetingEditRequest", new MeetingEditRequest(
                meetingId,
                meeting.subject(),
                meeting.memo(),
                meeting.schedules(),
                meeting.organizerTerminal()
        ));
        model.addAttribute("meetingId", meetingId.value());

        return "meeting/edit";
    }

    @PostMapping(params={"addSchedule"})
    String addSchedule(@PathVariable("meetingId") MeetingId meetingId,
                       @ModelAttribute("meetingEditRequest") MeetingEditRequest meetingEditRequest) {
        meetingEditRequest.addEmptySchedule();
        return "meeting/edit";
    }

    @PostMapping(params={"removeSchedule"})
    String removeSchedule(@PathVariable("meetingId") MeetingId meetingId,
                          @ModelAttribute("meetingEditRequest") MeetingEditRequest meetingEditRequest,
                          HttpServletRequest request) {
        final Integer index = Integer.valueOf(request.getParameter("removeSchedule"));
        meetingEditRequest.removeSchedule(index.intValue());
        return "meeting/edit";
    }

    @PostMapping
    String edit(@PathVariable("meetingId") MeetingId meetingId,
                  @Validated @ModelAttribute("meetingEditRequest") MeetingEditRequest meetingEditRequest,
                  BindingResult bindingResult, RedirectAttributes attributes,
                  UriComponentsBuilder builder) {
        if (bindingResult.hasErrors()) return "meeting/edit";

        meetingEditScenario.edit(meetingEditRequest);

        attributes.addFlashAttribute("meetingId", meetingId.value());

        URI uri = builder.path(String.format("/meeting/%s/edit/completed", meetingId.value())).build().toUri();
        return "redirect:" + uri.toString();
    }

    @GetMapping("completed")
    String completed(@ModelAttribute("meetingId") String meetingId, Model model, UriComponentsBuilder builder) {
        URI uri = builder.path(String.format("/meeting/%s", meetingId)).build().toUri();
        model.addAttribute("meetingShowLocation", uri.toString());
        return "meeting/completed";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "meetingId.value",
                "subject.value",
                "memo.value",
                "schedules.list[*",
                "organizerTerminal.ipAddress.value",
                "organizerTerminal.osFamily.value",
                "organizerTerminal.userAgentFamily.value"
        );
    }
}
