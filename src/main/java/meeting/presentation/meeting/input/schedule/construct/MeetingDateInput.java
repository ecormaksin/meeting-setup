package meeting.presentation.meeting.input.schedule.construct;

/**
 * ミーティング開催日（入力用）
 */
public class MeetingDateInput {

    String value;

    public MeetingDateInput(String value) {
        this.value = value;
    }

    @Deprecated
    MeetingDateInput() {}

    public static MeetingDateInput empty() {
        return new MeetingDateInput("");
    }

    public String value() {
        return value;
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public boolean isSame(MeetingDateInput other) {
        return this.value.equals(other.value);
    }
}
