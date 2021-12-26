package meeting.domain.model.attendance.convenience;

import meeting.domain.model.schedule.ScheduleNumber;

import java.util.Collections;
import java.util.List;

/**
 * 都合リスト
 */
public class Conveniences {
    List<Convenience> list;

    public Conveniences(List<Convenience> list) {
        this.list = list;
    }

    @Deprecated
    Conveniences() {
    }

    public static Conveniences of(List<Convenience> source) {
        return new Conveniences(source);
    }

    public List<Convenience> asList() {
        return Collections.unmodifiableList(list);
    }

    public Convenience of(ScheduleNumber scheduleNumber) {
        for (Convenience convenience : list) {
            if (scheduleNumber.equals(convenience.scheduleNumber())) {
                return convenience;
            }
        }
        throw new IllegalArgumentException("該当する日程候補が見つかりません。");
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Conveniences{" +
                "list=" + list +
                '}';
        // @formatter:on
    }
}
