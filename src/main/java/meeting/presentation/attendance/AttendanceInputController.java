package meeting.presentation.attendance;

import meeting.application.senario.AttendanceInputScenario;
import meeting.domain.model.attendance.AttendanceNumber;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.domain.model.participant.ParticipantNumberDuplicationStatus;
import meeting.domain.model.schedule.Schedules;
import meeting.presentation.attendance.input.AttendanceInputForm;
import meeting.presentation.participant.ParticipantNumberProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 出欠入力画面
 */
@Controller
@RequestMapping("meeting/{meetingNumber}/attendance/input")
public class AttendanceInputController {
    private static final String VIEW_PATH_FORM = "attendance/input/form";

    private final AttendanceInputScenario attendanceInputScenario;
    private final ParticipantNumberProxy participantNumberProxy;

    public AttendanceInputController(AttendanceInputScenario attendanceInputScenario,
                                     ParticipantNumberProxy participantNumberProxy) {
        this.attendanceInputScenario = attendanceInputScenario;
        this.participantNumberProxy = participantNumberProxy;
    }

    @GetMapping
    String init(Model model,
                @PathVariable("meetingNumber") MeetingNumber meetingNumber,
                @CookieValue("participantNumber") final String participantNumberValue) {

        final ParticipantNumber participantNumber = ParticipantNumber.of(participantNumberValue);

        ParticipantName participantName = attendanceInputScenario.latestParticipantName(participantNumber);
        Schedules schedules = attendanceInputScenario.schedules(meetingNumber);
        AttendanceInputForm attendanceInputForm = AttendanceInputForm.empty(participantName, schedules);

        model.addAttribute("attendanceInputForm", attendanceInputForm);

        return VIEW_PATH_FORM;
    }

    @PostMapping
    String input(@PathVariable("meetingNumber") MeetingNumber meetingNumber,
                 @Validated @ModelAttribute("attendanceInputForm") AttendanceInputForm attendanceInputForm,
                 BindingResult bindingResult, RedirectAttributes attributes,
                 @CookieValue("participantNumber") final String participantNumberValue,
                 UriComponentsBuilder builder) {

        if (bindingResult.hasErrors()) return VIEW_PATH_FORM;

        final ParticipantNumber participantNumber = ParticipantNumber.of(participantNumberValue);

        if (participantNumberDuplicated(
                meetingNumber,
                participantNumber,
                bindingResult
        )) {
            return VIEW_PATH_FORM;
        }

        if (participantNameDuplicated(
                meetingNumber,
                attendanceInputForm.participantName(),
                bindingResult
        )) {
            return VIEW_PATH_FORM;
        }

        AttendanceNumber attendanceNumber = AttendanceNumber.newNumber();
        attendanceInputScenario.input(meetingNumber, attendanceInputForm.toInputRequest(attendanceNumber, participantNumber));

        attributes.addFlashAttribute("meetingNumber", meetingNumber.value());
        attributes.addFlashAttribute("attendanceNumber", attendanceNumber.value());

        URI uri = builder.path(String.format("meeting/%s/attendance/input/completed", meetingNumber.value())).build().toUri();
        return "redirect:" + uri.toString();
    }

    private boolean participantNumberDuplicated(
            MeetingNumber meetingNumber,
            ParticipantNumber participantNumber,
            BindingResult bindingResult
    ) {

        ParticipantNumberDuplicationStatus participantNumberDuplicationStatus =
                attendanceInputScenario.participantNumberDuplicationStatus(meetingNumber, participantNumber);

        if (participantNumberDuplicationStatus == ParticipantNumberDuplicationStatus.あり) {
            bindingResult.reject(null, participantNumberDuplicationStatus.errorMessage());
            return true;
        }

        return false;
    }

    private boolean participantNameDuplicated(
            MeetingNumber meetingNumber,
            ParticipantName participantName,
            BindingResult bindingResult
    ) {

        ParticipantNameDuplicationStatus participantNameDuplicationStatus =
                attendanceInputScenario.participantNameDuplicationStatus(
                        meetingNumber,
                        participantName);

        if (participantNameDuplicationStatus == ParticipantNameDuplicationStatus.あり) {
            bindingResult.rejectValue("participantName.value", null,
                    participantNameDuplicationStatus.errorMessage());
            return true;
        }

        return false;
    }

    @GetMapping("completed")
    String completed(@ModelAttribute("meetingNumber") String meetingNumber,
                     @ModelAttribute("attendanceNumber") String attendanceNumber,
                     Model model, UriComponentsBuilder builder) {

        model.addAttribute("meetingNumber", meetingNumber);

        URI uri = builder.path(String.format("/meeting/%s/attendance/%s/edit", meetingNumber, attendanceNumber)).build().toUri();
        model.addAttribute("attendanceEditLocation", uri.toString());

        return "attendance/input/completed";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "participantNumber.value",
                "participantName.value",
                "conveniences.list[*",
                "attendanceComment.value",
                "schedules.list[*"
        );
    }
}
