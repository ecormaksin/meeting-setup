package meeting.infrastructure.datasource.meeting;

import meeting.MeetingDBTest;
import meeting.domain.model.meeting.*;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.*;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@MeetingDBTest
class MeetingDatasourceTest {

    @ClassRule
    static MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();
    @Autowired
    MeetingDatasource meetingDatasource;

    @Test
    void _メモあり_日程1件の場合() throws Exception {

        // Arrange
        final MeetingCreationRequest request = creationRequest();

        // Act
        meetingDatasource.create(request);
    }

    private MeetingCreationRequest creationRequest() {

        final MeetingNumber meetingNumber = MeetingNumber.newNumber();
        final Subject subject = new Subject("件名テスト");
        final Memo memo = new Memo("ミーティング メモ テスト");
        final OrganizerNumber organizerNumber = OrganizerNumber.newNumber();
        final Schedules schedules = schedules();
        final RegistrationRequestDateTime requestDateTime
                = new RegistrationRequestDateTime(
                        LocalDateTime.of(2022, 3, 1, 10, 0, 0));

        return new MeetingCreationRequest(
                meetingNumber,
                subject,
                memo,
                organizerNumber,
                schedules,
                requestDateTime);
    }

    private Schedules schedules() {
        final List<Schedule> list = Arrays.asList(schedule());
        return Schedules.of(list);
    }

    private Schedule schedule() {
        return new Schedule(ScheduleNumber.initialize(), scheduleConstruct());
    }

    private ScheduleConstruct scheduleConstruct() {
        final MeetingDate meetingDate = new MeetingDate(LocalDate.of(2022, 1, 1));
        final StartHour startHour = new StartHour(10);
        final StartMinute startMinute = StartMinute.JUST;
        final Duration duration = Duration.ONE_HOUR;

        return new ScheduleConstruct(meetingDate, startHour, startMinute, duration);
    }
}
