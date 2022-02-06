package meeting.presentation.meeting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ミーティング削除画面
 */
@Controller
@RequestMapping("meeting/{meetingId}/delete")
public class MeetingDeleteController {
    @GetMapping("completed")
    String completed() {
        return "meeting/delete/completed";
    }
}
