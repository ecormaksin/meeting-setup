package example.domain.model.participant.attendance;

/**
 * 出欠種別
 */
public enum AttendanceCategory {
    マル("○"),
    サンカク("△"),
    バツ("×");

    String 記号;

    AttendanceCategory(String 記号) {
        this.記号 = 記号;
    }

    public String symbol() {
        return 記号;
    }
}
