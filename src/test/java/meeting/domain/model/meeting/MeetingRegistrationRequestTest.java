package meeting.domain.model.meeting;

import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MeetingRegistrationRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void 追加する日程が過去の場合はエラー() {

        ScheduleConstruct scheduleConstruct =
                ScheduleConstruct.of(2022, 7, 1, 10, 0, 60);
        Schedule schedule = Schedule.initializedBy(scheduleConstruct);
        Schedules schedules = Schedules.of(Arrays.asList(schedule));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new MeetingRegistrationRequest(
                        MeetingNumber.newNumber(),
                        Subject.of("件名テスト"),
                        Memo.blank(),
                        schedules,
                        OrganizerNumber.newNumber(),
                        RegistrationRequestDateTime.of(2022, 7, 1, 11, 0)
                );
            });
        assertEquals("日程を追加する時は未来の日時を指定してください。", exception.getMessage());
    }
}
