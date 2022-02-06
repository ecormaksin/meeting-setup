package meeting.domain.model.schedule;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SchedulesTest {

    @Test
    void _1件で妥当な場合は妥当と判定される() {

        List<Schedule> list = Arrays.asList(Schedule.of(1,
                LocalDate.of(2022, 1,1),
                LocalTime.of(10,0),
                LocalTime.of(11,0)));
        Schedules schedules = Schedules.of(list);

        assertEquals(SchedulesValidity.妥当, schedules.validity());
    }

    @Test
    void _1件で未入力の場合は日程候補なしで不当と判定される() {

        List<Schedule> list = Arrays.asList(Schedule.empty());
        Schedules schedules = Schedules.of(list);

        assertEquals(SchedulesValidity.不当_日程候補なし, schedules.validity());
    }

    @Test
    void _3件がすべて妥当な場合は妥当と判定される() {

        LocalDate currentDate = LocalDate.now();
        List<Schedule> list = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            int count = i + 1;
            list.add(Schedule.of(count,
                    currentDate.plusDays(count),
                    LocalTime.of(10,0),
                    LocalTime.of(11,0)));
        }
        Schedules schedules = Schedules.of(list);

        assertEquals(SchedulesValidity.妥当, schedules.validity());
    }

    @Test
    void _3件がすべて未入力の場合は日程候補なしで不当と判定される() {

        List<Schedule> list = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            list.add(Schedule.empty());
        }
        Schedules schedules = Schedules.of(list);

        assertEquals(SchedulesValidity.不当_日程候補なし, schedules.validity());
    }

    @Test
    void _3件中1件が妥当で2件が不当の場合は日程不備で不当と判定される() {

        List<Schedule> list = Arrays.asList(
                Schedule.of(1,
                        LocalDate.of(2022, 1,1),
                        LocalTime.of(10,0),
                        LocalTime.of(11,0)),
                Schedule.of(2,
                        null,
                        LocalTime.of(10,0),
                        LocalTime.of(11,0)),
                Schedule.of(3,
                        LocalDate.of(2022, 1,1),
                        null,
                        LocalTime.of(11,0))
        );
        Schedules schedules = Schedules.of(list);

        assertEquals(SchedulesValidity.不当_日程に不備あり, schedules.validity());
    }

    @Test
    void 日程が重複している場合は不当と判定される() {

        LocalDate currentDate = LocalDate.now();
        List<Schedule> list = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            int count = i + 1;
            list.add(Schedule.of(count,
                    currentDate.plusDays(1),
                    LocalTime.of(10,0),
                    LocalTime.of(11,0)));
        }
        Schedules schedules = Schedules.of(list);

        assertEquals(SchedulesValidity.不当_日程重複, schedules.validity());
    }
}
