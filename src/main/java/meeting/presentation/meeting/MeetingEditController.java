package meeting.presentation.meeting;

import meeting.application.senario.MeetingEditScenario;
import meeting.domain.model.meeting.*;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.SchedulesInvalidException;
import meeting.presentation.meeting.input.meeting.MeetingInputForm;
import meeting.presentation.meeting.input.schedule.ScheduleInputs;
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
 * ミーティング編集画面
 */
@Controller
@RequestMapping("meeting/{meetingNumber}/edit")
public class MeetingEditController {

    static final String VIEW_PATH_FORM = "meeting/edit/form";

    MeetingEditScenario meetingEditScenario;

    public MeetingEditController(MeetingEditScenario meetingEditScenario) {
        this.meetingEditScenario = meetingEditScenario;
    }

    private void validateEditability(Meeting meeting, OrganizerNumber organizerNumber) {
        MeetingEditability meetingEditability = meeting.editabilityFor(organizerNumber);
        if (meetingEditability == MeetingEditability.編集可能) return;
        throw new RuntimeException(meetingEditability.statusMessage());
    }

    @GetMapping
    String init(Model model,
                @CookieValue("organizerNumber") String organizerNumberValue,
                @PathVariable("meetingNumber") MeetingNumber meetingNumber,
                HttpServletRequest request) {

        Meeting meeting = meetingEditScenario.meetingOf(meetingNumber);
        OrganizerNumber organizerNumber = OrganizerNumber.of(organizerNumberValue);

        validateEditability(meeting, organizerNumber);

        Schedules schedules = meeting.schedules();
        model.addAttribute("meetingInputForm", new MeetingInputForm(
                meeting.subject(),
                meeting.memo(),
                ScheduleInputs.of(schedules)
        ));

        return VIEW_PATH_FORM;
    }

    @PostMapping(params={"addSchedule"})
    String addSchedule(Model model,
                       @PathVariable("meetingNumber") MeetingNumber meetingNumber,
                       @ModelAttribute("meetingInputForm") MeetingInputForm meetingInputForm) {

        MeetingInputForm scheduleAdded = meetingInputForm.addEmptySchedule();
        model.addAttribute("meetingInputForm", scheduleAdded);
        return VIEW_PATH_FORM;
    }

    @PostMapping(params={"removeSchedule"})
    String removeSchedule(Model model,
                          @PathVariable("meetingNumber") MeetingNumber meetingNumber,
                          @ModelAttribute("meetingInputForm") MeetingInputForm meetingInputForm,
                          HttpServletRequest request) {

        Integer index = Integer.valueOf(request.getParameter("removeSchedule"));
        MeetingInputForm scheduleRemoved = meetingInputForm.removeSchedule(index.intValue());
        model.addAttribute("meetingInputForm", scheduleRemoved);
        return VIEW_PATH_FORM;
    }

    @PostMapping
    String edit(@PathVariable("meetingNumber") MeetingNumber meetingNumber,
                @Validated @ModelAttribute("meetingInputForm") MeetingInputForm meetingInputForm,
                BindingResult bindingResult,
                @CookieValue("organizerNumber") String organizerNumberValue,
                RedirectAttributes attributes,
                UriComponentsBuilder builder) {

        Meeting meeting = meetingEditScenario.meetingOf(meetingNumber);
        OrganizerNumber organizerNumber = OrganizerNumber.of(organizerNumberValue);

        validateEditability(meeting, organizerNumber);

        if (bindingResult.hasErrors()) return VIEW_PATH_FORM;

        try {

            MeetingRegistrationRequest registrationRequest = meetingInputForm.toRegistrationRequest(meetingNumber, organizerNumber);
            meetingEditScenario.edit(meeting, registrationRequest);

        } catch (SchedulesInvalidException e) {

            bindingResult.rejectValue("scheduleInputs", null, e.getMessage());
            return VIEW_PATH_FORM;

        } catch (MeetingRegistrationRequestInvalidException e) {

            ObjectError error = new ObjectError("globalError", e.getMessage());
            bindingResult.addError(error);
            return VIEW_PATH_FORM;

        }

        attributes.addFlashAttribute("meetingNumber", meetingNumber);

        URI uri = builder.path(
                            String.format("/meeting/%s/edit/completed", meetingNumber.value())
                        ).build().toUri();
        return "redirect:" + uri.toString();
    }

    @GetMapping("completed")
    String completed(@ModelAttribute("meetingNumber") MeetingNumber meetingNumber,
                     Model model, UriComponentsBuilder builder) {

        URI uri = builder.path(String.format("/meeting/%s/show", meetingNumber.value())).build().toUri();
        model.addAttribute("meetingShowLocation", uri.toString());

        return "meeting/edit/completed";
    }

    @PostMapping(params={"delete"})
    String delete(@PathVariable("meetingNumber") MeetingNumber meetingNumber,
                  @CookieValue("organizerNumber") String organizerNumberValue,
                  UriComponentsBuilder builder) {

        Meeting meeting = meetingEditScenario.meetingOf(meetingNumber);
        OrganizerNumber organizerNumber = OrganizerNumber.of(organizerNumberValue);

        validateEditability(meeting, organizerNumber);

        meetingEditScenario.delete(meetingNumber);

        URI uri = builder.path(
                String.format("/meeting/%s/delete/completed", meetingNumber.value())
        ).build().toUri();
        return "redirect:" + uri.toString();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "meetingNumber.value",
                "subject.value",
                "memo.value",
                "organizerNumber.value",
                "scheduleInputs.list[*");
    }
}
