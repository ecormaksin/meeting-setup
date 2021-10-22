package example.domain.model.participant;

import java.util.Iterator;
import java.util.List;

/**
 * 参加者リスト
 */
public class Participants {
    List<Participant> list;

    Participants(List<Participant> list) {
        this.list = list;
    }

    @Deprecated(since = "MyBatis")
    public Participants() {
    }

    public static Participants of(List<Participant> source) {
        return new Participants(source);
    }

    public Iterator<Participant> list() {
        return list.iterator();
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
