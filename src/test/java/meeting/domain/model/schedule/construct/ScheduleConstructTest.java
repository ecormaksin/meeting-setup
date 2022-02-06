package meeting.domain.model.schedule.construct;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScheduleConstructTest {

    @Test
    void _すべての項目が適切に入力されている場合は妥当と判定される() {

        ScheduleConstruct scheduleConstruct = ScheduleConstruct.of(LocalDate.of(2022, 1, 1),
                LocalTime.of(10,0),
                LocalTime.of(11,0));

        assertEquals(ScheduleConstructValidity.妥当_すべて入力済, scheduleConstruct.validity());
    }

    @Test
    void _すべて未入力の場合はすべて未入力で妥当と判定される() {

        ScheduleConstruct scheduleConstruct = ScheduleConstruct.empty();

        assertEquals(ScheduleConstructValidity.妥当_すべて未入力, scheduleConstruct.validity());
    }

    @Test
    void _開催日が未入力の場合は一部未入力で不当と判定される() {

        ScheduleConstruct scheduleConstruct = ScheduleConstruct.of(null,
                LocalTime.of(10,0),
                LocalTime.of(11,0));

        assertEquals(ScheduleConstructValidity.不当_一部未入力あり, scheduleConstruct.validity());
    }

    @Test
    void _開始時刻が未入力の場合は一部未入力で不当と判定される() {

        ScheduleConstruct scheduleConstruct = ScheduleConstruct.of(LocalDate.of(2022, 1, 1),
                null,
                LocalTime.of(11,0));

        assertEquals(ScheduleConstructValidity.不当_一部未入力あり, scheduleConstruct.validity());
    }

    @Test
    void _終了時刻が未入力の場合は一部未入力で不当と判定される() {

        ScheduleConstruct scheduleConstruct = ScheduleConstruct.of(LocalDate.of(2022, 1, 1),
                LocalTime.of(10,0),
                null);

        assertEquals(ScheduleConstructValidity.不当_一部未入力あり, scheduleConstruct.validity());
    }

    @Test
    void _開始時刻が終了時刻より後の場合は時刻の前後関係が不当と判定される() {

        ScheduleConstruct scheduleConstruct = ScheduleConstruct.of(LocalDate.of(2022, 1, 1),
                LocalTime.of(10,1),
                LocalTime.of(10,0));

        assertEquals(ScheduleConstructValidity.不当_時刻の前後関係が不当, scheduleConstruct.validity());
    }

    @Test
    void _開始時刻が終了時刻と同じ場合は時刻の前後関係が不当と判定される() {

        ScheduleConstruct scheduleConstruct = ScheduleConstruct.of(LocalDate.of(2022, 1, 1),
                LocalTime.of(10,0),
                LocalTime.of(10,0));

        assertEquals(ScheduleConstructValidity.不当_時刻の前後関係が不当, scheduleConstruct.validity());
    }
}
