package meeting.domain.model.meeting;

/**
 * ミーティングの編集可否
 */
public enum MeetingEditability {
    TRUE("meeting-show__edit-button__display-block"),
    FALSE("meeting-show__edit-button__display-none");

    String editOperableItemDisplayStatus;

    MeetingEditability(String editOperableItemDisplayStatus) {
        this.editOperableItemDisplayStatus = editOperableItemDisplayStatus;
    }

    public String editOperableItemDisplayStatus() {
        return editOperableItemDisplayStatus;
    }
}
