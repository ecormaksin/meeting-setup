package meeting.domain.model.meeting.search_condition;

import javax.validation.Valid;

public class MeetingSearchCondition {
    Keyword keyword;

    @Valid
    StartDate startDate;

    @Valid
    EndDate endDate;

    public MeetingSearchCondition(Keyword keyword,
                                  @Valid StartDate startDate,
                                  @Valid EndDate endDate) {
        this.keyword = keyword;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Deprecated
    MeetingSearchCondition() {
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
}
