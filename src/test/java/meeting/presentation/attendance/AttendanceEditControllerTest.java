package meeting.presentation.attendance;

import meeting.MeetingIntegrationTest;
import meeting.application.senario.AttendanceEditScenario;
import meeting.application.senario.MeetingShowScenario;
import meeting.domain.model.attendance.Attendance;
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
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.logging.Logger;

public class AttendanceEditControllerTest extends MeetingIntegrationTest {

    Logger logger = Logger.getLogger(AttendanceEditControllerTest.class.getName());

    @Autowired
    WebDriver driver;

    @MockBean
    MeetingShowScenario meetingShowScenario;

    @MockBean
    AttendanceEditScenario attendanceEditScenario;

    MeetingNumber meetingNumber;
    Meeting meeting;
    Schedules schedules = Schedules.of(Arrays.asList(
            Schedule.of(ScheduleNumber.of(1),
                    ScheduleConstruct.of(MeetingDate.of(2022, 4, 1), StartHour.of(10), StartMinute.JUST, Duration.OF_1_HOUR)),
            Schedule.of(ScheduleNumber.of(2),
                    ScheduleConstruct.of(MeetingDate.of(2022, 4, 2), StartHour.of(11), StartMinute.HALF, Duration.OF_1_HOUR_30_MINUTES))
    ));

    Attendances attendances = Attendances.of(Arrays.asList(
            Attendance.of(
                    AttendanceNumberTestHelper.fromInt(1),
                    ParticipantNumberTestHelper.fromInt(1),
                    ParticipantName.of("参加者名テスト1"),
                    Conveniences.of(Arrays.asList(
                            Convenience.of(ScheduleNumber.of(1), ConvenienceCategory.GOOD),
                            Convenience.of(ScheduleNumber.of(2), ConvenienceCategory.MODEST)
                    )))
    ));

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
                schedules
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
    }
}
