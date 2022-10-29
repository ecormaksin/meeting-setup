package meeting.domain.model.attendance.convenience;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * 都合リスト
 */
public class Conveniences {
    List<@Valid Convenience> list;

    @Deprecated
    Conveniences() {}

    public Conveniences(List<Convenience> list) {
        this.list = list;
    }

    public static Conveniences of(List<Convenience> source) {
        return new Conveniences(source);
    }

    public List<Convenience> asList() {
        return Collections.unmodifiableList(list);
    }
}
