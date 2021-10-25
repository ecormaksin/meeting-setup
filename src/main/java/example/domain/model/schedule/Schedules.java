package example.domain.model.schedule;

import java.util.Iterator;
import java.util.List;

/**
 * 候補日程リスト
 */
public class Schedules {
    List<Schedule> list;

    public Schedules(List<Schedule> list) {
        this.list = list;
    }

    @Deprecated
    Schedules() {
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
