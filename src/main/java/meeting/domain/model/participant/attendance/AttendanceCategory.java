package meeting.domain.model.participant.attendance;

/**
 * 出欠種別
 */
public enum AttendanceCategory {
    マル("○", "attendance-category-maru"),
    サンカク("△", "attendance-category-sankaku"),
    バツ("×", "attendance-category-shikaku");

    String 記号;
    String 表示スタイル;

    AttendanceCategory(String 記号, String 表示スタイル) {
        this.記号 = 記号;
        this.表示スタイル = 表示スタイル;
    }

    public String symbol() {
        return 記号;
    }

    public String displayStyle() {
        return 表示スタイル;
    }
}
