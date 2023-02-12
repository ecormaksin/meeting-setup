package meeting.presentation.meeting;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.MeetingShowScenario;
import meeting.domain.model.attendance.Attendance;
import meeting.domain.model.attendance.AttendanceNumberTestHelper;
import meeting.domain.model.attendance.Attendances;
import meeting.domain.model.attendance.convenience.Convenience;
import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.domain.model.attendance.convenience.Conveniences;
import meeting.domain.model.meeting.*;
import meeting.domain.model.organizer.OrganizerNumber;
import meeting.domain.model.organizer.OrganizerNumberTestHelper;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumberTestHelper;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.*;
import meeting.presentation.meeting.page_objects.ShowPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MeetingShowControllerTest extends MeetingIntegrationTest {

    Logger logger = Logger.getLogger(MeetingShowControllerTest.class.getName());

    @Autowired
    WebDriver driver;

    @MockBean
    MeetingShowScenario meetingShowScenario;

    MeetingNumber meetingNumber;
    Meeting meeting;

    private Meeting targetMeeting(MeetingNumber meetingNumber, Attendances attendances) {

        Subject subject = Subject.of("件名テスト1");
        Memo memo = Memo.blank();
        OrganizerNumber organizerNumber = OrganizerNumberTestHelper.fromInt(1);
        Schedules schedules = Schedules.of(Arrays.asList(
                Schedule.of(ScheduleNumber.of(1),
                        ScheduleConstruct.of(MeetingDate.of(2022, 4, 1), StartHour.of(10), StartMinute.JUST, Duration.OF_1_HOUR)),
                Schedule.of(ScheduleNumber.of(2),
                        ScheduleConstruct.of(MeetingDate.of(2022, 4, 2), StartHour.of(10), StartMinute.JUST, Duration.OF_1_HOUR)),
                Schedule.of(ScheduleNumber.of(3),
                        ScheduleConstruct.of(MeetingDate.of(2022, 4, 3), StartHour.of(10), StartMinute.JUST, Duration.OF_1_HOUR))
        ));

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
    }

    @Test
    void _最も都合の良い開催日時の背景色が強調されていること() throws Exception {
        Attendances attendances = Attendances.of(Arrays.asList(
                Attendance.of(
                        AttendanceNumberTestHelper.fromInt(1),
                        ParticipantNumberTestHelper.fromInt(1),
                        ParticipantName.of("参加者名テスト1"),
                        Conveniences.of(Arrays.asList(
                                Convenience.of(ScheduleNumber.of(1), ConvenienceCategory.GOOD),
                                Convenience.of(ScheduleNumber.of(2), ConvenienceCategory.BAD),
                                Convenience.of(ScheduleNumber.of(3), ConvenienceCategory.GOOD)
                        ))
                ),
                Attendance.of(
                        AttendanceNumberTestHelper.fromInt(2),
                        ParticipantNumberTestHelper.fromInt(2),
                        ParticipantName.of("参加者名テスト2"),
                        Conveniences.of(Arrays.asList(
                                Convenience.of(ScheduleNumber.of(1), ConvenienceCategory.GOOD),
                                Convenience.of(ScheduleNumber.of(2), ConvenienceCategory.MODEST),
                                Convenience.of(ScheduleNumber.of(3), ConvenienceCategory.BAD)
                        ))
                )
        ));

        Meeting meeting = targetMeeting(meetingNumber, attendances);
        when(meetingShowScenario.meetingOf(any(MeetingNumber.class))).thenReturn(meeting);
        ShowPage showPage = ShowPage.to(driver, meetingNumber);

        assertTrue(showPage.isBackgroundColorEmphasizedAt(0));
        assertFalse(showPage.isBackgroundColorEmphasizedAt(1));
        assertFalse(showPage.isBackgroundColorEmphasizedAt(2));
    }

    @Test
    void _複数の開催日時の都合が良い場合は該当箇所の背景色が強調されていること() throws Exception {
        Attendances attendances = Attendances.of(Arrays.asList(
                Attendance.of(
                        AttendanceNumberTestHelper.fromInt(1),
                        ParticipantNumberTestHelper.fromInt(1),
                        ParticipantName.of("参加者名テスト1"),
                        Conveniences.of(Arrays.asList(
                                Convenience.of(ScheduleNumber.of(1), ConvenienceCategory.GOOD),
                                Convenience.of(ScheduleNumber.of(2), ConvenienceCategory.BAD),
                                Convenience.of(ScheduleNumber.of(3), ConvenienceCategory.GOOD)
                        ))
                ),
                Attendance.of(
                        AttendanceNumberTestHelper.fromInt(2),
                        ParticipantNumberTestHelper.fromInt(2),
                        ParticipantName.of("参加者名テスト2"),
                        Conveniences.of(Arrays.asList(
                                Convenience.of(ScheduleNumber.of(1), ConvenienceCategory.GOOD),
                                Convenience.of(ScheduleNumber.of(2), ConvenienceCategory.BAD),
                                Convenience.of(ScheduleNumber.of(3), ConvenienceCategory.BAD)
                        ))
                ),
                Attendance.of(
                        AttendanceNumberTestHelper.fromInt(3),
                        ParticipantNumberTestHelper.fromInt(3),
                        ParticipantName.of("参加者名テスト3"),
                        Conveniences.of(Arrays.asList(
                                Convenience.of(ScheduleNumber.of(1), ConvenienceCategory.BAD),
                                Convenience.of(ScheduleNumber.of(2), ConvenienceCategory.BAD),
                                Convenience.of(ScheduleNumber.of(3), ConvenienceCategory.GOOD)
                        ))
                )
        ));

        Meeting meeting = targetMeeting(meetingNumber, attendances);
        when(meetingShowScenario.meetingOf(any(MeetingNumber.class))).thenReturn(meeting);
        ShowPage showPage = ShowPage.to(driver, meetingNumber);

        assertTrue(showPage.isBackgroundColorEmphasizedAt(0));
        assertFalse(showPage.isBackgroundColorEmphasizedAt(1));
        assertTrue(showPage.isBackgroundColorEmphasizedAt(2));
    }

    void _1名しか都合が良いと回答していない場合は背景色が強調されていないこと() throws Exception {
        Attendances attendances = Attendances.of(Arrays.asList(
                Attendance.of(
                        AttendanceNumberTestHelper.fromInt(1),
                        ParticipantNumberTestHelper.fromInt(1),
                        ParticipantName.of("参加者名テスト1"),
                        Conveniences.of(Arrays.asList(
                                Convenience.of(ScheduleNumber.of(1), ConvenienceCategory.GOOD),
                                Convenience.of(ScheduleNumber.of(2), ConvenienceCategory.BAD),
                                Convenience.of(ScheduleNumber.of(3), ConvenienceCategory.BAD)
                        ))
                ),
                Attendance.of(
                        AttendanceNumberTestHelper.fromInt(2),
                        ParticipantNumberTestHelper.fromInt(2),
                        ParticipantName.of("参加者名テスト2"),
                        Conveniences.of(Arrays.asList(
                                Convenience.of(ScheduleNumber.of(1), ConvenienceCategory.BAD),
                                Convenience.of(ScheduleNumber.of(2), ConvenienceCategory.GOOD),
                                Convenience.of(ScheduleNumber.of(3), ConvenienceCategory.BAD)
                        ))
                ),
                Attendance.of(
                        AttendanceNumberTestHelper.fromInt(3),
                        ParticipantNumberTestHelper.fromInt(3),
                        ParticipantName.of("参加者名テスト3"),
                        Conveniences.of(Arrays.asList(
                                Convenience.of(ScheduleNumber.of(1), ConvenienceCategory.BAD),
                                Convenience.of(ScheduleNumber.of(2), ConvenienceCategory.BAD),
                                Convenience.of(ScheduleNumber.of(3), ConvenienceCategory.GOOD)
                        ))
                )
        ));

        Meeting meeting = targetMeeting(meetingNumber, attendances);
        when(meetingShowScenario.meetingOf(any(MeetingNumber.class))).thenReturn(meeting);
        ShowPage showPage = ShowPage.to(driver, meetingNumber);

        assertFalse(showPage.isBackgroundColorEmphasizedAt(0));
        assertFalse(showPage.isBackgroundColorEmphasizedAt(1));
        assertFalse(showPage.isBackgroundColorEmphasizedAt(2));
    }

    @AfterEach
    void tearDown() {
        if (driver == null) return;
        driver.quit();
    }
}
