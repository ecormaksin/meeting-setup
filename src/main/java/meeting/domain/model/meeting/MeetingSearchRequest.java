package meeting.domain.model.meeting;

import meeting.domain.model.meeting.search_condition.MeetingSearchCondition;
import meeting.domain.model.organizer.OrganizerNumber;

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

    public MeetingSearchRequest(OrganizerNumber organizerNumber,
                                MeetingSearchCondition searchCondition) {
        this.organizerNumber = organizerNumber;
        this.searchCondition = searchCondition;
    }
    public static MeetingSearchRequest initialized(OrganizerNumber organizerNumber) {
        return new MeetingSearchRequest(organizerNumber, MeetingSearchCondition.initialized());
    }

    public static MeetingSearchRequest of(OrganizerNumber organizerNumber,
                                          MeetingSearchCondition searchCondition) {
        return new MeetingSearchRequest(organizerNumber, searchCondition);
    }

    public OrganizerNumber organizerNumber() {
        return organizerNumber;
    }

    public MeetingSearchCondition searchCondition() {
        return searchCondition;
    }
}
