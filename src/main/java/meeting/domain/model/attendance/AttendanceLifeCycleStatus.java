package meeting.domain.model.attendance;

/**
 * 出欠のエンティティとしてのライフサイクル上の状態
 */
public enum AttendanceLifeCycleStatus {
    CREATION("作成"),
    EDITION("編集");

    String text;

    AttendanceLifeCycleStatus(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }
}
