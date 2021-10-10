package example.domain.model.meeting.reply;

/**
 * 回答
 */
public class Reply {
    String 名前;
    RepliesToProposedSchedules 候補日程別回答;
    String コメント;

    @Override
    public String toString() {
        // @formatter:off
        return "Reply{" +
                "名前='" + 名前 + '\'' +
                ", 候補日程別回答=" + 候補日程別回答 +
                ", コメント='" + コメント + '\'' +
                '}';
        // @formatter:on
    }
}
