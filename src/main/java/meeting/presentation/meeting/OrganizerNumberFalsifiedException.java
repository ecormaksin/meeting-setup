package meeting.presentation.meeting;

public class OrganizerNumberFalsifiedException extends RuntimeException {

    public OrganizerNumberFalsifiedException() {
        super("主催者番号が改ざんされているので、操作を続行できません。");
    }
}
