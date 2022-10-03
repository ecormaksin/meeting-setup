package meeting.domain.model.meeting;

public class MeetingRegistrationRequestInvalidException extends IllegalArgumentException {

    public MeetingRegistrationRequestInvalidException(String message) {
        super(message);
    }
}
