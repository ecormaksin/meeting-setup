package meeting.domain.model.meeting.meeting_list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * ミーティング一覧
 */
public class MeetingList {
    List<MeetingListItem> list;

    MeetingList(List<MeetingListItem> list) {
        this.list = list;
    }

    @Deprecated
    MeetingList() {}

    public static MeetingList of(List<MeetingListItem> source) {
        return new MeetingList(source);
    }

    public static MeetingList empty() {
        return new MeetingList(new ArrayList<>());
    }

    public List<MeetingListItem> asList() {
        return Collections.unmodifiableList(list);
    }

    public MeetingListItemAvailability itemAvailability() {
        if (Objects.isNull(list)) return MeetingListItemAvailability.HAVE_NO_ITEMS;
        if (list.isEmpty()) return MeetingListItemAvailability.HAVE_NO_ITEMS;
        return MeetingListItemAvailability.HAVE_ITEMS;
    }

    @Override
    public String toString() {
        // @formatter:off
        return "MeetingList{" +
                "list=" + list +
                '}';
        // @formatter:on
    }
}
