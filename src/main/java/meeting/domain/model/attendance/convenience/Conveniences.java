package meeting.domain.model.attendance.convenience;

import java.util.Collections;
import java.util.List;

/**
 * 都合リスト
 */
public class Conveniences {
    List<Convenience> list;

    Conveniences(List<Convenience> list) {
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

    @Override
    public String toString() {
        // @formatter:off
        return "Conveniences{" +
                "list=" + list +
                '}';
        // @formatter:on
    }
}
