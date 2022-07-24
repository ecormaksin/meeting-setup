package meeting.presentation.meeting.input.schedule;

import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.Schedules;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleInputs {

    private static final int INITIAL_EMPTY_SCHEDULES_SIZE = 3;

    @NotNull(message = Schedules.MESSAGE_HAS_AT_LEAST_ONE_SCHEDULE)
    List<@NotNull @Valid ScheduleInput> list;

    @Deprecated
    ScheduleInputs() {}

    public ScheduleInputs(final List<ScheduleInput> list) {
        this.list = list;
    }

    public static ScheduleInputs of(final List<ScheduleInput> source) {
        return new ScheduleInputs(source);
    }

    public static ScheduleInputs initialEmptyList() {
        List<ScheduleInput> initialList = new ArrayList<>();
        for (int i = 0; i < INITIAL_EMPTY_SCHEDULES_SIZE; i++) {
            initialList.add(ScheduleInput.newInput());
        }
        return of(initialList);
    }

    public static ScheduleInputs of(final Schedules schedules) {
        List<ScheduleInput> inputs = new ArrayList<>();
        for (Schedule schedule : schedules.asList()) {
            ScheduleInput input = ScheduleInput.fromSchedule(schedule);
            inputs.add(input);
        }

        return of(inputs);
    }

    @AssertTrue(message = Schedules.MESSAGE_HAS_AT_LEAST_ONE_SCHEDULE)
    boolean isSetOfAtLeastOneSchedule() {
        return list.stream().anyMatch(s -> s.isValidAsDomain());
    }

    private List<ScheduleInput> validInputs() {
        return list.stream()
                .filter(s -> s.isValidAsDomain())
                .collect(Collectors.toList());
    }

    @AssertTrue(message = Schedules.MESSAGE_HAS_UNIQUE_START_DATE_TIMES)
    boolean isSetOfUniqueStartDateTime() {
        final List<ScheduleInput> validInputs = validInputs();
        if (validInputs.isEmpty()) return true;

        for (int i = 0; i < validInputs.size()-1; i++) {
            final ScheduleInput outer = validInputs.get(i);
            for (int j = i + 1; j < validInputs.size(); j++) {
                final ScheduleInput inner = validInputs.get(j);
                if (inner.isDuplicated(outer)) return false;
            }
        }
        return true;
    }

    public List<ScheduleInput> asList() {
        return Collections.unmodifiableList(list);
    }

    public ScheduleInputs addNew() {
        List<ScheduleInput> newList = new ArrayList<>(list);
        newList.add(ScheduleInput.newInput());
        return of(newList);
    }

    public ScheduleInputs remove(final int index) {
        List<ScheduleInput> newList = new ArrayList<>(list);
        newList.remove(index);
        return of(newList);
    }

    public Schedules toSchedules() {
        final List<ScheduleInput> validInputs = validInputs();

        List<Schedule> source = new ArrayList<>();
        for (ScheduleInput scheduleInput : validInputs) {
            Schedule schedule = scheduleInput.toSchedule();
            source.add(schedule);
        }

        return new Schedules(source);
    }
}
