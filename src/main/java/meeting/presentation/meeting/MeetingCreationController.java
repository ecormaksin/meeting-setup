package meeting.presentation.meeting;

import meeting.application.senario.MeetingCreationScenario;
import meeting.domain.model.meeting.MeetingCreationRequest;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.schedule.SchedulesInvalidException;
import meeting.presentation.meeting.input.meeting.MeetingInputForm;
import meeting.presentation.meeting.input.schedule.ScheduleInputCannotAddException;
import meeting.presentation.organizer.OrganizerNumberProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

    private static final String VIEW_PATH_FORM = "meeting/create/form";

    private final MeetingCreationScenario meetingCreationScenario;
    private final OrganizerNumberProxy organizerNumberProxy;

    public MeetingCreationController(MeetingCreationScenario meetingCreationScenario,
                                     OrganizerNumberProxy organizerNumberProxy) {
        this.meetingCreationScenario = meetingCreationScenario;
        this.organizerNumberProxy = organizerNumberProxy;
    }

    @GetMapping
    String init(Model model,
                @CookieValue(value = "organizerNumber", required = false) final String organizerNumberValue
    ) {
        final OrganizerNumber organizerNumber = organizerNumberProxy.organizerNumber(organizerNumberValue);

        model.addAttribute("meetingInputForm", MeetingInputForm.newForm());
        model.addAttribute("organizerNumber", organizerNumber);
        return VIEW_PATH_FORM;
    }

    @PostMapping(params={"addSchedule"})
    String addSchedule(Model model,
                       @ModelAttribute("meetingInputForm") final MeetingInputForm meetingInputForm,
                       BindingResult bindingResult) {
        try {
            final MeetingInputForm scheduleAdded = meetingInputForm.addEmptySchedule();
            model.addAttribute("meetingInputForm", scheduleAdded);
        } catch (ScheduleInputCannotAddException e) {
            ObjectError error = new ObjectError("globalError", e.getMessage());
            bindingResult.addError(error);
            model.addAttribute("meetingInputForm", meetingInputForm);
        }
        return VIEW_PATH_FORM;
    }

    @PostMapping(params={"removeSchedule"})
    String removeSchedule(Model model,
                          @ModelAttribute("meetingInputForm") final MeetingInputForm meetingInputForm,
                          HttpServletRequest request) {
        final Integer index = Integer.valueOf(request.getParameter("removeSchedule"));
        final MeetingInputForm scheduleRemoved = meetingInputForm.removeSchedule(index.intValue());
        model.addAttribute("meetingInputForm", scheduleRemoved);
        return VIEW_PATH_FORM;
    }

    @PostMapping
    String create(@Validated @ModelAttribute("meetingInputForm") final MeetingInputForm meetingInputForm,
                  BindingResult bindingResult,
                  @CookieValue("organizerNumber") final String organizerNumberValue,
                  RedirectAttributes attributes,
                  UriComponentsBuilder builder) {

        if (bindingResult.hasErrors()) return VIEW_PATH_FORM;

        final MeetingNumber meetingNumber = MeetingNumber.newNumber();
        final OrganizerNumber organizerNumber = OrganizerNumber.of(organizerNumberValue);

        try {
            final MeetingCreationRequest meetingCreationRequest = meetingInputForm.toCreationRequest(meetingNumber, organizerNumber);
            meetingCreationScenario.create(meetingCreationRequest);
        } catch (SchedulesInvalidException e) {
            bindingResult.rejectValue("scheduleInputs", null, e.getMessage());
            return VIEW_PATH_FORM;
        }

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
                "scheduleInputs.list[*",
                "organizerNumber.value"
        );
    }
}
