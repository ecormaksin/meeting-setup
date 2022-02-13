package meeting.domain.model.meeting.search_condition;

public enum MeetingSearchConditionValidity {
    妥当,
    不当_検索開始日が未入力,
    不当_検索終了日が未入力,
    不当_検索開始日と検索終了日の前後関係が逆;
}
