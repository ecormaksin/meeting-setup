package meeting.domain.model.meeting;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 主催者のミーティング一覧
 */
public class OrganizerMeetingList {
    List<OrganizerMeetingListItem> list;

    OrganizerMeetingList(List<OrganizerMeetingListItem> list) {
        this.list = list;
    }

    @Deprecated
    public OrganizerMeetingList() {
    }

    public static OrganizerMeetingList of(List<OrganizerMeetingListItem> source) {
        return new OrganizerMeetingList(source);
    }

    public List<OrganizerMeetingListItem> asList() {
        return Collections.unmodifiableList(list);
    }

    public OrganizerMeetingListItemAvailability itemAvailability() {
        if (Objects.isNull(list)) return OrganizerMeetingListItemAvailability.HAVE_NO_ITEMS;
        if (list.isEmpty()) return OrganizerMeetingListItemAvailability.HAVE_NO_ITEMS;
        return OrganizerMeetingListItemAvailability.HAVE_ITEMS;
    }

    @Override
    public String toString() {
        // @formatter:off
        return "OrganizerMeetingList{" +
                "list=" + list +
                '}';
        // @formatter:on
    }
}
