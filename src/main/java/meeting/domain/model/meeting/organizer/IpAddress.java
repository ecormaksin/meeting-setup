package meeting.domain.model.meeting.organizer;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * IPアドレス
 */
public class IpAddress {
    @NotBlank(message = "{meeting.domain.model.meeting.organizer.IpAddress.NotBlank.message}")
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

    @Override
    public boolean equals(Object anObject) {
        if (Objects.isNull(anObject)) return false;
        if (!(anObject instanceof IpAddress other)) return false;
        if (Objects.isNull(other.value)) return false;
        return other.value.equals(value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
