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
        CountOfConvenience thisGood = value.get(ConvenienceCategory.GOOD);
        CountOfConvenience otherGood = other.value.get(ConvenienceCategory.GOOD);

        if (thisGood.isNotAtLeastTwo()) return ConvenienceStatus.NOT_SUITABLE;
        if (thisGood.isGreaterThan(otherGood)) return ConvenienceStatus.MOST_SUITABLE;

        CountOfConvenience thisModest = value.get(ConvenienceCategory.MODEST);
        CountOfConvenience otherModest = other.value.get(ConvenienceCategory.MODEST);
        if (thisGood.isSame(otherGood) && thisModest.isGreaterThanOrSame(otherModest)) {
            return ConvenienceStatus.MOST_SUITABLE;
        }
        return ConvenienceStatus.NOT_SUITABLE;
    }
}
