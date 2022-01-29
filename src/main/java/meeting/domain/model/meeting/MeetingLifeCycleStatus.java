package meeting.domain.model.meeting;

/**
 * ミーティングのエンティティとしてのライフサイクル上の状態
 */
public enum MeetingLifeCycleStatus {
    CREATION("作成"),
    EDITION("編集");

    String text;

    MeetingLifeCycleStatus(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }
}
