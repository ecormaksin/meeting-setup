package meeting.domain.model.schedule;

import meeting.domain.model.schedule.construct.EndTime;
import meeting.domain.model.schedule.construct.MeetingDate;
import meeting.domain.model.schedule.construct.StartTime;

/**
 * 検索条件に該当する日程候補
 */
public class SearchedSchedule {

    MeetingDate meetingDate;
    StartTime startTime;
    EndTime endTime;

    @Deprecated
    SearchedSchedule() {}

    public SearchedSchedule(MeetingDate meetingDate,
                            StartTime startTime,
                            EndTime endTime) {
        this.meetingDate = meetingDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public MeetingDate meetingDate() {
        return meetingDate;
    }

    public StartTime startTime() {
        return startTime;
    }

    public EndTime endTime() {
        return endTime;
    }
}
