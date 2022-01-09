package meeting.presentation;

import meeting.domain.model.meeting.organizer.ClientBrowser;
import meeting.domain.model.meeting.organizer.IpAddress;
import meeting.domain.model.meeting.organizer.OSFamily;
import meeting.domain.model.meeting.organizer.UserAgentFamily;
import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class ClientInfoGetter {

    Parser parser;

    public ClientInfoGetter(Parser parser) {
        this.parser = parser;
    }

    public ClientBrowser clientBrowser(HttpServletRequest req) {

        IpAddress ipAddress = new IpAddress(clientIPAddress(req));

        String userAgent = req.getHeader("user-agent");
        Client client = parser.parse(userAgent);
        OSFamily osFamily = new OSFamily(client.os.family);
        UserAgentFamily userAgentFamily = new UserAgentFamily(client.userAgent.family);

        return new ClientBrowser(ipAddress, osFamily, userAgentFamily);
    }

    private String clientIPAddress(HttpServletRequest req) {
        String header = req.getHeader("X-Forwarded-For");
        if (Objects.isNull(header)) {
            return req.getRemoteAddr();
        }
        return header.split(" *, *")[0];
    }
}
