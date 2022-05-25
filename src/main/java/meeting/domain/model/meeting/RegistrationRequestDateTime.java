package meeting.domain.model.meeting;

import java.time.LocalDateTime;

/**
 * 登録依頼日時
 */
public class RegistrationRequestDateTime {

    private LocalDateTime value;

    @Deprecated
    RegistrationRequestDateTime() {}

    public RegistrationRequestDateTime(final LocalDateTime value) {
        this.value = value;
    }

    public static RegistrationRequestDateTime now() {
        return new RegistrationRequestDateTime(LocalDateTime.now());
    }

    public LocalDateTime value() {
        return value;
    }
}
