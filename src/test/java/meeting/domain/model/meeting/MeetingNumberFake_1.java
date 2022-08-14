package meeting.domain.model.meeting;

import mockit.Mock;
import mockit.MockUp;

public class MeetingNumberFake_1 extends MockUp<MeetingNumber> {

    @Mock
    public static MeetingNumber newNumber() {
        return MeetingNumberTestHelper.fromInt(1);
    }
}
