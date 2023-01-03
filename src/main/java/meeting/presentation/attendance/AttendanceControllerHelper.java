package meeting.presentation.attendance;

import meeting.domain.model.attendance.AttendanceInputDuplicationStatus;
import meeting.domain.model.participant.ParticipantNameDuplicationStatus;
import org.springframework.validation.BindingResult;

class AttendanceControllerHelper {

    static boolean attendanceInputDuplicated(
            AttendanceInputDuplicationStatus attendanceInputDuplicationStatus,
            BindingResult bindingResult
    ) {
        if (attendanceInputDuplicationStatus == AttendanceInputDuplicationStatus.あり) {
            bindingResult.reject(null, attendanceInputDuplicationStatus.errorMessage());
            return true;
        }

        return false;
    }

    static boolean participantNameDuplicated(
            ParticipantNameDuplicationStatus participantNameDuplicationStatus,
            BindingResult bindingResult
    ) {
        if (participantNameDuplicationStatus == ParticipantNameDuplicationStatus.あり) {
            bindingResult.rejectValue("participantName.valid", null,
                    participantNameDuplicationStatus.errorMessage());
            return true;
        }

        return false;
    }
}
