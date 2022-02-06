package meeting.domain.model.schedule.construct;

/**
 * 日程の構成要素の妥当性
 */
public enum ScheduleConstructValidity {
    妥当_すべて未入力,
    不当_一部未入力あり,
    不当_時刻の前後関係が不当,
    妥当_すべて入力済;
}
