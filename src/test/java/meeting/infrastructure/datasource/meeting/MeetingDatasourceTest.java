package meeting.infrastructure.datasource.meeting;

import meeting.MeetingDBTest;
import meeting.domain.model.meeting.Meeting;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.ScheduleNumber;
import meeting.domain.model.schedule.Schedules;
import meeting.domain.model.schedule.construct.*;
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
import java.time.LocalDateTime;
import java.util.Arrays;
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

    LocalDateTime currentDateTime = LocalDateTime.now();
    LocalDate currentDate = currentDateTime.toLocalDate();

    ScheduleConstruct firstScheduleConstruct = new ScheduleConstruct(
            new MeetingDate(currentDate),
            new StartHour(10),
            StartMinute.JUST,
            Duration.ONE_HOUR);
    LocalDateTime firstScheduleStart = LocalDateTime.of(
            currentDateTime.getYear(),
            currentDateTime.getMonth(),
            currentDateTime.getDayOfMonth(),
            10,
            0
            ).plusDays(1);
    LocalDateTime firstScheduleEnd = firstScheduleStart.plusHours(1);
    Schedule firstSchedule = new Schedule(ScheduleNumber.initialize(), firstScheduleConstruct);

    LocalDateTime secondScheduleStart = firstScheduleStart
            .plusDays(1)
            .plusHours(1);
    LocalDateTime secondScheduleEnd = secondScheduleStart.plusHours(1);

    ScheduleConstruct secondScheduleConstruct = new ScheduleConstruct(
            new MeetingDate(currentDate.plusDays(1)),
            new StartHour(11),
            StartMinute.JUST,
            Duration.ONE_HOUR);
    Schedule secondSchedule = new Schedule(ScheduleNumber.initialize(), firstScheduleConstruct);

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
        List<Schedule> scheduleList = Arrays.asList(firstSchedule, secondSchedule);
        return schedulesParam(scheduleList);
    }

    private MultiValueMap<String, String> reverseOrderedSchedulesParam() {
        List<Schedule> scheduleList = Arrays.asList(secondSchedule, firstSchedule);
        return schedulesParam(scheduleList);
    }

    private MultiValueMap<String, String> schedulesParam(List<Schedule> scheduleList) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);
            params.add("schedules.list[" + i + "].scheduleConstruct.meetingDate.value", schedule.meetingDate().toString());
            params.add("schedules.list[" + i + "].scheduleConstruct.startTime.value", schedule.startTime().toString());
            params.add("schedules.list[" + i + "].scheduleConstruct.endTime.value", schedule.endTime().toString());
        }

        return params;
    }

    private MultiValueMap<String, String> organizerTerminalParam() {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("organizerTerminal.ipAddress.value", "127.0.0.1");
        param.add("organizerTerminal.osFamily.value", "Windows");
        param.add("organizerTerminal.userAgentFamily.value", "Chrome");
        return param;
    }

    private MeetingNumber createdMeetingNumber(MvcResult result) {
        FlashMap flashMap = result.getFlashMap();
        String meetingNumberValue = (String) flashMap.get("meetingNumber");
        return new MeetingNumber(meetingNumberValue);
    }

    private Meeting findMeeting(MvcResult result) {
        MeetingNumber meetingNumber = createdMeetingNumber(result);
        return meetingDatasource.meetingOf(meetingNumber);
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

        Schedule firstSchedule() {
            return scheduleList().get(0);
        }

        Schedule secondSchedule() {
            return scheduleList().get(1);
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
            assertNotNull(firstSchedule());
        }

        @Test
        void 作成時の日程候補1件目に日程番号が発番されている() throws Exception {
            assertTrue(firstSchedule().scheduleNumber().value() > 0);
        }

        @Test
        void 日程候補1件目の開催日が作成時と同じ() throws Exception {
            assertEquals(firstScheduleStart.toLocalDate(), firstSchedule().meetingDate().value());
        }

        @Test
        void 日程候補1件目の開始時刻が作成時と同じ() throws Exception {
            assertEquals(firstScheduleStart.toLocalTime(), firstSchedule().startTime().value() );
        }

        @Test
        void 日程候補1件目の終了時刻が作成時と同じ() throws Exception {
            assertEquals(firstScheduleEnd.toLocalTime(), firstSchedule().endTime().value() );
        }

        @Test
        void 作成時の日程候補2件目を取得できる() throws Exception {
            assertNotNull(secondSchedule());
        }

        @Test
        void 作成時の日程候補2件目に日程番号が発番されている() throws Exception {
            assertTrue(secondSchedule().scheduleNumber().value() > 0);
        }

        @Test
        void 日程候補2件目の開催日が作成時と同じ() throws Exception {
            assertEquals(secondScheduleStart.toLocalDate(), secondSchedule().meetingDate().value());
        }

        @Test
        void 日程候補2件目の開始時刻が作成時と同じ() throws Exception {
            assertEquals(secondScheduleStart.toLocalTime(), secondSchedule().startTime().value() );
        }

        @Test
        void 日程候補2件目の終了時刻が作成時と同じ() throws Exception {
            assertEquals(secondScheduleEnd.toLocalTime(), secondSchedule().endTime().value() );
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

        Schedule firstSchedule() {
            return scheduleList().get(0);
        }

        Schedule secondSchedule() {
            return scheduleList().get(1);
        }

        @Test
        void 作成時の日程候補2件目の開催日が1件目として取得できる() throws Exception {
            assertEquals(firstScheduleStart.toLocalDate(), firstSchedule().meetingDate().value());
        }

        @Test
        void 作成時の日程候補2件目の開始時刻が1件目として取得できる() throws Exception {
            assertEquals(firstScheduleStart.toLocalTime(), firstSchedule().startTime().value());
        }

        @Test
        void 作成時の日程候補2件目の終了時刻が1件目として取得できる() throws Exception {
            assertEquals(firstScheduleEnd.toLocalTime(), firstSchedule().endTime().value());
        }

        @Test
        void 作成時の日程候補1件目の開催日が2件目として取得できる() throws Exception {
            assertEquals(secondScheduleStart.toLocalDate(), secondSchedule().meetingDate().value());
        }

        @Test
        void 作成時の日程候補1件目の開始時刻が2件目として取得できる() throws Exception {
            assertEquals(secondScheduleStart.toLocalTime(), secondSchedule().startTime().value());
        }

        @Test
        void 作成時の日程候補1件目の終了時刻が2件目として取得できる() throws Exception {
            assertEquals(secondScheduleEnd.toLocalTime(), secondSchedule().endTime().value());
        }
    }
}
