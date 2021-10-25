package example.domain.model.meeting;

import example.domain.model.participant.Participants;
import example.domain.model.schedule.Schedules;

/**
 * ミーティング
 */
public class Meeting {
    MeetingId meetingId;
    Subject subject;
    Memo memo;
    Schedules schedules;
    Participants participants;

    public String id() {
        return meetingId.value();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Meeting{" +
                "meetingId=" + meetingId +
                ", subject=" + subject +
                ", memo=" + memo +
                ", schedules=" + schedules +
                ", participants=" + participants +
                '}';
        // @formatter:on
    }
}
