package meeting.domain.model.meeting.search_condition;

import mockit.Mock;
import mockit.MockUp;

import java.time.LocalDate;

public class MeetingSearchConditionFake_20120123  extends MockUp<MeetingSearchCondition> {

    @Mock
    public static MeetingSearchCondition initialized() {

        LocalDate startDateValue = LocalDate.of(2012,1,23);

        return new MeetingSearchCondition(
                Keyword.empty(),
                new StartDate(startDateValue),
                new EndDate(startDateValue.plusDays(7)));
    }
}
