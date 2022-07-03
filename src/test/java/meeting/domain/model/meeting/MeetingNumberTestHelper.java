package meeting.domain.model.meeting;

import java.util.Objects;

public class MeetingNumberTestHelper {

    private static final String PREFIX = "mt";

    public static MeetingNumber fromInt(final int intValue) {
        final int formatLength = MeetingNumber.SIZE - PREFIX.length();
        final String value = PREFIX + String.format("%0" + formatLength + "d", intValue);

        return new MeetingNumber(value);
    }

    public static void main(String[] args) {
        if (Objects.isNull(args) || args.length != 1) {
            throw new IllegalArgumentException("ミーティング番号の元となる数値を指定してください。");
        }
        final MeetingNumber meetingNumber = fromInt(Integer.valueOf(args[0]));
        System.out.println(meetingNumber.value());
    }
}
