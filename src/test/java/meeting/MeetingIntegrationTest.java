package meeting;

import meeting.infrastructure.datasource.DBUnitBaseTest;
import meeting.presentation.TestWebConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@Import({TestWebConfig.class})
public abstract class MeetingIntegrationTest {
}
