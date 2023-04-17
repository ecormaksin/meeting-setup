package meeting.presentation.meeting;

import meeting.domain.model.meeting.MeetingNumber;
import org.springframework.ui.Model;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

public class MeetingControllerHelper {

    public static void setMeetingShowLocation(Model model,
                                              MeetingNumber meetingNumber,
                                              UriComponentsBuilder builder,
                                              HttpServletRequest request) {

        URI uri = builder
                .path(String.format("/meeting/%s/show", meetingNumber.value()))
                .build()
                .toUri();
        model.addAttribute("meetingShowLocation", uri.toString());
    }
}