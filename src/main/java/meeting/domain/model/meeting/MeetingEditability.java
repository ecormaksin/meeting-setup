package meeting.domain.model.meeting;

/**
 * ミーティングの編集可否
 */
public enum MeetingEditability {

    編集可能("meeting-show__edit-button__display-block", ""),
    編集不可_非主催者(MeetingEditability.DISPLAY_NONE_CLASS, "主催者ではないので編集・削除できません。"),
    編集不可_回答済(MeetingEditability.DISPLAY_NONE_CLASS, "回答されているので編集・削除できません。");

    static final String DISPLAY_NONE_CLASS = "meeting-show__edit-button__display-none";

    String operableItemStatus;
    String statusMessage;

    MeetingEditability(String operableItemStatus, String statusMessage) {
        this.operableItemStatus = operableItemStatus;
        this.statusMessage = statusMessage;
    }

    public String operableItemStatus() {
        return operableItemStatus;
    }

    public String statusMessage() {
        return statusMessage;
    }
}
