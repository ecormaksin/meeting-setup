package meeting.domain.model.participant;

import mockit.Mock;
import mockit.MockUp;

public class ParticipantNumberFake_2 extends MockUp<ParticipantNumber> {

    @Mock
    public static ParticipantNumber newNumber() {
        return ParticipantNumberTestHelper.fromInt(2);
    }
}
