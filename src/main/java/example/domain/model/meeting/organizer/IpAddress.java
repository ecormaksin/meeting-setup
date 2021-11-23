package example.domain.model.meeting.organizer;

import javax.validation.constraints.NotBlank;

/**
 * 主催者端末のIPアドレス
 */
public class IpAddress {
    @NotBlank(message = "{example.domain.model.meeting.organizer.IpAddress.NotBlank.message}")
    String value;

    public IpAddress(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Deprecated
    IpAddress() {
    }
}
