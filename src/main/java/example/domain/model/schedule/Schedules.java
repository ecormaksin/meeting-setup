package example.domain.model.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 候補日程リスト
 */
public class Schedules {
    private static final int INITIAL_EMPTY_SCHEDULES_SIZE = 3;

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

    public static Schedules initialEmptyList() {
        List<Schedule> list = new ArrayList<>();
        for (int i = 0; i < INITIAL_EMPTY_SCHEDULES_SIZE; i++) {
            list.add(new Schedule());
        }
        return new Schedules(list);
    }

    public List<Schedule> asList() {
        return list;
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
