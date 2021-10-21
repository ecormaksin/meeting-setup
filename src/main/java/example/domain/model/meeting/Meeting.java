package example.domain.model.meeting;

import example.domain.model.participant.Participants;

/**
 * ミーティング
 */
public class Meeting {
    MeetingNumber ミーティング番号;
    String 件名;
    String メモ;
    ProposedSchedules 候補日程セット;
    Participants 参加者セット;

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
                ", 候補日程セット=" + 候補日程セット +
                ", 参加者セット=" + 参加者セット +
                '}';
        // @formatter:on
    }
}
