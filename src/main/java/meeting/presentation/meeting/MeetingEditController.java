package meeting.presentation.meeting;

import meeting.application.senario.MeetingEditScenario;
import meeting.domain.model.meeting.*;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.presentation.meeting.input.meeting.MeetingInputForm;
import meeting.presentation.meeting.input.schedule.ScheduleInputs;
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
@RequestMapping("meeting/{meetingNumber}/edit")
public class MeetingEditController {

    private static final String VIEW_PATH_FORM = "meeting/edit/form";

    private MeetingEditScenario meetingEditScenario;

    public MeetingEditController(MeetingEditScenario meetingEditScenario) {
        this.meetingEditScenario = meetingEditScenario;
    }

    @GetMapping
    String init(Model model,
                @CookieValue("organizerNumber") final String organizerNumberValue,
                @PathVariable("meetingNumber") final MeetingNumber meetingNumber,
                HttpServletRequest request) {

        Meeting meeting = meetingEditScenario.meetingOf(meetingNumber);
        final OrganizerNumber organizerNumber = OrganizerNumber.of(organizerNumberValue);

        if (meeting.editabilityFor(organizerNumber) != MeetingEditability.編集可能) {
            throw new MeetingCannotEditException();
        }

        final Schedules schedules = meeting.schedules();

        model.addAttribute("meetingInputForm", new MeetingInputForm(
                meeting.subject(),
                meeting.memo(),
                ScheduleInputs.of(schedules)
        ));

        return VIEW_PATH_FORM;
    }

    @PostMapping(params={"addSchedule"})
    String addSchedule(Model model,
                       @ModelAttribute("meetingInputForm") final MeetingInputForm meetingInputForm) {

        final MeetingInputForm scheduleAdded = meetingInputForm.addEmptySchedule();
        model.addAttribute("meetingInputForm", scheduleAdded);
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
    String edit(@PathVariable("meetingNumber") final MeetingNumber meetingNumber,
                @Validated @ModelAttribute("meetingInputForm") final MeetingInputForm meetingInputForm,
                BindingResult bindingResult,
                @CookieValue("organizerNumber") final String organizerNumberValue,
                RedirectAttributes attributes,
                UriComponentsBuilder builder) {

        final Meeting meeting = meetingEditScenario.meetingOf(meetingNumber);
        final OrganizerNumber organizerNumber = OrganizerNumber.of(organizerNumberValue);

        if (meeting.editabilityFor(organizerNumber) != MeetingEditability.編集可能) {
            throw new MeetingCannotEditException();
        }

        if (bindingResult.hasErrors()) return "meeting/edit/form";

        final MeetingEditRequest meetingEditRequest = meetingInputForm.toEditRequest(meetingNumber, organizerNumber);
        meetingEditScenario.edit(meeting, meetingEditRequest);

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
                  @CookieValue("organizerNumber") final String organizerNumberValue,
                  UriComponentsBuilder builder) {

        final Meeting meeting = meetingEditScenario.meetingOf(meetingNumber);
        final OrganizerNumber organizerNumber = OrganizerNumber.of(organizerNumberValue);

        if (meeting.editabilityFor(organizerNumber) != MeetingEditability.編集可能) {
            throw new MeetingCannotDeleteException();
        }

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
