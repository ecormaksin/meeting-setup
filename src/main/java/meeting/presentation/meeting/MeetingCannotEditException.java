package meeting.presentation.meeting;

public class MeetingCannotEditException extends RuntimeException {

    public MeetingCannotEditException() {
        super("主催者ではないので、ミーティングを編集できません。");
    }
}
