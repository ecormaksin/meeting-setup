package meeting.domain.model.schedule;

public class SchedulesInvalidException extends RuntimeException {

    public SchedulesInvalidException(final String message) {
        super(message);
    }
}
