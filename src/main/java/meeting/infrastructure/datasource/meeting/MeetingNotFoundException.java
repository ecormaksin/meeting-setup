package meeting.infrastructure.datasource.meeting;

public class MeetingNotFoundException extends RuntimeException {

    public MeetingNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
