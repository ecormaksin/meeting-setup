package meeting.domain.model.meeting;

import mockit.Mock;
import mockit.MockUp;

import java.time.LocalDateTime;

public class RegistrationRequestDateTimeFake_19841231_1023 extends MockUp<RegistrationRequestDateTime> {

    @Mock
    public static RegistrationRequestDateTime now() {
        return new RegistrationRequestDateTime(LocalDateTime.of(1984, 12, 31, 10, 23));
    }
}
