package meeting.domain.model.meeting.organizer;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * クライアントのブラウザー
 */
public class ClientBrowser {

    @Valid
    IpAddress ipAddress;

    @Valid
    OSFamily osFamily;

    @Valid
    UserAgentFamily userAgentFamily;

    public ClientBrowser(@Valid IpAddress ipAddress,
                         @Valid OSFamily osFamily,
                         @Valid UserAgentFamily userAgentFamily) {
        this.ipAddress = ipAddress;
        this.osFamily = osFamily;
        this.userAgentFamily = userAgentFamily;
    }

    public static ClientBrowser of (@NotBlank String noWrappedIpAddress,
                                    @NotBlank String noWrappedOSFamily,
                                    @NotBlank String noWrappedUserAgentFamily) {
        return new ClientBrowser(
                new IpAddress(noWrappedIpAddress),
                new OSFamily(noWrappedOSFamily),
                new UserAgentFamily(noWrappedUserAgentFamily)
        );
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
    ClientBrowser() {}

    @Override
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof ClientBrowser other)) return false;
        if (!ipAddress.equals(other.ipAddress)) return false;
        if (!osFamily.equals(other.osFamily)) return false;
        if (!userAgentFamily.equals(other.userAgentFamily)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress, osFamily, userAgentFamily);
    }
}
