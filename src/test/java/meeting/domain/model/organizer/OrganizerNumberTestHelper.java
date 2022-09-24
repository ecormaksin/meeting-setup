package meeting.domain.model.organizer;

import java.util.Objects;

public class OrganizerNumberTestHelper {

    static final String PREFIX = "og";

    public static OrganizerNumber fromInt(int intValue) {
        int formatLength = OrganizerNumber.SIZE - PREFIX.length();
        String value = PREFIX + String.format("%0" + formatLength + "d", intValue);

        return new OrganizerNumber(value);
    }

    public static void main(String[] args) {
        if (Objects.isNull(args) || args.length != 1) {
            throw new IllegalArgumentException("ミーティング番号の元となる数値を指定してください。");
        }
        OrganizerNumber organizerNumber = fromInt(Integer.valueOf(args[0]));
        System.out.println(organizerNumber.value());
    }
}
