package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.StartMinute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 開始分の選択肢
 */
public class StartMinuteOptions {

    List<StartMinuteOption> list;

    private StartMinuteOptions(List<StartMinuteOption> list) {
        this.list = list;
    }

    public static StartMinuteOptions initialize() {

        List<StartMinuteOption> list = new ArrayList<>();

        list.add(new StartMinuteOption("", ""));
        for (StartMinute startMinute : StartMinute.values()) {
            StartMinuteOption option = new StartMinuteOption(
                    String.valueOf(startMinute.value()),
                    startMinute.label());
            list.add(option);
        }

        return new StartMinuteOptions(list);
    }

    public List<StartMinuteOption> asList() {
        return Collections.unmodifiableList(list);
    }
}
