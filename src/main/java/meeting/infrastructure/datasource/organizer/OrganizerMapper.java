package meeting.infrastructure.datasource.organizer;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.domain.model.organizer.OrganizerNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrganizerMapper {

    boolean notExists(@Param("organizerNumber") OrganizerNumber organizerNumber);

    void insertOrganizer(@Param("organizerNumber") OrganizerNumber organizerNumber);

    void insertOrganizerAndMeetingRelation(
            @Param("meetingNumber") MeetingNumber meetingNumber,
            @Param("organizerNumber") OrganizerNumber organizerNumber);

    void deleteOrganizerAndMeetingRelation(
            @Param("meetingNumber") MeetingNumber meetingNumber);
}
