package example.application.activity;

import example.application.service.meeting.MeetingService;
import example.domain.model.meeting.MeetingNumber;
import org.springframework.stereotype.Service;

/**
 * ミーティング作成業務
 */
@Service
public class MeetingCreationActivity {
    MeetingService ミーティングサービス;

    public MeetingCreationActivity(MeetingService ミーティングサービス) {
        this.ミーティングサービス = ミーティングサービス;
    }

    public MeetingNumber 作成() {
        MeetingNumber meetingNumber = ミーティングサービス.新規作成準備();
        return meetingNumber;
    }
}
