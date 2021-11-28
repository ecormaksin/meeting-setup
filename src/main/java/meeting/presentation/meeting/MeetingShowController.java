package meeting.presentation.meeting;

import meeting.domain.model.meeting.MeetingId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ミーティング表示画面
 */
@Controller
@RequestMapping("meeting")
public class MeetingShowController {

    @GetMapping("{meetingId}")
    String show(@PathVariable("meetingId") MeetingId meetingId) {
        return "meeting/show";
    }
}
