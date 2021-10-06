package example.domain.model.meeting;

/**
 * ミーティング
 */
public class Meeting {
    MeetingNumber ミーティング番号;
    String 件名;
    String メモ;

    public String number() {
        return ミーティング番号.toString();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Meeting{" +
                "ミーティング番号=" + ミーティング番号 +
                ", 件名='" + 件名 + "'" +
                ", メモ='" + メモ + "'" +
                "}";
        // @formatter:on
    }
}
