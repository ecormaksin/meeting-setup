package meeting.domain.model.schedule;

import java.util.Collections;
import java.util.List;

public class SearchedSchedules {

    List<SearchedSchedule> list;

    @Deprecated
    SearchedSchedules() {}

    public SearchedSchedules(List<SearchedSchedule> list) {
        this.list = list;
    }

    public static SearchedSchedules of(List<SearchedSchedule> list) {
        return new SearchedSchedules(list);
    }

    public List<SearchedSchedule> asList() {
        return Collections.unmodifiableList(list);
    }
}
