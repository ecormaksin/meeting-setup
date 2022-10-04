package meeting.domain.model.attendance.convenience;

public enum ConvenienceStatus {

    MOST_SUITABLE("convenience-most-suitable"),
    NOT_SUITABLE("");

    String status;

    ConvenienceStatus(String status) {
        this.status = status;
    }

    public String status(){
        return status;
    }
}
