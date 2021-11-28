package meeting.domain.model.meeting.organizer;

import javax.validation.Valid;

/**
 * 主催者の端末
 */
public class OrganizerTerminal {

    @Valid
    IpAddress ipAddress;

    @Valid
    OSFamily osFamily;

    @Valid
    UserAgentFamily userAgentFamily;

    public OrganizerTerminal(@Valid IpAddress ipAddress,
                             @Valid OSFamily osFamily,
                             @Valid UserAgentFamily userAgentFamily) {
        this.ipAddress = ipAddress;
        this.osFamily = osFamily;
        this.userAgentFamily = userAgentFamily;
    }

    public IpAddress ipAddress() {
        return ipAddress;
    }

    public OSFamily osFamily() {
        return osFamily;
    }

    public UserAgentFamily userAgentFamily() {
        return userAgentFamily;
    }

    @Deprecated
    OrganizerTerminal () {}
}
