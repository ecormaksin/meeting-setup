package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 会議時間の選択肢
 */
public class DurationOptions {

    List<DurationOption> list;

    private DurationOptions(List<DurationOption> list) {
        this.list = list;
    }

    public static DurationOptions initialize() {

        List<DurationOption> list = new ArrayList<>();

        list.add(new DurationOption("", ""));
        for (Duration duration : Duration.values()) {
            DurationOption option = new DurationOption(
                    String.valueOf(duration.value()),
                    duration.label());
            list.add(option);
        }

        return new DurationOptions(list);
    }

    public List<DurationOption> asList() {
        return Collections.unmodifiableList(list);
    }
}
