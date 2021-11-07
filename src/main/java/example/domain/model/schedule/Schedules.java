package example.domain.model.schedule;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 候補日程リスト
 */
@SchedulesHasAtLeastOne
public class Schedules {
    private static final int INITIAL_EMPTY_SCHEDULES_SIZE = 3;

    List<@Valid Schedule> list;

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
        return Collections.unmodifiableList(list);
    }

    public Schedules addNew() {
        List<Schedule> newList = new ArrayList<>(list);
        newList.add(new Schedule());
        return of(newList);
    }

    public Schedules remove(int index) {
        List<Schedule> newList = new ArrayList<>(list);
        newList.remove(index);
        return of(newList);
    }

    public boolean hasValidSchedule() {

        if (Objects.isNull(list)) return false;
        if (list.isEmpty()) return false;

        return list.stream()
                .anyMatch(schedule -> schedule.areAllElementsNotNull());
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
