package meeting.presentation.meeting;

import meeting.application.senario.MeetingSearchScenario;
import meeting.domain.model.meeting.MeetingSearchRequest;
import meeting.domain.model.meeting.OrganizerNumber;
import meeting.domain.model.meeting.meeting_list.MeetingList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * ミーティング検索画面
 */
@Controller
@RequestMapping("meeting/search")
public class MeetingSearchController {
    MeetingSearchScenario meetingSearchScenario;

    public MeetingSearchController(MeetingSearchScenario meetingSearchScenario) {
        this.meetingSearchScenario = meetingSearchScenario;
    }

    @GetMapping
    String init(Model model,
                @RequestHeader("organizerNumber") final OrganizerNumber organizerNumber) {

        MeetingSearchRequest meetingSearchRequest = MeetingSearchRequest.initialize(organizerNumber);
        MeetingList meetingList = meetingSearchScenario.search(meetingSearchRequest);

        model.addAttribute("meetingSearchRequest", meetingSearchRequest);
        model.addAttribute("meetingList", meetingList);

        return "meeting/search";
    }

    @GetMapping(params = {"exec"})
    String search(Model model,
                  @RequestHeader("organizerNumber") final OrganizerNumber organizerNumber,
                  @Validated @ModelAttribute("meetingSearchRequest") MeetingSearchRequest meetingSearchRequest,
                  BindingResult bindingResult) {

        if (organizerNumber.isDifferent(meetingSearchRequest.organizerNumber())) {
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
                "organizerNumber.value"
        );
    }
}
