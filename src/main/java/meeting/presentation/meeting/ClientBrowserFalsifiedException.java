package meeting.presentation.meeting;

public class ClientBrowserFalsifiedException extends RuntimeException {

    public ClientBrowserFalsifiedException() {
        super("クライアント ブラウザーの情報が改ざんされているので、操作を続行できません。");
    }
}
