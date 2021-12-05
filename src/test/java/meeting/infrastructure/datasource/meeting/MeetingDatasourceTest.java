package meeting.infrastructure.datasource.meeting;

import meeting.MeetingDBTest;
import meeting.domain.model.meeting.MeetingCreationRequest;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.meeting.Memo;
import meeting.domain.model.meeting.Subject;
import meeting.domain.model.meeting.organizer.OrganizerTerminal;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.Schedules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@MeetingDBTest
@AutoConfigureMockMvc
class MeetingDatasourceTest {
    MeetingCreationRequest meetingCreationRequest;
    Subject subject;
    Memo memo;
    Schedules schedules;
    OrganizerTerminal organizerTerminal;

    @Autowired
    MeetingDatasource meetingDatasource;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        subject = new Subject("件名テスト");
        memo = new Memo("メモテスト");
        schedules = Schedules.of(Arrays.asList(
                Schedule.of(1,
                        LocalDate.of(2021, 12, 6),
                        LocalTime.of(10,0),
                        LocalTime.of(11,0)),
                Schedule.of(2,
                        LocalDate.of(2021, 12, 7),
                        LocalTime.of(14,0),
                        LocalTime.of(15,0))
        ));
        organizerTerminal = OrganizerTerminal.of("127.0.0.1",
                "Windows",
                "Chrome");
        meetingCreationRequest = new MeetingCreationRequest(subject, memo, schedules, organizerTerminal);
    }

    @Test
    void ミーティングIDでミーティングを取得できる() throws Exception {
        // mockMvcでFlashAttributeからMeegingIdを取得する方法が分かっていないため、データ ソースを使ってミーティングを作成する。
//         MvcResult  result = mockMvc.perform(
//                post("/meeting/create")
//                        .param("subject.value", "件名テスト")
//                        .param("memo.value", "メモテスト")
//                        .param("schedules.list[0].scheduleConstruct.meetingDate.value", "2021-12-06")
//                        .param("schedules.list[0].scheduleConstruct.startTime.value", "10:00")
//                        .param("schedules.list[0].scheduleConstruct.endTime.value", "11:00")
//                        .param("schedules.list[1].scheduleConstruct.meetingDate.value", "2021-12-07")
//                        .param("schedules.list[1].scheduleConstruct.startTime.value", "14:00")
//                        .param("schedules.list[1].scheduleConstruct.endTime.value", "15:00")
//                        .param("organizerTerminal.ipAddress.value", "127.0.0.1")
//                        .param("organizerTerminal.osFamily.value", "Windows")
//                        .param("organizerTerminal.userAgentFamily.value", "Chrome")
//                )
//                .andDo(print()).andReturn();
        MeetingId meetingId = meetingDatasource.create(meetingCreationRequest);

        assertNotNull(meetingId);
    }
}
