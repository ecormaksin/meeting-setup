package meeting.presentation.meeting;

import meeting.domain.model.meeting.MeetingNumber;
import org.springframework.ui.Model;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Objects;

public class MeetingControllerHelper {

    public static void setMeetingShowLocation(Model model,
                                              MeetingNumber meetingNumber,
                                              UriComponentsBuilder builder,
                                              HttpServletRequest request) {

        URI uri = builder
                .scheme(scheme(request))
                .host(host(request))
                .path(String.format("/meeting/%s/show", meetingNumber.value()))
                .build()
                .toUri();
        model.addAttribute("meetingShowLocation", uri.toString());
    }

    private static String scheme(HttpServletRequest request) {
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (Objects.isNull(scheme) || scheme.isBlank()) {
            scheme = request.getScheme();
        }
        return scheme;
    }

    private static String host(HttpServletRequest request) {
        String host = request.getHeader("X-Forwarded-Host");
        if (Objects.isNull(host) || host.isBlank()) {
            host = request.getServerName();
        }
        return host;
    }
}