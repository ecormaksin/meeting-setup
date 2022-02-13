package meeting.domain.model.meeting;

import meeting.domain.model.meeting.organizer.ClientBrowser;
import meeting.domain.model.meeting.search_condition.MeetingSearchCondition;

import javax.validation.Valid;

/**
 * ミーティング検索依頼
 */
public class MeetingSearchRequest {

    @Valid
    ClientBrowser organizerTerminal;

    @Valid
    MeetingSearchCondition searchCondition;

    public MeetingSearchRequest(@Valid ClientBrowser clientBrowser,
                                @Valid MeetingSearchCondition searchCondition) {
        this.organizerTerminal = clientBrowser;
        this.searchCondition = searchCondition;
    }

    @Deprecated
    MeetingSearchRequest() {
    }

    public ClientBrowser organizerTerminal() {
        return organizerTerminal;
    }

    public MeetingSearchCondition searchCondition() {
        return searchCondition;
    }

    public static MeetingSearchRequest initial(ClientBrowser clientBrowser) {
        return new MeetingSearchRequest(clientBrowser, MeetingSearchCondition.initial());
    }
}
