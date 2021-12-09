package meeting.infrastructure.datasource.meeting;

import meeting.MeetingDBTest;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.meeting.MeetingTest;
import meeting.domain.model.meeting.organizer.OrganizerTerminal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.FlashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@MeetingDBTest
@AutoConfigureMockMvc
class MeetingDatasourceTest {
    @Autowired
    MeetingDatasource meetingDatasource;

    @Autowired
    MockMvc mockMvc;

    @Test
    void ミーティングIDでミーティングを取得できる() throws Exception {
        MvcResult result = mockMvc.perform(
                        post("/meeting/create")
                                .param("subject.value", "件名テスト")
                                .param("memo.value", "メモテスト")
                                .param("schedules.list[0].scheduleConstruct.meetingDate.value", "2021-12-06")
                                .param("schedules.list[0].scheduleConstruct.startTime.value", "10:00")
                                .param("schedules.list[0].scheduleConstruct.endTime.value", "11:00")
                                .param("schedules.list[1].scheduleConstruct.meetingDate.value", "2021-12-07")
                                .param("schedules.list[1].scheduleConstruct.startTime.value", "14:00")
                                .param("schedules.list[1].scheduleConstruct.endTime.value", "15:00")
                                .param("organizerTerminal.ipAddress.value", "127.0.0.1")
                                .param("organizerTerminal.osFamily.value", "Windows")
                                .param("organizerTerminal.userAgentFamily.value", "Chrome")
                )
                .andReturn();
        FlashMap flashMap = result.getFlashMap();
        System.out.println(flashMap.toString());
        String meetingIdValue = (String) flashMap.get("meetingId");
        MeetingId createdMeetingId = new MeetingId(meetingIdValue);

        assertNotNull(createdMeetingId);

        MeetingTest meetingTest = meetingDatasource.findBy(createdMeetingId);
        assertNotNull(meetingTest);

        MeetingId foundMeetingId = meetingTest.meetingId();
        assertNotNull(foundMeetingId);
        assertEquals(createdMeetingId.value(), foundMeetingId.value());
        assertEquals("件名テスト", meetingTest.subject().value());
        assertEquals("メモテスト", meetingTest.memo().value());

        OrganizerTerminal organizerTerminal = meetingTest.organizerTerminal();
        assertEquals("127.0.0.1", organizerTerminal.ipAddress().value());
        assertEquals("Windows", organizerTerminal.osFamily().value());
        assertEquals("Chrome", organizerTerminal.userAgentFamily().value());
    }
}
