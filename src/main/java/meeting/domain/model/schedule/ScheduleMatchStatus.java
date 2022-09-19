package meeting.domain.model.schedule;

public enum ScheduleMatchStatus {

    BEST_MATCHED("schedule-best-matched"),
    NOT_MATCHED("");

    String status;

    ScheduleMatchStatus(String status) {
        this.status = status;
    }

    public String status(){
        return status;
    }
}
