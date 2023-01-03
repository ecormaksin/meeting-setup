package meeting.domain.model.attendance;

/**
 * 出欠入力の重複状態
 */
public enum AttendanceInputDuplicationStatus {
    なし(""),
    あり("すでに出欠を入力しているので編集してください。");

    String errorMessage;

    AttendanceInputDuplicationStatus(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String errorMessage() {
        return errorMessage;
    }
}
