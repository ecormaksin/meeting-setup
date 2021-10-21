package example.domain.model.schedule;

import java.util.Iterator;
import java.util.List;

/**
 * スケジュールリスト
 */
public class Schedules {
    List<Schedule> list;

    Schedules(List<Schedule> list) {
        this.list = list;
    }

    @Deprecated(since = "MyBatis")
    public Schedules() {
    }

    public static Schedules of(List<Schedule> source) {
        return new Schedules(source);
    }

    public Iterator<Schedule> list() {
        return list.iterator();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Schedules{" +
                "list=" + list +
                '}';
        // @formatter:on
    }
}
