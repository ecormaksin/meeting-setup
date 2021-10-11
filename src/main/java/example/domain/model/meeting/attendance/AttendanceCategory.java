package example.domain.model.meeting.attendance;

/**
 * 回答種別
 */
public enum AttendanceCategory {
    マル("○"),
    サンカク("△"),
    バツ("×");

    String 記号;

    AttendanceCategory(String 記号) {
        this.記号 = 記号;
    }

    public String 記号() {
        return 記号;
    }
}
