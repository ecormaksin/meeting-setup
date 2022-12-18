package meeting.domain.model.participant;

import java.util.Objects;

public class ParticipantNumberTestHelper {

    static final String PREFIX = "pt";

    public static ParticipantNumber fromInt(int intValue) {
        int formatLength = ParticipantNumber.SIZE - PREFIX.length();
        String value = PREFIX + String.format("%0" + formatLength + "d", intValue);

        return new ParticipantNumber(value);
    }

    public static void main(String[] args) {
        if (Objects.isNull(args) || args.length != 1) {
            throw new IllegalArgumentException("出欠番号の元となる数値を指定してください。");
        }
        ParticipantNumber attendanceNumber = fromInt(Integer.valueOf(args[0]));
        System.out.println(attendanceNumber.value());
    }
}
