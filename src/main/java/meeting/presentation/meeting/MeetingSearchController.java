package meeting.presentation.meeting;

import meeting.application.senario.MeetingSearchScenario;
import meeting.domain.model.meeting.MeetingSearchRequest;
import meeting.domain.model.meeting.list.MeetingList;
import meeting.domain.model.meeting.search_condition.MeetingSearchCondition;
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

    static final String VIEW_SEARCH = "meeting/search";

    MeetingSearchScenario meetingSearchScenario;
    OrganizerNumberProxy organizerNumberProxy;

    public MeetingSearchController(MeetingSearchScenario meetingSearchScenario,
                                   OrganizerNumberProxy organizerNumberProxy) {
        this.meetingSearchScenario = meetingSearchScenario;
        this.organizerNumberProxy = organizerNumberProxy;
    }

    @GetMapping
    String init(Model model,
                @CookieValue(value = "organizerNumber", required = false) String organizerNumberValue
    ) {
        OrganizerNumber organizerNumber = organizerNumberProxy.organizerNumber(organizerNumberValue);

        MeetingSearchRequest meetingSearchRequest = MeetingSearchRequest.initialized(organizerNumber);
        MeetingList meetingList = meetingSearchScenario.search(meetingSearchRequest);

        model.addAttribute("searchCondition", meetingSearchRequest.searchCondition());
        model.addAttribute("meetingList", meetingList);
        model.addAttribute("organizerNumber", organizerNumber);

        return VIEW_SEARCH;
    }

    @GetMapping(params = {"search"})
    String search(Model model,
                  @Validated @ModelAttribute("searchCondition") MeetingSearchCondition searchCondition,
                  BindingResult bindingResult,
                  @CookieValue("organizerNumber") String organizerNumberValue
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("meetingList", MeetingList.empty());
            return VIEW_SEARCH;
        }

        OrganizerNumber organizerNumber = OrganizerNumber.of(organizerNumberValue);
        MeetingSearchRequest meetingSearchRequest = MeetingSearchRequest.of(organizerNumber, searchCondition);
        MeetingList meetingList = meetingSearchScenario.search(meetingSearchRequest);
        model.addAttribute("meetingList", meetingList);

        return VIEW_SEARCH;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "keyword.value",
                "startDate.value",
                "endDate.value",
                "limitCount.value",
                "organizerNumber.value",
                "meetingList.list[*"
        );
    }
}
