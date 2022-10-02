package meeting.infrastructure.datasource.meeting;

import meeting.domain.model.meeting.MeetingNumber;

public class MeetingNotFoundException extends RuntimeException {

    MeetingNumber meetingNumber;

    public MeetingNotFoundException(MeetingNumber meetingNumber) {
        super(String.format("指定されたミーティングは、存在しないか主催者に削除されています。主催者へミーティング番号を確認してください。: %s",
                meetingNumber.value()));
        this.meetingNumber = meetingNumber;
    }
}
