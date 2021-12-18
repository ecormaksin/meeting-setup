package meeting.domain.model.participant.attendance;

/**
 * 出欠種別
 */
public enum AttendanceCategory {
    マル("○", "attendance-category-maru"),
    サンカク("△", "attendance-category-sankaku"),
    バツ("×", "attendance-category-shikaku");

    String symbol;
    String displayStyle;

    public static AttendanceCategory of(String symbol) {
        for (AttendanceCategory attendanceCategory : AttendanceCategory.values()) {
            if (attendanceCategory.symbol().equals(symbol)) {
                return attendanceCategory;
            }
        }
        throw new IllegalArgumentException(
                String.format("指定された記号に該当する出欠種別はありません。記号を確認してください。（%s）", symbol)
        );
    }

    AttendanceCategory(String symbol, String displayStyle) {
        this.symbol = symbol;
        this.displayStyle = displayStyle;
    }

    public String symbol() {
        return symbol;
    }

    public String displayStyle() {
        return displayStyle;
    }
}
