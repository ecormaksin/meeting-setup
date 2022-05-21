package meeting.presentation.attendance;

import meeting.domain.model.meeting.MeetingNumber;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 出欠削除画面
 */
@Controller
@RequestMapping("meeting/{meetingNumber}/attendance/{attendanceId}/delete")
public class AttendanceDeleteController {
    @GetMapping("completed")
    String completed(@PathVariable("meetingNumber") MeetingNumber meetingNumber) {
        return "attendance/delete/completed";
    }
}
