package meeting.presentation.meeting;

import meeting.application.senario.MeetingSearchScenario;
import meeting.domain.model.meeting.MeetingSearchRequest;
import meeting.domain.model.meeting.meeting_list.MeetingList;
import meeting.domain.model.meeting.organizer.ClientBrowser;
import meeting.presentation.ClientInfoGetter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * ミーティング検索画面
 */
@Controller
@RequestMapping("meeting/search")
public class MeetingSearchController {
    MeetingSearchScenario meetingSearchScenario;

    ClientInfoGetter clientInfoGetter;

    public MeetingSearchController(MeetingSearchScenario meetingSearchScenario,
                                   ClientInfoGetter clientInfoGetter) {
        this.meetingSearchScenario = meetingSearchScenario;
        this.clientInfoGetter = clientInfoGetter;
    }

    @GetMapping
    String init(Model model, HttpServletRequest request) {

        ClientBrowser clientBrowser = clientInfoGetter.clientBrowser(request);
        MeetingSearchRequest meetingSearchRequest = MeetingSearchRequest.initial(clientBrowser);
        MeetingList meetingList = meetingSearchScenario.search(meetingSearchRequest);

        model.addAttribute("meetingSearchRequest", MeetingSearchRequest.initial(clientBrowser));
        model.addAttribute("meetingList", meetingList);

        return "meeting/search";
    }

    @GetMapping(params = {"exec"})
    String search(Model model,
                  HttpServletRequest request,
                  @Validated @ModelAttribute("meetingSearchRequest") MeetingSearchRequest meetingSearchRequest,
                  BindingResult bindingResult) {

        ClientBrowser clientBrowser = clientInfoGetter.clientBrowser(request);
        if (!clientBrowser.equals(meetingSearchRequest.organizerTerminal())) {
            throw new ClientBrowserFalsifiedException();
        }

        model.addAttribute("meetingSearchRequest", meetingSearchRequest);

        if (bindingResult.hasErrors()) {
            model.addAttribute("meetingList", MeetingList.empty());
            return "meeting/search";
        }

        MeetingList meetingList = meetingSearchScenario.search(meetingSearchRequest);
        model.addAttribute("meetingList", meetingList);

        return "meeting/search";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "searchCondition.keyword.value",
                "searchCondition.startDate.value",
                "searchCondition.endDate.value",
                "organizerTerminal.ipAddress.value",
                "organizerTerminal.osFamily.value",
                "organizerTerminal.userAgentFamily.value"
        );
    }
}
