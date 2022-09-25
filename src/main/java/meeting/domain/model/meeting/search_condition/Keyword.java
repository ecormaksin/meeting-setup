package meeting.domain.model.meeting.search_condition;

/**
 * 検索キーワード
 */
public class Keyword {
    String value;

    @Deprecated
    Keyword() {}

    public Keyword(String value) {
        this.value = value;
    }

    public static Keyword empty() {
        return new Keyword("");
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
