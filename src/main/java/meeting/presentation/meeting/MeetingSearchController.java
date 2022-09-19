package meeting.presentation.meeting;

import meeting.application.senario.MeetingSearchScenario;
import meeting.domain.model.meeting.MeetingSearchRequest;
import meeting.domain.model.meeting.meeting_list.MeetingList;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.presentation.organizer.OrganizerNumberProxy;
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

    private static final String VIEW_SEARCH = "meeting/search";

    private final MeetingSearchScenario meetingSearchScenario;
    private final OrganizerNumberProxy organizerNumberProxy;

    public MeetingSearchController(MeetingSearchScenario meetingSearchScenario,
                                   OrganizerNumberProxy organizerNumberProxy) {
        this.meetingSearchScenario = meetingSearchScenario;
        this.organizerNumberProxy = organizerNumberProxy;
    }

    @GetMapping
    String init(Model model,
                @CookieValue(value = "organizerNumber", required = false) final String organizerNumberValue
    ) {
        final OrganizerNumber organizerNumber = organizerNumberProxy.organizerNumber(organizerNumberValue);

        MeetingSearchRequest meetingSearchRequest = MeetingSearchRequest.initialize(organizerNumber);
        MeetingList meetingList = meetingSearchScenario.search(meetingSearchRequest);

        model.addAttribute("meetingSearchRequest", meetingSearchRequest);
        model.addAttribute("meetingList", meetingList);

        return VIEW_SEARCH;
    }

    @GetMapping(params = {"exec"})
    String search(Model model,
                  @CookieValue("organizerNumber") final String organizerNumberValue,
                  @Validated @ModelAttribute("meetingSearchRequest") MeetingSearchRequest meetingSearchRequest,
                  BindingResult bindingResult) {

        final OrganizerNumber organizerNumber = OrganizerNumber.of(organizerNumberValue);

        if (organizerNumber.isDifferent(meetingSearchRequest.organizerNumber())) {
            throw new OrganizerNumberFalsifiedException();
        }

        model.addAttribute("meetingSearchRequest", meetingSearchRequest);

        if (bindingResult.hasErrors()) {
            model.addAttribute("meetingList", MeetingList.empty());
            return VIEW_SEARCH;
        }

        MeetingList meetingList = meetingSearchScenario.search(meetingSearchRequest);
        model.addAttribute("meetingList", meetingList);

        return VIEW_SEARCH;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "searchCondition.keyword.value",
                "searchCondition.startDate.value",
                "searchCondition.endDate.value",
                "organizerNumber.value",
                "meetingList.list[*"
        );
    }
}
