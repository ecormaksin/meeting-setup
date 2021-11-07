package example.domain.model.schedule;

import example.domain.model.schedule.construct.ScheduleConstruct;

import javax.validation.Valid;
import java.util.*;

/**
 * 候補日程リスト
 */
@SchedulesValid
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
        if (isEmpty()) {
            return false;
        }
        return list.stream().anyMatch(Schedule::isConstructNotNull);
    }

    public boolean isNotDuplicated() {
        if (!hasValidSchedule()) {
            return true;
        }

        Set<ScheduleConstruct> scheduleConstructSet = new HashSet<>();
        boolean isNotDuplicated = true;
        for (Schedule schedule : list) {
            ScheduleConstruct scheduleConstruct = schedule.scheduleConstruct();
            if (!scheduleConstruct.isValid()) {
                continue;
            }
            if (!scheduleConstructSet.add(scheduleConstruct)) {
                isNotDuplicated = false;
                break;
            }
        }

        return isNotDuplicated;
    }

    private boolean isEmpty() {
        if (Objects.isNull(list)) return true;
        if (list.isEmpty()) return true;
        return false;
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
