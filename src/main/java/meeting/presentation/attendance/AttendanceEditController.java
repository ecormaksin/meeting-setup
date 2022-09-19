package meeting.presentation.attendance;

import meeting.application.senario.AttendanceEditScenario;
import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceNumber;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.domain.model.participant.ParticipantNumberDuplicationStatus;
import meeting.domain.model.schedule.Schedules;
import meeting.presentation.attendance.input.AttendanceInputForm;
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
 * 出欠編集画面
 */
@Controller
@RequestMapping("meeting/{meetingNumber}/attendance/{attendanceNumber}/edit")
public class AttendanceEditController {

    private static final String VIEW_PATH_FORM = "attendance/edit/form";

    private final AttendanceEditScenario attendanceEditScenario;

    public AttendanceEditController(AttendanceEditScenario attendanceEditScenario) {
        this.attendanceEditScenario = attendanceEditScenario;
    }

    @GetMapping
    String init(Model model,
                @PathVariable("meetingNumber") MeetingNumber meetingNumber,
                @PathVariable("attendanceNumber") AttendanceNumber attendanceNumber) {

        Schedules schedules = attendanceEditScenario.schedules(meetingNumber);
        Attendance attendance = attendanceEditScenario.attendanceOf(attendanceNumber);
        AttendanceInputForm attendanceInputForm = new AttendanceInputForm(
                attendance.participantName(),
                attendance.conveniences(),
                attendance.attendanceComment(),
                schedules
        );

        model.addAttribute("attendanceInputForm", attendanceInputForm);

        return VIEW_PATH_FORM;
    }

    @PostMapping
    String edit(@PathVariable("meetingNumber") MeetingNumber meetingNumber,
                @PathVariable("attendanceNumber") AttendanceNumber attendanceNumber,
                @Validated @ModelAttribute("attendanceInputForm") AttendanceInputForm attendanceInputForm,
                BindingResult bindingResult, RedirectAttributes attributes,
                @CookieValue("participantNumber") final String participantNumberValue,
                UriComponentsBuilder builder) {

        if (bindingResult.hasErrors()) return VIEW_PATH_FORM;

        final ParticipantNumber participantNumber = ParticipantNumber.of(participantNumberValue);

        if (participantNumberDuplicated(
                meetingNumber,
                attendanceNumber,
                participantNumber,
                bindingResult
        )) {
            return VIEW_PATH_FORM;
        }

        if (participantNameDuplicated(
                meetingNumber,
                attendanceNumber,
                attendanceInputForm.participantName(),
                bindingResult
        )) {
            return VIEW_PATH_FORM;
        }

        attendanceEditScenario.edit(meetingNumber, attendanceInputForm.toEditRequest(attendanceNumber, participantNumber));

        attributes.addFlashAttribute("meetingNumber", meetingNumber.value());
        attributes.addFlashAttribute("attendanceNumber", attendanceNumber.value());

        URI uri = builder.path(
                        String.format("meeting/%s/attendance/%s/edit/completed",
                                meetingNumber.value(),
                                attendanceNumber.value()
                    )).build().toUri();
        return "redirect:" + uri.toString();
    }

    private boolean participantNumberDuplicated(
            MeetingNumber meetingNumber,
            AttendanceNumber attendanceNumber,
            ParticipantNumber participantNumber,
            BindingResult bindingResult
    ) {

        ParticipantNumberDuplicationStatus participantNumberDuplicationStatus =
                attendanceEditScenario.participantNumberDuplicationStatus(meetingNumber, attendanceNumber, participantNumber);

        if (participantNumberDuplicationStatus == ParticipantNumberDuplicationStatus.あり) {
            bindingResult.reject(null, participantNumberDuplicationStatus.errorMessage());
            return true;
        }

        return false;
    }

    private boolean participantNameDuplicated(
            MeetingNumber meetingNumber,
            AttendanceNumber attendanceNumber,
            ParticipantName participantName,
            BindingResult bindingResult
    ) {

        ParticipantNameDuplicationStatus participantNameDuplicationStatus =
                attendanceEditScenario.participantNameDuplicationStatus(
                        meetingNumber,
                        attendanceNumber,
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

        return "attendance/edit/completed";
    }

    @PostMapping(params={"delete"})
    String delete(@PathVariable("meetingNumber") MeetingNumber meetingNumber,
                  @PathVariable("attendanceNumber") AttendanceNumber attendanceNumber,
                  UriComponentsBuilder builder) {

        attendanceEditScenario.delete(attendanceNumber);

        URI uri = builder.path(
                String.format("meeting/%s/attendance/%s/delete/completed",
                        meetingNumber.value(),
                        attendanceNumber.value()
                )).build().toUri();
        return "redirect:" + uri.toString();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "attendanceNumber.value",
                "participantName.value",
                "conveniences.list[*",
                "attendanceComment.value",
                "schedules.list[*"
        );
    }
}
