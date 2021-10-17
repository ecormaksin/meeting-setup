package example.domain.model.schedule;

import java.util.Iterator;
import java.util.List;

/**
 * スケジュールリスト
 */
public class ScheduleList {
    List<Schedule> list;

    ScheduleList(List<Schedule> list) {
        this.list = list;
    }

    @Deprecated(since = "MyBatis")
    public ScheduleList() {
    }

    public static ScheduleList of(List<Schedule> source) {
        return new ScheduleList(source);
    }

    public Iterator<Schedule> list() {
        return list.iterator();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "ScheduleList{" +
                "list=" + list +
                '}';
        // @formatter:on
    }
}
