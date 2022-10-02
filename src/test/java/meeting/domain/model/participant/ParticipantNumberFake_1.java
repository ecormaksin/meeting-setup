package meeting.domain.model.participant;

import mockit.Mock;
import mockit.MockUp;

public class ParticipantNumberFake_1 extends MockUp<ParticipantNumber> {

    @Mock
    public static ParticipantNumber newNumber() {
        return ParticipantNumberTestHelper.fromInt(1);
    }
}
