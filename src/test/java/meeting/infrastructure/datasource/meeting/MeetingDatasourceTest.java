package meeting.infrastructure.datasource.meeting;

import meeting.MeetingDBTest;
import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.Attendances;
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

import static org.junit.jupiter.api.Assertions.*;

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
        final MeetingNumber requestMeetingNumber = request.meetingNumber();
        final Meeting meeting = meetingDatasource.findBy(requestMeetingNumber);

        // Assert
        assertNotNull(meeting);

        final MeetingNumber actualMeetingNumber = meeting.meetingNumber();
        assertEquals("01g4asj3gvmxr9bszn5mdcj7ts", actualMeetingNumber.value());

        final Subject subject = meeting.subject();
        assertEquals("件名テスト", subject.value());

        final Memo memo = meeting.memo();
        assertEquals("ミーティング メモ テスト", memo.value());

        final OrganizerNumber organizerNumber = meeting.organizerNumber();
        assertEquals("01g4aspv7rx0fd5mv5wvfygt1t", organizerNumber.value());

        final Schedules schedules = meeting.schedules();

        final List<Schedule> scheduleList = schedules.asList();
        assertEquals(1, scheduleList.size());

        final Schedule schedule = scheduleList.get(0);

        final ScheduleNumber scheduleNumber = schedule.scheduleNumber();
        assertTrue(scheduleNumber.value() > 0);

        final ScheduleConstruct scheduleConstruct = schedule.scheduleConstruct();

        final MeetingDate meetingDate = scheduleConstruct.meetingDate();
        assertEquals(LocalDate.of(2022, 1, 1), meetingDate.value());

        final StartHour startHour = scheduleConstruct.startHour();
        assertEquals(10, startHour.value());

        final StartMinute startMinute = scheduleConstruct.startMinute();
        assertEquals(0, startMinute.value());

        final Duration duration = scheduleConstruct.duration();
        assertEquals(60, duration.value());

        final Attendances attendances = meeting.attendances();
        final List<Attendance> attendanceList = attendances.asList();
        assertEquals(0, attendanceList.size());
    }

    private MeetingCreationRequest creationRequest() {

        final MeetingNumber meetingNumber = new MeetingNumber("01g4asj3gvmxr9bszn5mdcj7ts");
        final Subject subject = new Subject("件名テスト");
        final Memo memo = new Memo("ミーティング メモ テスト");
        final OrganizerNumber organizerNumber = new OrganizerNumber("01g4aspv7rx0fd5mv5wvfygt1t");
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
        final StartMinute startMinute = StartMinute.of(0);
        final Duration duration = Duration.of(60);

        return new ScheduleConstruct(meetingDate, startHour, startMinute, duration);
    }
}
