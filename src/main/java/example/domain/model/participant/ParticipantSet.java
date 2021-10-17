package example.domain.model.participant;

import java.util.Iterator;
import java.util.Set;

/**
 * 参加者セット
 */
public class ParticipantSet {
    Set<Participant> set;

    ParticipantSet(Set<Participant> set) {
        this.set = set;
    }

    @Deprecated(since = "MyBatis")
    public ParticipantSet() {
    }

    public static ParticipantSet of(Set<Participant> source) {
        return new ParticipantSet(source);
    }

    public Iterator<Participant> list() {
        return set.iterator();
    }

    @Override
    public String toString() {
        // @formatter:off
        return "ParticipantSet{" +
                "set=" + set +
                '}';
        // @formatter:on
    }
}
