package example.domain.model.meeting;

import example.domain.model.participant.Participants;
import example.domain.model.schedule.Schedules;

/**
 * ミーティング
 */
public class Meeting {
    MeetingNumber ミーティング番号;
    String 件名;
    String メモ;
    Schedules 候補日程リスト;
    Participants 参加者リスト;

    public String number() {
        return ミーティング番号.toString();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Meeting{" +
                "ミーティング番号=" + ミーティング番号 +
                ", 件名='" + 件名 + '\'' +
                ", メモ='" + メモ + '\'' +
                ", 候補日程リスト=" + 候補日程リスト +
                ", 参加者セット=" + 参加者リスト +
                '}';
        // @formatter:on
    }
}
