package example.application.service.meeting;

import example.domain.model.meeting.MeetingNumber;
import org.springframework.stereotype.Service;

/**
 * ミーティングサービス
 */
@Service
public class MeetingService {
    MeetingRepository ミーティングリポジトリ;

    public MeetingService(MeetingRepository ミーティングリポジトリ) {
        this.ミーティングリポジトリ = ミーティングリポジトリ;
    }

    public MeetingNumber 新規作成準備() {
        return ミーティングリポジトリ.新規作成準備();
    }
}
