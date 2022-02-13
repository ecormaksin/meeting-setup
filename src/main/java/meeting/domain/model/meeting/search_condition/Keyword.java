package meeting.domain.model.meeting.search_condition;

/**
 * 検索キーワード
 */
public class Keyword {
    String value;

    public Keyword(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Keyword empty() {
        return new Keyword("");
    }

    @Override
    public String toString() {
        return value;
    }

    @Deprecated
    Keyword() {
    }
}
