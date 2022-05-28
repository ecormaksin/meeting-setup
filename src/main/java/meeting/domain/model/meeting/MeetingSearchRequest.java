package meeting.domain.model.meeting;

import meeting.domain.model.meeting.search_condition.MeetingSearchCondition;

import javax.validation.Valid;

/**
 * ミーティング検索依頼
 */
public class MeetingSearchRequest {

    @Valid
    OrganizerNumber organizerNumber;

    @Valid
    MeetingSearchCondition searchCondition;

    @Deprecated
    MeetingSearchRequest() {}

    public MeetingSearchRequest(@Valid OrganizerNumber organizerNumber,
                                @Valid MeetingSearchCondition searchCondition) {
        this.organizerNumber = organizerNumber;
        this.searchCondition = searchCondition;
    }
    public static MeetingSearchRequest initialize(OrganizerNumber organizerNumber) {
        return new MeetingSearchRequest(organizerNumber, MeetingSearchCondition.initialize());
    }

    public OrganizerNumber organizerNumber() {
        return organizerNumber;
    }

    public MeetingSearchCondition searchCondition() {
        return searchCondition;
    }
}
