package meeting.domain.model.attendance.convenience;

import java.util.Map;

public class CountsOfConvenience {

    Map<ConvenienceCategory, CountOfConvenience> value;

    public CountsOfConvenience(Map<ConvenienceCategory, CountOfConvenience> value) {
        this.value = value;
    }

    public static CountsOfConvenience of(Map<ConvenienceCategory, CountOfConvenience> value) {
        return new CountsOfConvenience(value);
    }

    public ConvenienceStatus isMoreSuitableOrSame(CountsOfConvenience other) {
        CountOfConvenience thisMaru = value.get(ConvenienceCategory.GOOD);
        CountOfConvenience otherMaru = other.value.get(ConvenienceCategory.GOOD);

        if (thisMaru.isNotAtLeastTwo()) return ConvenienceStatus.NOT_SUITABLE;
        if (thisMaru.isGreaterThan(otherMaru)) return ConvenienceStatus.MOST_SUITABLE;

        CountOfConvenience thisSankaku = value.get(ConvenienceCategory.MODEST);
        CountOfConvenience otherSankaku = other.value.get(ConvenienceCategory.MODEST);
        if (thisMaru.isSame(otherMaru) && thisSankaku.isGreaterThanOrSame(otherSankaku)) {
            return ConvenienceStatus.MOST_SUITABLE;
        }
        return ConvenienceStatus.NOT_SUITABLE;
    }
}
