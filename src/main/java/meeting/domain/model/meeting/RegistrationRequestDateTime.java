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

    public static RegistrationRequestDateTime of(final LocalDateTime value) {
        return new RegistrationRequestDateTime(value);
    }

    public static RegistrationRequestDateTime of(int year, int month, int dayOfMonth, int hour, int minute) {
        final LocalDateTime value = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
        return new RegistrationRequestDateTime(value);
    }

    public LocalDateTime value() {
        return value;
    }
}
