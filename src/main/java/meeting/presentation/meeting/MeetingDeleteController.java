package meeting.presentation.meeting;

import meeting.application.senario.MeetingDeleteScenario;
import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingDeleteRequest;
import meeting.domain.model.meeting.MeetingEditability;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.meeting.organizer.ClientBrowser;
import meeting.presentation.ClientInfoGetter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

/**
 * ミーティング削除画面
 */
@Controller
@RequestMapping("meeting/{meetingId}/delete")
public class MeetingDeleteController {
    MeetingDeleteScenario meetingDeleteScenario;

    ClientInfoGetter clientInfoGetter;

    public MeetingDeleteController(MeetingDeleteScenario meetingDeleteScenario,
                                   ClientInfoGetter clientInfoGetter) {
        this.meetingDeleteScenario = meetingDeleteScenario;
        this.clientInfoGetter = clientInfoGetter;
    }

    @GetMapping
    String init(Model model,
                @PathVariable("meetingId") MeetingId meetingId,
                HttpServletRequest request) {

        ClientBrowser clientBrowser = clientInfoGetter.clientBrowser(request);
        Meeting meeting = meetingDeleteScenario.meetingOf(meetingId);

        if (meeting.editability(clientBrowser) != MeetingEditability.編集可) {
            throw new MeetingCannotDeleteException();
        }

        model.addAttribute("meetingDeleteRequest", new MeetingDeleteRequest(
                meetingId,
                meeting.subject(),
                meeting.memo(),
                meeting.schedules(),
                clientBrowser
        ));

        return "meeting/delete/form";
    }

    @PostMapping
    String delete(@PathVariable("meetingId") MeetingId meetingId,
                  @ModelAttribute("meetingDeleteRequest") MeetingDeleteRequest meetingDeleteRequest,
                  BindingResult bindingResult,
                  UriComponentsBuilder builder) {

        Meeting meeting = meetingDeleteScenario.meetingOf(meetingId);

        if (meeting.editability(meetingDeleteRequest.organizerTerminal()) != MeetingEditability.編集可) {
            throw new MeetingCannotDeleteException();
        }

        if (bindingResult.hasErrors()) return "meeting/delete/form";

        meetingDeleteScenario.delete(meetingId);

        URI uri = builder.path(
                            String.format("/meeting/%s/delete/completed", meetingId.value())
                        ).build().toUri();
        return "redirect:" + uri.toString();
    }

    @GetMapping("completed")
    String completed() {
        return "meeting/delete/completed";
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
