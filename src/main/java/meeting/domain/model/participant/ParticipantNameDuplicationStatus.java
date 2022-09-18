package meeting.domain.model.participant;

/**
 * 参加者名の重複状態
 */
public enum ParticipantNameDuplicationStatus {
    なし(""),
    あり("参加者名は重複しないように入力してください。");

    String errorMessage;

    ParticipantNameDuplicationStatus(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String errorMessage() {
        return errorMessage;
    }
}
