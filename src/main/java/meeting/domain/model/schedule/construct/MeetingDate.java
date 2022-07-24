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

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public static final String MESSAGE_VALID_FORMAT = "開催日は'yyyy-MM-dd'形式で入力してください。";

    @NotNull(message = MESSAGE_VALID_FORMAT)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate value;

    @Deprecated
    MeetingDate() {}

    public MeetingDate(final LocalDate value) {
        this.value = value;
        this.validate(this);
    }

    public static MeetingDate of(final LocalDate localDate) {
        return new MeetingDate(localDate);
    }

    public static MeetingDate of(final int year, final int month, final int dayOfMonth) {
        final LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
        return of(localDate);
    }

    public static MeetingDate of(final String value) {
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
