package meeting.domain.model.schedule;

public class SchedulesInvalidException extends IllegalArgumentException {

    public SchedulesInvalidException(String message) {
        super(message);
    }
}
