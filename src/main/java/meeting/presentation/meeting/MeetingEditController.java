package meeting.presentation.meeting;

import meeting.application.senario.MeetingEditScenario;
import meeting.domain.model.meeting.*;
import meeting.domain.model.meeting.organizer.ClientBrowser;
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
 * ミーティング編集画面
 */
@Controller
@RequestMapping("meeting/{meetingId}/edit")
public class MeetingEditController {
    MeetingEditScenario meetingEditScenario;

    ClientInfoGetter clientInfoGetter;

    public MeetingEditController(MeetingEditScenario meetingEditScenario,
                                 ClientInfoGetter clientInfoGetter) {
        this.meetingEditScenario = meetingEditScenario;
        this.clientInfoGetter = clientInfoGetter;
    }

    @GetMapping
    String init(Model model,
                @PathVariable("meetingId") MeetingId meetingId,
                HttpServletRequest request) {

        ClientBrowser clientBrowser = clientInfoGetter.clientBrowser(request);
        Meeting meeting = meetingEditScenario.meetingOf(meetingId);

        if (meeting.editability(clientBrowser) != MeetingEditability.編集可) {
            throw new MeetingCannotEditException();
        }

        model.addAttribute("meetingEditRequest", new MeetingEditRequest(
                meetingId,
                meeting.subject(),
                meeting.memo(),
                meeting.schedules(),
                clientBrowser
        ));

        return "meeting/edit/form";
    }

    @PostMapping(params={"addSchedule"})
    String addSchedule(@PathVariable("meetingId") MeetingId meetingId,
                       @ModelAttribute("meetingEditRequest") MeetingEditRequest meetingEditRequest) {
        meetingEditRequest.addEmptySchedule();
        return "meeting/edit/form";
    }

    @PostMapping(params={"removeSchedule"})
    String removeSchedule(@PathVariable("meetingId") MeetingId meetingId,
                          @ModelAttribute("meetingEditRequest") MeetingEditRequest meetingEditRequest,
                          HttpServletRequest request) {
        final Integer index = Integer.valueOf(request.getParameter("removeSchedule"));
        meetingEditRequest.removeSchedule(index.intValue());
        return "meeting/edit/form";
    }

    @PostMapping
    String edit(@PathVariable("meetingId") MeetingId meetingId,
                  @Validated @ModelAttribute("meetingEditRequest") MeetingEditRequest meetingEditRequest,
                  BindingResult bindingResult, RedirectAttributes attributes,
                  UriComponentsBuilder builder) {

        Meeting meeting = meetingEditScenario.meetingOf(meetingId);

        if (meeting.editability(meetingEditRequest.organizerTerminal()) != MeetingEditability.編集可) {
            throw new MeetingCannotEditException();
        }

        if (bindingResult.hasErrors()) return "meeting/edit/form";

        meetingEditScenario.edit(meeting, meetingEditRequest);

        attributes.addFlashAttribute("meetingId", meetingId.value());

        URI uri = builder.path(
                            String.format("/meeting/%s/edit/completed", meetingId.value())
                        ).build().toUri();
        return "redirect:" + uri.toString();
    }

    @GetMapping("completed")
    String completed(@ModelAttribute("meetingId") String meetingId,
                     Model model, UriComponentsBuilder builder) {

        model.addAttribute("meetingId", meetingId);

        URI uri = builder.path(String.format("/meeting/%s", meetingId)).build().toUri();
        model.addAttribute("meetingShowLocation", uri.toString());

        return "meeting/edit/completed";
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
