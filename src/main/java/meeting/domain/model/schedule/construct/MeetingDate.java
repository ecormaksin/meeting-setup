package meeting.domain.model.schedule.construct;

import meeting.domain.ValidationConcern;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 開催日
 */
public class MeetingDate extends ValidationConcern {

    @NotNull(message = "開催日は'yyyy-MM-dd'形式で必ず入力してください。")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate value;

    @Deprecated
    MeetingDate() {}

    public MeetingDate(final LocalDate value) {
        this.value = value;
        this.validate(this);
    }

    public static MeetingDate fromString(final String value) {
        return new MeetingDate(LocalDate.parse(value));
    }

    public boolean isSame(@NotNull @Valid final MeetingDate other) {
        return this.value.equals(other.value);
    }

    public LocalDate value() {
        return this.value;
    }

    public String stringValue() {
        return value.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String show() {
        return value.format(DateTimeFormatter.ofPattern("MM/dd(E)"));
    }
}
