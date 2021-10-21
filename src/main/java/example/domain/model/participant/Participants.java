package example.domain.model.participant;

import java.util.Iterator;
import java.util.Set;

/**
 * 参加者セット
 */
public class Participants {
    Set<Participant> set;

    Participants(Set<Participant> set) {
        this.set = set;
    }

    @Deprecated(since = "MyBatis")
    public Participants() {
    }

    public static Participants of(Set<Participant> source) {
        return new Participants(source);
    }

    public Iterator<Participant> list() {
        return set.iterator();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "Participants{" +
                "set=" + set +
                '}';
        // @formatter:on
    }
}
