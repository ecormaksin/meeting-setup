package meeting.presentation.meeting;

import meeting.application.senario.MeetingCreationScenario;
import meeting.domain.model.meeting.MeetingCreationRequest;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.OrganizerNumber;
import meeting.presentation.meeting.input.meeting.MeetingInputForm;
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

    public MeetingCreationController(MeetingCreationScenario meetingCreationScenario) {
        this.meetingCreationScenario = meetingCreationScenario;
    }

    @GetMapping
    String init(Model model) {
        model.addAttribute("meetingInputForm", MeetingInputForm.newForm());
        model.addAttribute("organizerNumber", OrganizerNumber.newNumber());
        return "meeting/create/form";
    }

    @PostMapping(params={"addSchedule"})
    String addSchedule(Model model, @ModelAttribute("meetingInputForm") final MeetingInputForm meetingInputForm) {
        final MeetingInputForm scheduleAdded = meetingInputForm.addEmptySchedule();
        model.addAttribute("meetingInputForm", scheduleAdded);
        return "meeting/create/form";
    }

    @PostMapping(params={"removeSchedule"})
    String removeSchedule(Model model,
                          @ModelAttribute("meetingInputForm") final MeetingInputForm meetingInputForm,
                          HttpServletRequest request) {
        final Integer index = Integer.valueOf(request.getParameter("removeSchedule"));
        final MeetingInputForm scheduleRemoved = meetingInputForm.removeSchedule(index.intValue());
        model.addAttribute("meetingInputForm", scheduleRemoved);
        return "meeting/create/form";
    }

    @PostMapping
    String create(@Validated @ModelAttribute("meetingInputForm") final MeetingInputForm meetingInputForm,
                  @CookieValue("organizerNumber") final String organizerNumberValue,
                  BindingResult bindingResult,
                  RedirectAttributes attributes,
                  UriComponentsBuilder builder) {

        if (bindingResult.hasErrors()) return "meeting/create/form";

        final MeetingNumber meetingNumber = MeetingNumber.newNumber();
        final MeetingCreationRequest meetingCreationRequest = meetingInputForm.toCreationRequest(
                meetingNumber,
                new OrganizerNumber(organizerNumberValue)
        );
        meetingCreationScenario.create(meetingCreationRequest);

        attributes.addFlashAttribute("meetingNumber", meetingNumber);

        URI uri = builder.path("/meeting/create/completed").build().toUri();
        return "redirect:" + uri.toString();
    }

    @GetMapping("completed")
    String completed(Model model,
                     @ModelAttribute("meetingNumber") MeetingNumber meetingNumber,
                     UriComponentsBuilder builder) {

        model.addAttribute("meetingNumber", meetingNumber);

        URI uri = builder.path(String.format("/meeting/%s/show", meetingNumber.value())).build().toUri();
        model.addAttribute("meetingShowLocation", uri.toString());

        return "meeting/create/completed";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "subject.value",
                "memo.value",
                "organizerNumber.value",
                "scheduleInputs.list[*");
    }
}
