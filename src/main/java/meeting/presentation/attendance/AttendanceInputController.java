package meeting.presentation.attendance;

import meeting.application.senario.AttendanceInputScenario;
import meeting.domain.model.attendance.AttendanceNumber;
import meeting.domain.model.attendance.AttendanceInputRequest;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.schedule.Schedules;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static meeting.domain.model.participant.ParticipantNameDuplicationStatus.あり;

/**
 * 出欠入力画面
 */
@Controller
@RequestMapping("meeting/{meetingNumber}/attendance/input")
public class AttendanceInputController {
    AttendanceInputScenario attendanceInputScenario;

    public AttendanceInputController(AttendanceInputScenario attendanceInputScenario) {
        this.attendanceInputScenario = attendanceInputScenario;
    }

    @GetMapping
    String init(Model model, @PathVariable("meetingNumber") MeetingNumber meetingNumber) {

        Schedules schedules = attendanceInputScenario.schedules(meetingNumber);
        AttendanceInputRequest attendanceInputRequest = AttendanceInputRequest.empty(schedules);

        model.addAttribute("attendanceInputRequest", attendanceInputRequest);

        return "attendance/input/form";
    }

    @PostMapping
    String input(@PathVariable("meetingNumber") MeetingNumber meetingNumber,
                 @Validated @ModelAttribute("attendanceInputRequest") AttendanceInputRequest attendanceInputRequest,
                 BindingResult bindingResult, RedirectAttributes attributes,
                 UriComponentsBuilder builder) {

        if (bindingResult.hasErrors()) return "attendance/input/form";

        ParticipantNameDuplicationStatus participantNameDuplicationStatus =
                attendanceInputScenario.participantNameDuplicationStatus(
                        meetingNumber,
                        attendanceInputRequest.participantName());
        if (participantNameDuplicationStatus == あり) {
            bindingResult.rejectValue("participantName.value", null,
                    participantNameDuplicationStatus.errorMessage());
            return "attendance/input/form";
        }

        AttendanceNumber attendanceNumber = attendanceInputScenario.input(meetingNumber, attendanceInputRequest);

        attributes.addFlashAttribute("meetingNumber", meetingNumber.value());
        attributes.addFlashAttribute("attendanceNumber", attendanceNumber.value());

        URI uri = builder.path(String.format("meeting/%s/attendance/input/completed", meetingNumber.value())).build().toUri();
        return "redirect:" + uri.toString();
    }

    @GetMapping("completed")
    String completed(@ModelAttribute("meetingNumber") String meetingNumber,
                     @ModelAttribute("attendanceId") String attendanceId,
                     Model model, UriComponentsBuilder builder) {

        model.addAttribute("meetingNumber", meetingNumber);

        URI uri = builder.path(String.format("/meeting/%s/attendance/%s/edit", meetingNumber, attendanceId)).build().toUri();
        model.addAttribute("attendanceEditLocation", uri.toString());

        return "attendance/input/completed";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "participantName.value",
                "conveniences.list[*",
                "attendanceComment.value",
                "schedules.list[*"
        );
    }
}
