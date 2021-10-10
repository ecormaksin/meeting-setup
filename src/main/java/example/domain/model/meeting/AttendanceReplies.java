package example.domain.model.meeting;

import java.util.Iterator;
import java.util.List;

public class AttendanceReplies {
    List<AttendanceReply> list;

    AttendanceReplies(List<AttendanceReply> list) {
        this.list = list;
    }

    @Deprecated(since = "MyBatis")
    public AttendanceReplies() {
    }

    public static AttendanceReplies of(List<AttendanceReply> source) {
        return new AttendanceReplies(source);
    }

    public Iterator<AttendanceReply> list() {
        return list.iterator();
    }
}
