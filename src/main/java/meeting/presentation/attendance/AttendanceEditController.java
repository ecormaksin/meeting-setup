package meeting.presentation.attendance;

import meeting.application.senario.AttendanceEditScenario;
import meeting.domain.model.attendance.*;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.attendance.convenience.Conveniences;
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
import java.util.List;
import java.util.Objects;

import static meeting.domain.model.attendance.ParticipantNameDuplicationStatus.あり;

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

        return "attendance/edit";
    }

    @PostMapping
    String edit(@PathVariable("meetingId") MeetingId meetingId,
                 @PathVariable("attendanceId") AttendanceId attendanceId,
                 @Validated @ModelAttribute("attendanceEditRequest") AttendanceEditRequest attendanceEditRequest,
                 BindingResult bindingResult, RedirectAttributes attributes,
                 UriComponentsBuilder builder) {

        if (bindingResult.hasErrors()) return "attendance/edit";

        if (checkIfNotInputConvenience(bindingResult, attendanceEditRequest.conveniences())) return "attendance/edit";

        ParticipantNameDuplicationStatus participantNameDuplicationStatus =
                attendanceEditScenario.participantNameDuplicationStatus(
                        meetingId,
                        attendanceId,
                        attendanceEditRequest.participantName());
        if (participantNameDuplicationStatus == あり) {
            bindingResult.rejectValue("participantName.value", "error.code.participantName.duplicated");
            return "attendance/edit";
        }

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

    private boolean checkIfNotInputConvenience(BindingResult bindingResult, Conveniences conveniences) {

        boolean existNotInputConvenience = false;

        List<Convenience> convenienceList = conveniences.asList();
        for (int i = 0; i < convenienceList.size(); i++) {
            Convenience convenience = convenienceList.get(i);
            ConvenienceCategory convenienceCategory = convenience.convenienceCategory();
            if (Objects.nonNull(convenienceCategory) && convenienceCategory != ConvenienceCategory.未回答) {
                continue;
            }
            existNotInputConvenience = true;
            bindingResult.rejectValue("conveniences.list[" + i + "].convenienceCategory",
                    "error.code.convenience.notInput");
        }

        return existNotInputConvenience;
    }

    @GetMapping("completed")
    String completed(@ModelAttribute("meetingId") String meetingId,
                     @ModelAttribute("attendanceId") String attendanceId,
                     Model model, UriComponentsBuilder builder) {

        model.addAttribute("meetingId", meetingId);

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
