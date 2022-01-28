package meeting.presentation.attendance;

import meeting.application.senario.AttendanceEditScenario;
import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceId;
import meeting.domain.model.attendance.AttendanceEditRequest;
import meeting.domain.model.meeting.MeetingId;
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

/**
 * 出欠編集画面
 */
@Controller
@RequestMapping("meeting/{meetingId}/attendance/{attendanceId}/edit")
public class AttendanceEditController {
    AttendanceEditScenario attendanceEditScenario;

    public AttendanceEditController(AttendanceEditScenario attendanceEditScenario) {
        this.attendanceEditScenario = attendanceEditScenario;
    }

    @GetMapping
    String init(Model model,
                @PathVariable("meetingId") MeetingId meetingId,
                @PathVariable("attendanceId") AttendanceId attendanceId) {
        Schedules schedules = attendanceEditScenario.schedules(meetingId);
        Attendance attendance = attendanceEditScenario.attendanceOf(attendanceId);
        AttendanceEditRequest attendanceEditRequest = new AttendanceEditRequest(
                attendanceId,
                attendance.participantName(),
                attendance.conveniences(),
                attendance.attendanceComment(),
                schedules
        );

        model.addAttribute("attendanceEditRequest", attendanceEditRequest);
        model.addAttribute("meetingId", meetingId.value());
        model.addAttribute("attendanceId", attendanceId.value());

        return "attendance/edit";
    }

    @PostMapping
    String edit(@PathVariable("meetingId") MeetingId meetingId,
                 @PathVariable("attendanceId") AttendanceId attendanceId,
                 @Validated @ModelAttribute("attendanceEditRequest") AttendanceEditRequest attendanceEditRequest,
                 BindingResult bindingResult, RedirectAttributes attributes,
                 UriComponentsBuilder builder) {
        if (bindingResult.hasErrors()) return "attendance/edit";

        attendanceEditScenario.edit(meetingId, attendanceEditRequest);

        attributes.addFlashAttribute("meetingId", meetingId.value());
        attributes.addFlashAttribute("attendanceId", attendanceId.value());

        URI uri = builder.path(
                        String.format("meeting/%s/attendance/%s/edit/completed",
                                meetingId.value(),
                                attendanceId.value()
                    )).build().toUri();
        return "redirect:" + uri.toString();
    }

    @GetMapping("completed")
    String completed(@ModelAttribute("meetingId") String meetingId,
                     @ModelAttribute("attendanceId") String attendanceId,
                     Model model, UriComponentsBuilder builder) {
        URI uri = builder.path(String.format("/meeting/%s/attendance/%s/edit", meetingId, attendanceId)).build().toUri();
        model.addAttribute("attendanceEditLocation", uri.toString());
        return "attendance/completed";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "attendanceId.value",
                "participantName.value",
                "conveniences.list[*",
                "attendanceComment.value",
                "schedules.list[*"
        );
    }
}
