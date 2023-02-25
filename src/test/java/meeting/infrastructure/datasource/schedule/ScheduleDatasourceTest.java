package meeting.infrastructure.datasource.schedule;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.MeetingNumberTestHelper;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.*;
import meeting.infrastructure.datasource.DBUnitWithoutWebTest;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.junit.jupiter.Container;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScheduleDatasourceTest extends DBUnitWithoutWebTest {

    @Container
    private static final MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Autowired
    ScheduleDatasource scheduleDatasource;

    @Test
    @DatabaseSetup("testdata/schedule_for_list.xlsx")
    void _開催日時と会議時間の昇順で会議日程を取得できる() {
        MeetingNumber meetingNumber = MeetingNumberTestHelper.fromInt(1);

        Schedules actual = scheduleDatasource.findByMeeingNumber(meetingNumber);

        assertEquals(actual.asList(), Arrays.asList(
                Schedule.of(ScheduleNumber.of(5),
                        ScheduleConstruct.of(MeetingDate.of("2022-04-01"), StartHour.of(10), StartMinute.JUST, Duration.OF_1_HOUR)),
                Schedule.of(ScheduleNumber.of(4),
                        ScheduleConstruct.of(MeetingDate.of("2022-04-01"), StartHour.of(10), StartMinute.JUST, Duration.OF_1_HOUR_30_MINUTES)),
                Schedule.of(ScheduleNumber.of(3),
                        ScheduleConstruct.of(MeetingDate.of("2022-04-01"), StartHour.of(11), StartMinute.JUST, Duration.OF_1_HOUR)),
                Schedule.of(ScheduleNumber.of(2),
                        ScheduleConstruct.of(MeetingDate.of("2022-04-01"), StartHour.of(11), StartMinute.HALF, Duration.OF_1_HOUR)),
                Schedule.of(ScheduleNumber.of(1),
                        ScheduleConstruct.of(MeetingDate.of("2022-04-02"), StartHour.of(11), StartMinute.JUST, Duration.OF_1_HOUR))
        ));
    }

    @Test
    void _会議日程を取得できなかった場合は例外が発生する() {
        MeetingNumber meetingNumber = MeetingNumberTestHelper.fromInt(1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> scheduleDatasource.findByMeeingNumber(meetingNumber));

        assertEquals(exception.getMessage(), "ミーティングの日程候補が見つかりませんでした。主催者に次のミーティング番号を確認してください。: mt000000000000000000000001");
    }
}
