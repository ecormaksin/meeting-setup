package meeting.domain.model.meeting.search_condition;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.util.Objects;

public class MeetingSearchCondition {
    Keyword keyword;

    @Valid
    StartDate startDate;

    @Valid
    EndDate endDate;

    LimitCount limitCount = LimitCount.defaultValue();

    boolean durationValid;

    @Deprecated
    MeetingSearchCondition() {}

    public MeetingSearchCondition(Keyword keyword,
                                  StartDate startDate,
                                  EndDate endDate) {
        this.keyword = keyword;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static MeetingSearchCondition initialized() {
        LocalDate currentDate = LocalDate.now();

        return new MeetingSearchCondition(
                Keyword.empty(),
                new StartDate(currentDate),
                new EndDate(currentDate.plusDays(7)));
    }

    public static MeetingSearchCondition of(Keyword keyword,
                                            StartDate startDate,
                                            EndDate endDate) {
        return new MeetingSearchCondition(
                keyword,
                startDate,
                endDate);
    }

    @AssertTrue(message = "「検索開始日 ≦ 検索終了日」となるように入力してください。")
    boolean isDurationValid() {
        if (startDate.isEmpty()) return true;
        if (endDate.isEmpty()) return true;
        return startDate.isBeforeOrSame(endDate);
    }

    public Keyword keyword() {
        return keyword;
    }

    public StartDate startDate() {
        return startDate;
    }

    public EndDate endDate() {
        return endDate;
    }

    public LimitCount limitCount() {
        return limitCount;
    }
}
