package meeting.domain.model.attendance;

import mockit.Mock;
import mockit.MockUp;

public class AttendanceNumberFake_11 extends MockUp<AttendanceNumber> {

    @Mock
    public static AttendanceNumber newNumber() {
        return AttendanceNumberTestHelper.fromInt(11);
    }
}
