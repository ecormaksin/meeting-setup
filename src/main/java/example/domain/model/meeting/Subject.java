package example.domain.model.meeting;

import javax.validation.constraints.NotBlank;

/**
 * 件名
 */
public class Subject {
    @NotBlank(message = "{example.domain.model.meeting.Subject.NotBlank.message}")
    String value;

    public Subject(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Deprecated
    Subject() {
    }
}
