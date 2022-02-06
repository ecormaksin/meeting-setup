package meeting.domain.model.schedule;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScheduleTest {

    LocalDateTime futureDateTime = LocalDateTime.now()
            .plusDays(1);
    LocalDateTime pastDateTime = LocalDateTime.now()
            .minusDays(1);

    @Test
    void すべて未入力の場合は妥当と判定される() throws Exception {
        Schedule schedule = Schedule.empty();
        assertEquals(ScheduleValidity.妥当_未入力, schedule.validity());
    }

    @Test
    void 作成時の日程が未来の場合は妥当と判断される() throws Exception {
        Schedule schedule = Schedule.of(futureDateTime.toLocalDate(),
                futureDateTime.toLocalTime(),
                futureDateTime.plusHours(1).toLocalTime());
        assertEquals(ScheduleValidity.妥当, schedule.validity());
    }

    @Test
    void 作成時の日程が過去の場合は不当と判断される() throws Exception {
        Schedule schedule = Schedule.of(pastDateTime.toLocalDate(),
                pastDateTime.toLocalTime(),
                pastDateTime.plusHours(1).toLocalTime());
        assertEquals(ScheduleValidity.不当_追加時に過去, schedule.validity());
    }

    @Test
    void 編集時の日程が未来の場合は妥当と判断される() throws Exception {
        Schedule schedule = Schedule.of(1,
                futureDateTime.toLocalDate(),
                futureDateTime.toLocalTime(),
                futureDateTime.plusHours(1).toLocalTime());
        assertEquals(ScheduleValidity.妥当, schedule.validity());
    }

    @Test
    void 編集時の日程が過去の場合は妥当と判断される() throws Exception {
        Schedule schedule = Schedule.of(1,
                pastDateTime.toLocalDate(),
                pastDateTime.toLocalTime(),
                pastDateTime.plusHours(1).toLocalTime());
        assertEquals(ScheduleValidity.妥当, schedule.validity());
    }

    @Test
    void 開催日が未入力の場合は構成要素の問題として不当と判定される() throws Exception {
        Schedule schedule = Schedule.of(1,
                null,
                LocalTime.of(10, 0),
                LocalTime.of(11,0));
        assertEquals(ScheduleValidity.不当_構成要素の問題, schedule.validity());
    }

    @Test
    void 開始時刻が未入力の場合は構成要素の問題として不当と判定される() throws Exception {
        Schedule schedule = Schedule.of(1,
                LocalDate.of(2022, 2, 1),
                null,
                LocalTime.of(11,0));
        assertEquals(ScheduleValidity.不当_構成要素の問題, schedule.validity());
    }

    @Test
    void 終了時刻が未入力の場合は構成要素の問題として不当と判定される() throws Exception {
        Schedule schedule = Schedule.of(1,
                LocalDate.of(2022, 2, 1),
                LocalTime.of(10,0),
                null);
        assertEquals(ScheduleValidity.不当_構成要素の問題, schedule.validity());
    }

    @Test
    void 開始時刻が終了時刻より後の場合は構成要素の問題として不当と判定される() throws Exception {
        Schedule schedule = Schedule.of(1,
                LocalDate.of(2022, 2, 1),
                LocalTime.of(10,0),
                LocalTime.of(9,59));
        assertEquals(ScheduleValidity.不当_構成要素の問題, schedule.validity());
    }

    @Test
    void 開始時刻が終了時刻と同時刻の場合は構成要素の問題として不当と判定される() throws Exception {
        Schedule schedule = Schedule.of(1,
                LocalDate.of(2022, 2, 1),
                LocalTime.of(10,0),
                LocalTime.of(10,0));
        assertEquals(ScheduleValidity.不当_構成要素の問題, schedule.validity());
    }
}
