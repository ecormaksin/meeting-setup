package meeting.infrastructure.datasource.meeting;

import meeting.MeetingDBTest;
import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingId;
import meeting.domain.model.meeting.organizer.OrganizerTerminal;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.ScheduleConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.FlashMap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@MeetingDBTest
@AutoConfigureMockMvc
class MeetingDatasourceTest {
    @Autowired
    MeetingDatasource meetingDatasource;

    @Autowired
    MockMvc mockMvc;

    private MockHttpServletRequestBuilder postMeetingCreation() {
        return post("/meeting/create");
    }

    private MultiValueMap<String, String> subjectParam() {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("subject.value", "件名テスト");
        return param;
    }

    private MultiValueMap<String, String> memoParam() {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("memo.value", "メモテスト");
        return param;
    }

    private MultiValueMap<String, String> memoWithoutValueParam() {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("memo.value", "");
        return param;
    }

    private MultiValueMap<String, String> orderedSchedulesParam() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("schedules.list[0].scheduleConstruct.meetingDate.value", "2021-12-06");
        params.add("schedules.list[0].scheduleConstruct.startTime.value", "10:00");
        params.add("schedules.list[0].scheduleConstruct.endTime.value", "11:00");

        params.add("schedules.list[1].scheduleConstruct.meetingDate.value", "2021-12-07");
        params.add("schedules.list[1].scheduleConstruct.startTime.value", "14:00");
        params.add("schedules.list[1].scheduleConstruct.endTime.value", "15:00");

        return params;
    }

    private MultiValueMap<String, String> reverseOrderedSchedulesParam() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("schedules.list[0].scheduleConstruct.meetingDate.value", "2021-12-07");
        params.add("schedules.list[0].scheduleConstruct.startTime.value", "14:00");
        params.add("schedules.list[0].scheduleConstruct.endTime.value", "15:00");

        params.add("schedules.list[1].scheduleConstruct.meetingDate.value", "2021-12-06");
        params.add("schedules.list[1].scheduleConstruct.startTime.value", "10:00");
        params.add("schedules.list[1].scheduleConstruct.endTime.value", "11:00");

        return params;
    }

    private MultiValueMap<String, String> organizerTerminalParam() {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("organizerTerminal.ipAddress.value", "127.0.0.1");
        param.add("organizerTerminal.osFamily.value", "Windows");
        param.add("organizerTerminal.userAgentFamily.value", "Chrome");
        return param;
    }

    private MeetingId createdMeetingId(MvcResult result) {
        FlashMap flashMap = result.getFlashMap();
        String meetingIdValue = (String) flashMap.get("meetingId");
        return new MeetingId(meetingIdValue);
    }

    private Meeting findMeeting(MvcResult result) {
        MeetingId meetingId = createdMeetingId(result);
        return meetingDatasource.meetingOf(meetingId);
    }

    @Nested
    @MeetingDBTest
    @AutoConfigureMockMvc
    @DisplayName("メモと昇順の日程候補リストを持つ標準的なミーティングの場合")
    class MeetingWithMemoAndOrderedSchedules {

        @Autowired
        MockMvc mockMvc;

        Meeting meeting;

        @BeforeEach
        void setup()  throws Exception  {
            meeting = MeetingDatasourceTest.this.findMeeting(performPostRequest());
        }

        private MvcResult performPostRequest() throws Exception {
            return mockMvc.perform(
                    MeetingDatasourceTest.this.postMeetingCreation()
                            .params(MeetingDatasourceTest.this.subjectParam())
                            .params(MeetingDatasourceTest.this.memoParam())
                            .params(MeetingDatasourceTest.this.orderedSchedulesParam())
                            .params(MeetingDatasourceTest.this.organizerTerminalParam())
                    )
                    .andReturn();
        }

        @Test
        void ミーティングがNULLではない() throws Exception {
            assertNotNull(meeting);
        }

        @Test
        void 作成時の件名を取得できる() throws Exception {
            assertEquals("件名テスト", meeting.subject().value());
        }

        @Test
        void 作成時のメモを取得できる() throws Exception {
            assertEquals("メモテスト", meeting.memo().value());
        }

        @Test
        void 日程候補リストがNULLではない() throws Exception {
            assertNotNull(meeting.schedules());
        }

        List<Schedule> scheduleList() {
            Schedules schedules = meeting.schedules();
            return schedules.asList();
        }

        @Test
        void 日程候補リストのデータがNULLではない() throws Exception {
            assertNotNull(scheduleList());
        }

        @Test
        void 日程候補リストの件数が作成時と同じ() throws Exception {
            assertEquals(2, scheduleList().size());
        }

        @Test
        void 作成時の日程候補1件目を取得できる() throws Exception {
            Schedule schedule = scheduleList().get(0);
            assertNotNull(schedule);
            assertNotNull(schedule.scheduleNumber());
            ScheduleConstruct scheduleConstruct = schedule.scheduleConstruct();
            assertEquals(LocalDate.of(2021, 12, 6), scheduleConstruct.meetingDate().value());
            assertEquals(LocalTime.of(10, 0),scheduleConstruct.startTime().value() );
            assertEquals(LocalTime.of(11, 0),scheduleConstruct.endTime().value() );
        }

        @Test
        void 作成時の日程候補2件目を取得できる() throws Exception {
            Schedule schedule = scheduleList().get(1);
            assertNotNull(schedule);
            assertNotNull(schedule.scheduleNumber());
            ScheduleConstruct scheduleConstruct = schedule.scheduleConstruct();
            assertEquals(LocalDate.of(2021, 12, 7), scheduleConstruct.meetingDate().value());
            assertEquals(LocalTime.of(14, 0),scheduleConstruct.startTime().value() );
            assertEquals(LocalTime.of(15, 0),scheduleConstruct.endTime().value() );
        }

        private OrganizerTerminal organizerTerminal() {
            return meeting.organizerTerminal();
        }

        @Test
        void 主催者端末がNULLではない() throws Exception {
            assertNotNull(organizerTerminal());
        }

        @Test
        void 作成時のIPアドレスと同じ() throws Exception {
            OrganizerTerminal organizerTerminal = organizerTerminal();
            assertEquals("127.0.0.1", organizerTerminal.ipAddress().value());
        }

        @Test
        void 作成時のOSファミリーと同じ() throws Exception {
            OrganizerTerminal organizerTerminal = organizerTerminal();
            assertEquals("Windows", organizerTerminal.osFamily().value());
        }

        @Test
        void 作成時のブラウザと同じ() throws Exception {
            OrganizerTerminal organizerTerminal = organizerTerminal();
            assertEquals("Chrome", organizerTerminal.userAgentFamily().value());
        }
    }

    @Nested
    @MeetingDBTest
    @AutoConfigureMockMvc
    @DisplayName("メモがなく降順の日程候補リストを持つミーティングの場合")
    class MeetingWithoutMemoAndReverseOrderedSchedules {

        @Autowired
        MockMvc mockMvc;

        Meeting meeting;

        @BeforeEach
        void setup()  throws Exception  {
            meeting = MeetingDatasourceTest.this.findMeeting(performPostRequest());
        }

        private MvcResult performPostRequest() throws Exception {
            return mockMvc.perform(
                            MeetingDatasourceTest.this.postMeetingCreation()
                                    .params(MeetingDatasourceTest.this.subjectParam())
                                    .params(MeetingDatasourceTest.this.memoWithoutValueParam())
                                    .params(MeetingDatasourceTest.this.reverseOrderedSchedulesParam())
                                    .params(MeetingDatasourceTest.this.organizerTerminalParam())
                    )
                    .andReturn();
        }

        @Test
        void メモがNULLである() throws Exception {
            assertNull(meeting.memo());
        }

        List<Schedule> scheduleList() {
            Schedules schedules = meeting.schedules();
            return schedules.asList();
        }

        @Test
        void 作成時の日程候補1件目を取得できる() throws Exception {
            Schedule schedule = scheduleList().get(0);
            ScheduleConstruct scheduleConstruct = schedule.scheduleConstruct();
            assertEquals(LocalDate.of(2021, 12, 6), scheduleConstruct.meetingDate().value());
            assertEquals(LocalTime.of(10, 0),scheduleConstruct.startTime().value() );
            assertEquals(LocalTime.of(11, 0),scheduleConstruct.endTime().value() );
        }

        @Test
        void 作成時の日程候補2件目を取得できる() throws Exception {
            Schedule schedule = scheduleList().get(1);
            ScheduleConstruct scheduleConstruct = schedule.scheduleConstruct();
            assertEquals(LocalDate.of(2021, 12, 7), scheduleConstruct.meetingDate().value());
            assertEquals(LocalTime.of(14, 0),scheduleConstruct.startTime().value() );
            assertEquals(LocalTime.of(15, 0),scheduleConstruct.endTime().value() );
        }
    }
}
