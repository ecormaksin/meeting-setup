package meeting.domain.model.schedule;

import meeting.domain.model.schedule.construct.Duration;
import meeting.domain.model.schedule.construct.ScheduleConstruct;
import meeting.domain.model.schedule.construct.StartMinute;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SchedulesTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void 件数が0件の場合はエラー() {

        SchedulesInvalidException exception = assertThrows(SchedulesInvalidException.class, () -> {
            Schedules.of(new ArrayList<>());
        });
        assertEquals("日程を1件以上5件以下で指定してください。", exception.getMessage());
    }

    @Test
    void 件数が規定上限を超えている場合はエラー() {

        List<Schedule> scheduleList = new ArrayList<>();

        for (int count = 1; count <= 6; count++) {
            Schedule schedule = Schedule.initializedBy(
                ScheduleConstruct.of(2022, 1, count, 10, StartMinute.JUST, Duration.OF_1_HOUR)
            );
            scheduleList.add(schedule);
        }

        SchedulesInvalidException exception = assertThrows(SchedulesInvalidException.class, () -> {
            Schedules.of(scheduleList);
        });
        assertEquals("日程を1件以上5件以下で指定してください。", exception.getMessage());
    }

    @Test
    void 日程が重複している場合はエラー() {

        List<Schedule> scheduleList = new ArrayList<>();

        for (int count = 1; count <= 2; count++) {
            Schedule schedule = Schedule.initializedBy(
                    ScheduleConstruct.of(2022, 1, 1, 10, StartMinute.JUST, Duration.OF_1_HOUR)
            );
            scheduleList.add(schedule);
        }

        SchedulesInvalidException exception = assertThrows(SchedulesInvalidException.class, () -> {
            Schedules.of(scheduleList);
        });
        assertEquals("日程は重複のないように指定してください。", exception.getMessage());
    }
}
