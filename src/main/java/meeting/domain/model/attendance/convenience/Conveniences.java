package meeting.domain.model.attendance.convenience;

import java.util.Collections;
import java.util.List;

/**
 * 都合リスト
 */
public class Conveniences {
    List<Convenience> list;

    @Deprecated
    Conveniences() {}

    public Conveniences(List<Convenience> list) {
        this.list = list;
    }

    public List<Convenience> asList() {
        return Collections.unmodifiableList(list);
    }
}
