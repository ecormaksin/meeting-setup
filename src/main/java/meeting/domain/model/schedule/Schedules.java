package meeting.domain.model.schedule;

import meeting.domain.model.schedule.construct.ScheduleConstruct;
import meeting.domain.model.schedule.construct.ScheduleConstructValidity;

import javax.validation.Valid;
import java.util.*;

/**
 * 日程候補リスト
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
            list.add(Schedule.empty());
        }
        return new Schedules(list);
    }

    public List<Schedule> asList() {
        return Collections.unmodifiableList(list);
    }

    public Schedules addNew() {
        List<Schedule> newList = new ArrayList<>(list);
        newList.add(Schedule.empty());
        return of(newList);
    }

    public Schedules remove(int index) {
        List<Schedule> newList = new ArrayList<>(list);
        newList.remove(index);
        return of(newList);
    }

    public SchedulesValidity validity() {
        if (isEmpty()) return SchedulesValidity.不当_日程候補なし;
        if (areDuplicated()) return SchedulesValidity.不当_日程重複;
        if (containAllValidSchedule()) return SchedulesValidity.妥当;
        return SchedulesValidity.不当_日程に不備あり;
    }

    private boolean containAllValidSchedule() {
        boolean anyValidInput = list.stream().anyMatch(s -> {
            return s.validity() == ScheduleValidity.妥当;
        });
        if (!anyValidInput) return false;

        boolean allValid = list.stream().allMatch(s -> {
            if (s.validity() == ScheduleValidity.妥当) return true;
            if (s.validity() == ScheduleValidity.妥当_未入力) return true;
            return false;
        });
        if (!allValid) return false;

        return true;
    }

    private boolean areDuplicated() {
        boolean areDuplicated = false;

        Set<ScheduleConstruct> scheduleConstructSet = new HashSet<>();
        for (Schedule schedule : list) {
            ScheduleConstruct scheduleConstruct = schedule.scheduleConstruct();
            if (scheduleConstruct.validity() != ScheduleConstructValidity.妥当_すべて入力済) {
                continue;
            }
            if (!scheduleConstructSet.add(scheduleConstruct)) {
                areDuplicated = true;
                break;
            }
        }

        return areDuplicated;
    }

    private boolean isEmpty() {
        if (Objects.isNull(list)) return true;
        if (list.isEmpty()) return true;
        return list.stream().allMatch(s -> {return s.validity() == ScheduleValidity.妥当_未入力;});
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
