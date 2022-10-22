package meeting.domain.model.schedule.construct;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScheduleConstructTest {

    @Test
    void 等価性のテスト() {

        ScheduleConstruct scheduleConstructA = ScheduleConstruct.of(2022, 1, 1, 10, StartMinute.JUST, Duration.ONE_HOUR);
        ScheduleConstruct scheduleConstructB = ScheduleConstruct.of(2022, 1, 1, 10, StartMinute.JUST, Duration.ONE_HOUR);

        Set<ScheduleConstruct> constructSet = new HashSet<>();
        assertTrue(constructSet.add(scheduleConstructA));
        assertFalse(constructSet.add(scheduleConstructB));
    }
}
