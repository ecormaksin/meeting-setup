package meeting.presentation.meeting;

import meeting.application.senario.MeetingCreationScenario;
import meeting.domain.model.meeting.MeetingCreationRequest;
import meeting.domain.model.meeting.MeetingId;
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
    String init(Model model, HttpServletRequest request) {

        ClientBrowser clientBrowser = clientInfoGetter.clientBrowser(request);
        model.addAttribute("meetingCreationRequest", MeetingCreationRequest.empty(clientBrowser));

        return "meeting/create/form";
    }

    @PostMapping(params={"addSchedule"})
    String addSchedule(@ModelAttribute("meetingCreationRequest") MeetingCreationRequest meetingCreationRequest) {
        meetingCreationRequest.addEmptySchedule();
        return "meeting/create/form";
    }

    @PostMapping(params={"removeSchedule"})
    String removeSchedule(@ModelAttribute("meetingCreationRequest") MeetingCreationRequest meetingCreationRequest,
                          HttpServletRequest request) {
        final Integer index = Integer.valueOf(request.getParameter("removeSchedule"));
        meetingCreationRequest.removeSchedule(index.intValue());
        return "meeting/create/form";
    }

    @PostMapping
    String create(HttpServletRequest request,
                  @Validated @ModelAttribute("meetingCreationRequest") MeetingCreationRequest meetingCreationRequest,
                  BindingResult bindingResult,
                  RedirectAttributes attributes,
                  UriComponentsBuilder builder) {

        ClientBrowser clientBrowser = clientInfoGetter.clientBrowser(request);
        if (!clientBrowser.equals(meetingCreationRequest.organizerTerminal())) {
            throw new ClientBrowserFalsifiedException();
        }

        if (bindingResult.hasErrors()) return "meeting/create/form";

        MeetingId meetingId = meetingCreationScenario.create(meetingCreationRequest);

        attributes.addFlashAttribute("meetingId", meetingId.value());

        URI uri = builder.path("/meeting/create/completed").build().toUri();
        return "redirect:" + uri.toString();
    }

    @GetMapping("completed")
    String completed(@ModelAttribute("meetingId") String meetingId, Model model, UriComponentsBuilder builder) {

        model.addAttribute("meetingId", meetingId);

        URI uri = builder.path(String.format("/meeting/%s/show", meetingId)).build().toUri();
        model.addAttribute("meetingShowLocation", uri.toString());

        return "meeting/create/completed";
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
