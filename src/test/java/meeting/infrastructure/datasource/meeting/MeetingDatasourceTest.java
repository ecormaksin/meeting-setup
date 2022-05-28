package meeting.infrastructure.datasource.meeting;

import meeting.MeetingDBTest;
import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.meeting.Memo;
import meeting.domain.model.meeting.OrganizerNumber;
import meeting.domain.model.meeting.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MeetingDBTest
class MeetingDatasourceTest {
    @Autowired
    MeetingDatasource meetingDatasource;

    @Test
    void _メモあり_日程1件の場合() throws Exception {

        // Arrange
        final MeetingNumber meetingNumber = MeetingNumber.newNumber();
        final Subject subject = new Subject("件名テスト");
        final Memo memo = new Memo("ミーティング メモ テスト");
        final OrganizerNumber organizerNumber = OrganizerNumber.newNumber();
    }
}
