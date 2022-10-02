package meeting.domain.model.participant;

import meeting.domain.model.attendance.convenience.ConvenienceCategory;

import java.util.Map;

public class ConvenientParticipantsStatus {

    Map<ConvenienceCategory, TotalNumberOfParticipant> value;

    public ConvenientParticipantsStatus(Map<ConvenienceCategory, TotalNumberOfParticipant> value) {
        this.value = value;
    }

    public static ConvenientParticipantsStatus of(Map<ConvenienceCategory, TotalNumberOfParticipant> value) {
        return new ConvenientParticipantsStatus(value);
    }

    public boolean isMatchedBetterThanOrSame(ConvenientParticipantsStatus other) {
        TotalNumberOfParticipant thisMaru = value.get(ConvenienceCategory.マル);
        TotalNumberOfParticipant otherMaru = other.value.get(ConvenienceCategory.マル);

        if (thisMaru.isGreaterThan(otherMaru)) return true;

        TotalNumberOfParticipant thisSankaku = value.get(ConvenienceCategory.サンカク);
        TotalNumberOfParticipant otherSankaku = other.value.get(ConvenienceCategory.サンカク);

        if (thisMaru.isSame(otherMaru) && thisSankaku.isGreaterThanOrSame(otherSankaku)) return true;

        return false;
    }
}
