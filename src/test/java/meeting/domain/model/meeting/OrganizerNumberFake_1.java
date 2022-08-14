package meeting.domain.model.meeting;

import mockit.Mock;
import mockit.MockUp;

public class OrganizerNumberFake_1 extends MockUp<OrganizerNumber> {

    @Mock
    public static OrganizerNumber newNumber() {
        return OrganizerNumberTestHelper.fromInt(1);
    }
}
