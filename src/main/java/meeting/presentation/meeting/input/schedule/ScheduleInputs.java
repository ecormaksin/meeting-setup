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

    @NotNull
    List<@NotNull @Valid ScheduleInput> list;

    @Deprecated
    ScheduleInputs() {}

    public ScheduleInputs(@NotNull final List<@NotNull @Valid ScheduleInput> list) {
        this.list = list;
    }

    public static ScheduleInputs of(@NotNull final List<@NotNull @Valid ScheduleInput> source) {
        return new ScheduleInputs(source);
    }

    public static ScheduleInputs initialEmptyList() {
        List<ScheduleInput> initialList = new ArrayList<>();
        for (int i = 0; i < INITIAL_EMPTY_SCHEDULES_SIZE; i++) {
            initialList.add(ScheduleInput.newInput());
        }
        return of(initialList);
    }

    public static ScheduleInputs fromDomainObject(final Schedules schedules) {
        List<ScheduleInput> inputs = new ArrayList<>();
        for (Schedule schedule : schedules.asList()) {
            ScheduleInput input = ScheduleInput.fromDomainObject(schedule);
            inputs.add(input);
        }

        return of(inputs);
    }

    @AssertTrue(message = "日程を1件以上指定してください。")
    boolean haveAtLeastOne() {
        return list.stream().anyMatch(s -> s.isValidAsDomain());
    }

    private List<ScheduleInput> validInputs() {
        return list.stream()
                .filter(s -> s.isValidAsDomain())
                .collect(Collectors.toList());
    }

    @AssertTrue(message = "日程は重複のないように指定してください。")
    boolean areNotDuplicated() {
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

    public Schedules toDomainObject() {
        final List<ScheduleInput> validInputs = validInputs();

        List<Schedule> source = new ArrayList<>();
        for (ScheduleInput scheduleInput : validInputs) {
            Schedule schedule = scheduleInput.toDomainObject();
            source.add(schedule);
        }

        return new Schedules(source);
    }
}
