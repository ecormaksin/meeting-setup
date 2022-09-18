package meeting.domain.model.participant;

/**
 * 参加者番号の重複状態
 */
public enum ParticipantNumberDuplicationStatus {
    なし(""),
    あり("すでに出欠を入力しているので編集してください。");

    String errorMessage;

    ParticipantNumberDuplicationStatus(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String errorMessage() {
        return errorMessage;
    }
}
