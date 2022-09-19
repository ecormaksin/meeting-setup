package meeting.domain.model.attendance;

/**
 * 出欠の編集可否
 */
public enum AttendanceEditability {

    編集可能("participant-name__link"),
    編集不可("participant-name__link-disabled");

    String operableItemStatus;

    AttendanceEditability(String operableItemStatus) {
        this.operableItemStatus = operableItemStatus;
    }

    public String operableItemStatus() {
        return operableItemStatus;
    }
}
