package meeting.presentation.meeting;

public class MeetingCannotDeleteException extends RuntimeException {

    public MeetingCannotDeleteException() {
        super("主催者ではないので、ミーティングを削除できません。");
    }
}
