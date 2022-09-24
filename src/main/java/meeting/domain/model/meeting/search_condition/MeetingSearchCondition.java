package meeting.domain.model.meeting.search_condition;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;

import static meeting.domain.model.meeting.search_condition.MeetingSearchConditionValidity.*;

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

        durationValid = isDurationValid();
    }

    public static MeetingSearchCondition initialized() {
        LocalDate currentDate = LocalDate.now();

        return new MeetingSearchCondition(Keyword.empty(),
                new StartDate(currentDate),
                new EndDate(currentDate.plusDays(7)));
    }

    @AssertTrue(message = "「検索開始日 ≦ 検索終了日」となるように入力してください。")
    boolean isDurationValid() {
        return startDate.isSameOrAfter(endDate);
    }

    public MeetingSearchConditionValidity validity() {
        if (startDate.isEmpty()) return 不当_検索開始日が未入力;
        if (endDate.isEmpty()) return 不当_検索終了日が未入力;
        if (endDate.value().isBefore(startDate.value())) return 不当_検索開始日と検索終了日の前後関係が逆;
        return 妥当;
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
