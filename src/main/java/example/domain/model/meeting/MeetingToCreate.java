package example.domain.model.meeting;

import example.domain.model.schedule.Schedule;
import example.domain.model.schedule.ScheduleList;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * ミーティング作成
 */
public class MeetingToCreate {
    @NotBlank
    String 件名;

    String メモ;

    @NotNull
    @Valid
    ScheduleList スケジュールリスト;

    public MeetingToCreate(String 件名, String メモ, ScheduleList スケジュールリスト) {
        this.件名 = 件名;
        this.メモ = メモ;
        this.スケジュールリスト = スケジュールリスト;
    }

    public static MeetingToCreate blank() {
        return new MeetingToCreate("", "", ScheduleList.of(new ArrayList<Schedule>()));
    }

    public String 件名() {
        return 件名;
    }

    public String メモ() {
        return メモ;
    }

    public ScheduleList スケジュールリスト() {
        return スケジュールリスト;
    }
}
