package meeting.domain.model.schedule.construct;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class ScheduleConstructTest {

    @Test
    void equalsHashCodeContracts() {
        EqualsVerifier.simple().forClass(ScheduleConstruct.class).verify();
    }
}
