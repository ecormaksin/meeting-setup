package meeting.domain.model.meeting;

public class MeetingRegistrationRequestInvalidException extends RuntimeException {

    public MeetingRegistrationRequestInvalidException(String message) {
        super(message);
    }
}
