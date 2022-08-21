package meeting.presentation.meeting.input.schedule;

import meeting.domain.model.schedule.Schedule;
import meeting.domain.model.schedule.Schedules;
import meeting.presentation.meeting.input.schedule.construct.ScheduleConstructInput;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

public class ScheduleInputs {

    private static final int INITIAL_EMPTY_SCHEDULES_SIZE = 3;

    @NotNull(message = Schedules.SIZE_WITHIN_SPECIFIED_RANGE)
    List<@Valid ScheduleInput> list;

    boolean sizeWithinSpecifiedRange;

    @Deprecated
    ScheduleInputs() {}

    public ScheduleInputs(final List<ScheduleInput> list) {
        this.list = list;

        sizeWithinSpecifiedRange = isSizeWithinSpecifiedRange();
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

    @AssertTrue(message = Schedules.SIZE_WITHIN_SPECIFIED_RANGE)
    boolean isSizeWithinSpecifiedRange() {
        final long sizeOfValidSchedules = sizeOfValidSchedules();
        return (Schedules.MIN_SIZE <= sizeOfValidSchedules) && (sizeOfValidSchedules <= Schedules.MAX_SIZE);
    }

    private long sizeOfValidSchedules() {
        return list.stream().filter(s -> s.isValidAsDomain()).count();
    }

    private List<ScheduleInput> validInputs() {
        return list.stream()
                .filter(s -> s.isValidAsDomain())
                .collect(Collectors.toList());
    }

    @AssertTrue(message = Schedules.SET_OF_UNIQUE_MEETING_DATE_TIMES)
    boolean isSetOfUniqueMeetingDateTimes() {
        final List<ScheduleInput> validInputs = validInputs();
        if (validInputs.isEmpty()) return true;

        Set<ScheduleConstructInput> constructSet = new HashSet<>();

        for (ScheduleInput schedule : validInputs()) {
            ScheduleConstructInput construct = schedule.scheduleConstructInput();
            boolean isAddedElement =  constructSet.add(construct);
            if (!isAddedElement) return false;
        }

        return true;
    }

    public List<ScheduleInput> asList() {
        return Collections.unmodifiableList(list);
    }

    public ScheduleInputAppendable appendable() {
        if (list.size() >= Schedules.MAX_SIZE) {
            return ScheduleInputAppendable.NO;
        }
        return ScheduleInputAppendable.YES;
    }

    public ScheduleInputs addNew() throws ScheduleInputCannotAddException {

        if (appendable() == ScheduleInputAppendable.NO) {
            throw new ScheduleInputCannotAddException();
        }

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
