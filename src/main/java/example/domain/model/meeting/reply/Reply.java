package example.domain.model.meeting.reply;

/**
 * 回答
 */
public class Reply {
    String 名前;
    String コメント;
    RepliesToProposedSchedules 候補日程別回答;

    @Override
    public String toString() {
        // @formatter:off
        return "Reply{" +
                "名前='" + 名前 + '\'' +
                ", コメント='" + コメント + '\'' +
                ", 候補日程別回答=" + 候補日程別回答 +
                '}';
        // @formatter:on
    }
}
