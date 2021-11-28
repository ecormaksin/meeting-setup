package meeting.domain.model.participant;

import java.util.Collections;
import java.util.List;

/**
 * 参加者リスト
 */
public class Participants {
    List<Participant> list;

    Participants(List<Participant> list) {
        this.list = list;
    }

    @Deprecated
    public Participants() {
    }

    public static Participants of(List<Participant> source) {
        return new Participants(source);
    }

    public List<Participant> asList() {
        return Collections.unmodifiableList(list);
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Participants{" +
                "list=" + list +
                '}';
        // @formatter:on
    }
}
