package meeting.infrastructure.datasource.participant;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import meeting.domain.model.participant.ParticipantName;
import meeting.domain.model.participant.ParticipantNumber;
import meeting.domain.model.participant.ParticipantNumberTestHelper;
import meeting.infrastructure.datasource.DBUnitWithoutWebTest;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParticipantDatasourceTest extends DBUnitWithoutWebTest {

    @Container
    private static final MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Autowired
    ParticipantDatasource participantDatasource;

    @Test
    @DatabaseSetup("testdata/participant_for_last_name_exists.xlsx")
    void _最新の参加者名を取得できる() {
        ParticipantNumber participantNumber = ParticipantNumberTestHelper.fromInt(1);

        ParticipantName participantName = participantDatasource.getLatestParticipantName(participantNumber);

        assertEquals(participantName.value(), "参加者名テスト1");
    }

    @Test
    @DatabaseSetup("testdata/participant_for_last_name_not_exists.xlsx")
    void _最新の参加者名を取得できない場合は空欄である() {
        ParticipantNumber participantNumber = ParticipantNumberTestHelper.fromInt(1);

        ParticipantName participantName = participantDatasource.getLatestParticipantName(participantNumber);

        assertEquals(participantName.value(), "");
    }
}
