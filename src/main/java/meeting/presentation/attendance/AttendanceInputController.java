package meeting.presentation.attendance;

import meeting.application.senario.AttendanceInputScenario;
import meeting.domain.model.attendance.AttendanceInputRequest;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.schedule.Schedules;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 出欠入力画面
 */
@Controller
@RequestMapping("meeting/{meetingId}/attendance/input")
public class AttendanceInputController {
    AttendanceInputScenario attendanceInputScenario;

    public AttendanceInputController(AttendanceInputScenario attendanceInputScenario) {
        this.attendanceInputScenario = attendanceInputScenario;
    }

    @GetMapping
    String init(Model model, @PathVariable("meetingId") MeetingId meetingId) {
        Schedules schedules = attendanceInputScenario.schedules(meetingId);
        AttendanceInputRequest attendanceInputRequest = AttendanceInputRequest.empty(schedules);

        model.addAttribute("meetingId", meetingId);
        model.addAttribute("attendanceInputRequest", attendanceInputRequest);
        model.addAttribute("schedules", schedules);

        return "attendance/form";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "participantName.value",
                "attendanceComment.value",
                "conveniences.*"
        );
    }
}
