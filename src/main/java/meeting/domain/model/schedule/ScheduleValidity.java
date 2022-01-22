package meeting.domain.model.schedule;

/**
 * 日程の妥当性
 */
public enum ScheduleValidity {
    妥当,
    妥当_未入力,
    不当_構成要素の問題,
    不当_追加時に過去;
}
