package example.domain.model.meeting.reply;

/**
 * 回答種別
 */
public enum ReplyCategory {
    マル("○"),
    サンカク("△"),
    バツ("×");

    String 記号;

    ReplyCategory(String 記号) {
        this.記号 = 記号;
    }

    public String 記号() {
        return 記号;
    }
}
