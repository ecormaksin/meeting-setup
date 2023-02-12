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
    Conveniences() {
    }

    public Conveniences(List<Convenience> list) {
        this.list = list;
    }

    public static Conveniences of(List<Convenience> source) {
        return new Conveniences(source);
    }

    public List<Convenience> asList() {
        return Collections.unmodifiableList(list);
    }


    public boolean isSame(Conveniences other) {
        return this.list.equals(other.list);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Convenience convenience : list) {
            if (sb.length() > 0) sb.append(", ");
            sb.append("""
                    {
                        "convenience": %s
                    }
                    """.formatted(convenience.toString()));
        }
        return "[%s]".formatted(sb.toString());
    }
}
