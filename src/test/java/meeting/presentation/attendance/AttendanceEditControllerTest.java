package meeting.presentation.attendance;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.AttendanceEditScenario;
import meeting.application.senario.MeetingShowScenario;
import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceNumber;
import meeting.domain.model.attendance.AttendanceNumberTestHelper;
import meeting.domain.model.attendance.Attendances;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.meeting.*;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.organizer.OrganizerNumberFake_2;
import meeting.domain.model.organizer.OrganizerNumberTestHelper;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.domain.model.participant.ParticipantNumberFake_1;
import meeting.domain.model.participant.ParticipantNumberTestHelper;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.*;
import meeting.presentation.TopPage;
import meeting.presentation.attendance.page_objects.EditFormPage;
import meeting.presentation.meeting.page_objects.ShowPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AttendanceEditControllerTest extends MeetingIntegrationTest {

    Logger logger = Logger.getLogger(AttendanceEditControllerTest.class.getName());

    @Autowired
    WebDriver driver;

    @MockBean
    MeetingShowScenario meetingShowScenario;

    @MockBean
    AttendanceEditScenario attendanceEditScenario;

    ShowPage showPage;

    EditFormPage formPage;

    MeetingNumber meetingNumber;
    Meeting meeting;
    Schedules schedules = Schedules.of(Arrays.asList(
            Schedule.of(ScheduleNumber.of(1),
                    ScheduleConstruct.of(MeetingDate.of(2022, 4, 1), StartHour.of(10), StartMinute.JUST, Duration.OF_1_HOUR)),
            Schedule.of(ScheduleNumber.of(2),
                    ScheduleConstruct.of(MeetingDate.of(2022, 4, 2), StartHour.of(11), StartMinute.HALF, Duration.OF_1_HOUR_30_MINUTES))
    ));

    Attendance attendance = Attendance.of(
            AttendanceNumberTestHelper.fromInt(1),
            ParticipantNumberTestHelper.fromInt(1),
            ParticipantName.of("参加者名テスト1"),
            Conveniences.of(Arrays.asList(
                    Convenience.of(ScheduleNumber.of(1), ConvenienceCategory.GOOD),
                    Convenience.of(ScheduleNumber.of(2), ConvenienceCategory.MODEST)
            )));

    Attendances attendances = Attendances.of(Arrays.asList(attendance));

    OrganizerNumber organizerNumber;
    ParticipantNumber participantNumber;

    private Meeting showBaseMeeting(MeetingNumber meetingNumber) {

        Subject subject = Subject.of("件名テスト1");
        Memo memo = Memo.blank();
        OrganizerNumber organizerNumber = OrganizerNumberTestHelper.fromInt(1);

        return Meeting.of(
                meetingNumber,
                subject,
                memo,
                organizerNumber,
                schedules,
                attendances
        );
    }

    @BeforeEach
    void setup() throws Exception {
        meetingNumber = MeetingNumberTestHelper.fromInt(1);
        meeting = showBaseMeeting(meetingNumber);

        new OrganizerNumberFake_2();
        organizerNumber = OrganizerNumberFake_2.newNumber();

        new ParticipantNumberFake_1();
        participantNumber = ParticipantNumberFake_1.newNumber();

        TopPage.to(driver);

        when(meetingShowScenario.meetingOf(any(MeetingNumber.class))).thenReturn(meeting);
        showPage = ShowPage.to(driver, meetingNumber);
        showPage.setOrganizerNumber(driver, organizerNumber);
        showPage.setParticipantNumber(driver, participantNumber);

        when(attendanceEditScenario.schedules(any(MeetingNumber.class))).thenReturn(schedules);
        when(attendanceEditScenario.attendanceOf(any(AttendanceNumber.class))).thenReturn(attendance);

        formPage = showPage.moveToAttendanceEditPage(1, EditFormPage.class);
    }

    @Test
    void test() {
        logger.info(driver.getCurrentUrl());
        logger.info(driver.getPageSource());

        assertNotNull(formPage);
    }
}
