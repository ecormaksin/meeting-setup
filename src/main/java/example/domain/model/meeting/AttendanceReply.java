package example.domain.model.meeting;

import example.domain.model.meeting.reply.Reply;

/**
 * 出欠回答
 */
public class AttendanceReply {
    AttendanceReplyNumber 出欠回答番号;
    Reply 回答;

    @Override
    public String toString() {
        // @formatter:off
        return "AttendanceReply{" +
                "出欠回答番号=" + 出欠回答番号 +
                ", 回答=" + 回答 +
                '}';
        // @formatter:on
    }
}
